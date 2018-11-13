package com.shehuan.wanandroid.ui.splash

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.ui.main.MainActivity

class SplashActivity : BaseActivity() {
    override fun initLayoutResID(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {

    }

    override fun initView() {
        MainActivity.start(this)
        finish()
    }

    override fun initLoad() {

    }
}
