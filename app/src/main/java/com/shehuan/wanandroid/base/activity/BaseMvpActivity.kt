package com.shehuan.wanandroid.base.activity

import android.os.Bundle
import com.shehuan.wanandroid.base.BasePresenter

abstract class BaseMvpActivity<P : BasePresenter<*>> : BaseActivity() {
    lateinit var presenter: P

    abstract fun initPresenter(): P

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = initPresenter()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }
}