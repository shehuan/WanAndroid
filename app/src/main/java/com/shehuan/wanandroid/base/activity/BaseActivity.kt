package com.shehuan.wanandroid.base.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.shehuan.library.StatusView
import com.shehuan.library.StatusViewBuilder
import com.shehuan.wanandroid.R
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context

    abstract fun initLayoutResID(): Int

    abstract fun initData()

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResID())
        mContext = this

        initData()
        initView()
    }

    protected lateinit var statusView: StatusView

    protected fun initStatusView(id: Int, errorRetry: (View) -> Unit) {
        statusView = StatusView.init(this, id)
        statusView.setLoadingView(R.layout.dialog_loading_layout)
        statusView.config(StatusViewBuilder.Builder()
                .setOnErrorRetryClickListener(errorRetry)
                .build())
    }

    protected fun initToolbar(title: String) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}