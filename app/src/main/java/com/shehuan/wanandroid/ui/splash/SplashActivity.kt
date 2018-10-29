package com.shehuan.wanandroid.ui.splash

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.ui.login.LoginActivity
import com.shehuan.wanandroid.ui.main.MainActivity
import com.shehuan.wanandroid.utils.sp.SpUtil

class SplashActivity : BaseActivity() {
    override fun initLayoutResID(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {

    }

    override fun initView() {
        if (!SpUtil.getCookies().isEmpty()) {
            MainActivity.start(mContext)
        } else {
            LoginActivity.start(mContext)
        }
    }
}
