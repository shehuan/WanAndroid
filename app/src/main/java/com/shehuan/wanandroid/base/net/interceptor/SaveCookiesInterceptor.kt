package com.shehuan.wanandroid.base.net.interceptor

import com.shehuan.wanandroid.utils.sp.SpUtil
import okhttp3.Interceptor
import okhttp3.Response
import java.lang.StringBuilder

class SaveCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        val request = chain.request()
        if (request.url().toString().contains("login") || request.url().toString().contains("register")) {
            if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                val cookies = originalResponse.headers("Set-Cookie")

                if (!cookies.isEmpty()) {
                    val cookieStr = StringBuilder()
                    for (cookie in cookies) {
                        cookieStr.append(cookie)
                        cookieStr.append("#")
                    }
                    SpUtil.setCookies(cookieStr.toString())
                }
            }
        }

        return originalResponse
    }
}