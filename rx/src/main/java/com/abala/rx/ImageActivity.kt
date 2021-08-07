package com.abala.rx

import android.app.ProgressDialog
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.HttpURLConnection
import java.net.URL

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        loadImage()
    }

    override fun onDestroy() {
        super.onDestroy()
        ensureDisposed()
    }

    private val imageUrl = "http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg"

    private var mDisposable: Disposable? = null

    private fun loadImage() {
        Observable //起点
            .just(imageUrl) //Step 2
            .map { url -> getRemoteImage(url) }//业务1
            .map { bitmap: Bitmap? -> watermark(bitmap) }//业务2
            //.map {} 业务3...
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.compose(transformer())
            .subscribe(object : Observer<Bitmap?> {
                override fun onSubscribe(d: Disposable) {//Step 1
                    //Disposable 需要缓存 在onDestroy 方法中确保释放
                    mDisposable = d
                    showLoadingView()
                }

                override fun onNext(image: Bitmap) {//Step 3
                    updateImage(image)
                }

                override fun onError(e: Throwable) {//Step 3
                    Log.i(TAG, "onError $e")
                    dismissLoadingView()
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                }

                override fun onComplete() {//Step 4
                    dismissLoadingView()
                }
            })
    }

    private var loadingView: ProgressDialog? = null
    private fun showLoadingView() {
        loadingView = ProgressDialog(this)
        loadingView?.setMessage("Image loading ...")
        loadingView?.show()
    }

    private fun dismissLoadingView() {
        loadingView?.dismiss()
        loadingView = null
    }

    private fun ensureDisposed() {
        if (mDisposable?.isDisposed == false && isFinishing) {
            mDisposable?.dispose()
        }
    }

    private fun getRemoteImage(url: String): Bitmap? {
        val conn = URL(url).openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        if (conn.responseCode == HttpURLConnection.HTTP_OK) {
            return BitmapFactory.decodeStream(conn.inputStream)
        }
        return null
    }

    private fun watermark(bitmap: Bitmap?): Bitmap? {
        val config = bitmap?.config ?: Bitmap.Config.ARGB_8888
        val bmp = bitmap?.copy(config, true) ?: return null
        val canvas = Canvas(bmp)
        val mark = "RxJava WaterMark"
        val startX = 88F
        val startY = 88F
        val paint = Paint()
        paint.textSize = 88F
        paint.color = Color.RED
        paint.isDither = true //获取更清晰的图像采样
        paint.isFilterBitmap = true //过滤一些
        canvas.drawText(mark, startX, startY, paint)
        return bmp
    }

    private fun updateImage(image: Bitmap?) {
        findViewById<ImageView>(R.id.image).setImageBitmap(image)
    }

    //使用compose()操作符 封装一些业务 使其更紧凑
    fun <U> transformer(): ObservableTransformer<U, U> {
        return ObservableTransformer<U, U> { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            //.map {} 做其它业务
        }
    }

}