package com.abala.rx.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetUtil {

    private const val TIME_OUT_IN_MS = 10000L
    private const val HOST = "https://www.wanandroid.com/"
    var baseUrl = HOST
    fun buildRetrofit(): Retrofit {
        val client = OkHttpClient
            .Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .connectTimeout(TIME_OUT_IN_MS, TimeUnit.MILLISECONDS)
            .readTimeout(TIME_OUT_IN_MS, TimeUnit.MILLISECONDS)
            .writeTimeout(TIME_OUT_IN_MS, TimeUnit.MILLISECONDS)
            .build()//OkHttp客户端配置
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(client)//OkHttp客户端
            .addConverterFactory(GsonConverterFactory.create(Gson()))//json解析工具
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava处理工具
            .build()
    }
}