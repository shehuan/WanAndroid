package com.shehuan.keasymvp.mvp.net

import com.shehuan.keasymvp.mvp.Const
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private val okHttpClient: OkHttpClient by lazy {
        getOkHttpClient(true)
    }

    fun <S> create(service: Class<S>): S {
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Const.WAN_ANDROID_UTL)
                .build()
        return retrofit.create(service)
    }

    private fun getOkHttpClient(flag: Boolean): OkHttpClient {
        //配置超时拦截器
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)

        if (flag) {
            //配置log打印拦截器
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
        }

        // 请求相应拦截器
        // builder.addInterceptor(new CommonInterceptor());

        return builder.build()
    }
}