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
        statusView = StatusView.init(this, id).apply {
            setLoadingView(R.layout.dialog_loading_layout)
            config(StatusViewBuilder.Builder()
                    .showEmptyRetry(false)
                    .setOnErrorRetryClickListener(errorRetry)
                    .build())
        }
    }

    protected fun initToolbar(titleStr: String) {
        toolbar.run {
            title = titleStr
            setSupportActionBar(this)
            setNavigationOnClickListener {
                finish()
            }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}