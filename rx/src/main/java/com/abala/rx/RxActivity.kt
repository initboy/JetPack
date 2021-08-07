package com.abala.rx

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.abala.rx.api.*
import com.abala.rx.bean.ProjectList
import com.abala.rx.bean.ProjectTree
import com.abala.rx.bean.Tree
import com.abala.rx.net.NetUtil
import com.abala.rx.op.ObservableClick
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

const val TAG = "RxActivity"

class RxActivity : AppCompatActivity() {
    lateinit var api: NetApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx)
        api = NetUtil.buildRetrofit().create(NetApi::class.java)
        val b = findViewById<Button>(R.id.op_custom)
        onClickOpCustom(b)
    }

    private val observerTree = object : Observer<ProjectTree> {
        override fun onSubscribe(d: Disposable) {//Step 1
            Log.i(TAG, "onSubscribe $d")
        }

        override fun onNext(t: ProjectTree) {//Step 3 normal
            Log.i(TAG, "onNext $t")
        }

        override fun onError(e: Throwable) {//Step 3 abnormal
            Log.i(TAG, "onError $e")
        }

        override fun onComplete() {//Step 4
            Log.i(TAG, "onComplete")
        }
    }

    fun onClickTree(v: View) {
        api.getProjectTree()//Step 2
            .subscribeOn(Schedulers.io())//给上一步分配异步线程
            .observeOn(AndroidSchedulers.mainThread())//给下一步分配AndroidUI主线程
            .subscribe(observerTree)
    }

    fun onClickList(v: View) {
        val disposable = api.getProjectList(1, 294)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {//实现 Consumer 实现 accept w
                Log.i(TAG, "accept $it")
            }
    }

    //使用compose()操作符 封装一些业务 使其更紧凑
    fun <U> transformer(): ObservableTransformer<U, U> {
        return ObservableTransformer<U, U> { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            //.map {} 做其它业务
        }
    }

    //多接口依赖调用，代码层层嵌套
    fun onClickShake(v: View) {
        Log.i(TAG, "v  $v")
        val d = RxView.clicks(v)
            .throttleLast(2, TimeUnit.SECONDS)
            .subscribe {
                api.getProjectTree()
                    .compose(transformer())
                    .subscribe { projectTree: ProjectTree? ->
                        projectTree?.data?.forEach { tree: Tree ->
                            api.getProjectList(1, tree.id)
                                .compose(transformer())
                                .subscribe { projectList: ProjectList? ->
                                    Log.i(TAG, "onClickShake accept $projectList")
                                }
                        }
                    }
            }
    }

    //flatMap 操作的使用 ，规避代码层层嵌套
    fun onClickFlatMap(v: View) {
        Log.i(TAG, "onClickFlatMap v $v")
        val disposable = RxView.clicks(v)
            .throttleFirst(1, TimeUnit.SECONDS)
            .observeOn(Schedulers.io())//给下面切换异步线程
            .flatMap { api.getProjectTree() }//拿到主要数据
            .flatMap { Observable.fromIterable(it.data) }//遍历列表，每遍历一次，下面的代码会执行一遍
            .flatMap { api.getProjectList(1, it.id) }//拿到一个列表中的信息
            .observeOn(AndroidSchedulers.mainThread())//给下面切换UI线程
            .subscribe {
                Log.i(TAG, "onClickFlatMap accept $it")
            }
    }

    fun onClickImage(v: View) {
        startActivity(Intent(this, ImageActivity::class.java))
    }

    //背压 ObservableEmitter 发射的任务太多，后续无法及时处理导致的内存增长
    //原始的create操作符
    fun origin() {
        val disposable = Observable
            .create<String> {
                it.onNext("from create source")
                it.onError(Throwable("error"))
            }
            .map { 111 }
            .subscribe {

            }
    }

    //自定义操作符的使用
    private fun onClickOpCustom(v: View) {
        Log.i(TAG, "onClickOpCustom $v")
        val d = ObservableClick.click(v)
            .throttleFirst(1, TimeUnit.SECONDS)//1秒之内的点击操作只向下发送一次
            .map { 123 }
            .subscribe(object : Observer<Any> {
                override fun onSubscribe(d: Disposable) {
                    Log.i(TAG, "onSubscribe")
                }

                override fun onNext(t: Any) {
                    Log.i(TAG, "onNext $t")
                }

                override fun onError(e: Throwable) {
                    Log.i(TAG, "onError")
                }

                override fun onComplete() {
                    Log.i(TAG, "onComplete")
                }

            })
    }

    //多api连续请求，比如注册并登录业务
    fun regAndLogin() {
        val api = NetUtil
            .buildRetrofit()
            .create(LoginApi::class.java)

        api.reg(RegRequest())//请求注册
            .subscribeOn(Schedulers.io())//给上面分配异步线程
            .observeOn(AndroidSchedulers.mainThread())//给下面分配UI线程
            .doOnNext {//更新注册结果的UI
            }
            .observeOn(Schedulers.io())//给下面分配异步线程
            .flatMap {//请求登录
                api.login(LoginRequest())
            }
            .observeOn(AndroidSchedulers.mainThread())//给下面分配UI线程
            .subscribe(object : Observer<LoginResponse> {
                override fun onSubscribe(d: Disposable) {//更新开始注册UI

                }

                override fun onNext(t: LoginResponse) {//更新开始登录UI
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {//结束
                }

            })
    }

}