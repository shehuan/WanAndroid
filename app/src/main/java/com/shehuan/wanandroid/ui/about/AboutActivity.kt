package com.shehuan.wanandroid.ui.about

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity

class AboutActivity : BaseActivity() {
    override fun initLayoutResID(): Int {
        return R.layout.activity_about
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar("关于")
    }
}
