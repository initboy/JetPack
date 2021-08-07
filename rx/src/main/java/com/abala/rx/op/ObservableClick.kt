package com.abala.rx.op

import android.os.Looper
import android.util.Log
import android.view.View
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.atomic.AtomicBoolean

//实现控件防暴击的自定义Rx操作符，使用方法方法click(v: View)
class ObservableClick(private var v: View) : Observable<Any>() {
    companion object {
        const val TAG = "ObservableClick"

        @JvmStatic
        fun click(v: View): Observable<Any> = ObservableClick(v)
    }

    //继续向下流转的对象，没有特殊指明用Any
    private val e = Any()

    //订阅设置点击事件监听
    override fun subscribeActual(observer: Observer<Any>?) {
        val wrapper = OnClickListenerWrapper(v, observer)
        observer?.onSubscribe(wrapper)
        v.setOnClickListener(wrapper)
        Log.i(TAG, "<<< subscribeActual")
    }

    //点击事件包装类
    inner class OnClickListenerWrapper(
        private var v: View,
        private var observer: Observer<Any>?
    ) :
        View.OnClickListener, Disposable {
        //记录是否处理
        private val isDisposable = AtomicBoolean()
        override fun onClick(v: View?) {
            Log.i(TAG, "<<< onClick $isDisposed")
            //事件向下流转
            if (!isDisposed) {
                observer?.onNext(e)
            }
        }

        //dispose 后 点击监听事件置空
        override fun dispose() {
            Log.i(TAG, "<<< dispose ")
            if (isDisposable.compareAndSet(false, true)) {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    v.setOnClickListener(null)
                } else {
                    AndroidSchedulers.mainThread().scheduleDirect {
                        v.setOnClickListener(null)
                    }
                }
            }
        }

        override fun isDisposed(): Boolean = isDisposable.get()
    }
}