package com.shehuan.wanandroid

import android.app.Application
import org.litepal.LitePal

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        LitePal.initialize(this)
    }

    companion object {
        private lateinit var instance: App
        fun getApp(): App {
            return instance
        }
    }
}