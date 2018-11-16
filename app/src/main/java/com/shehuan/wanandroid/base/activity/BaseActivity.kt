package com.shehuan.wanandroid.base.activity

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.shehuan.statusview.StatusView
import com.shehuan.statusview.StatusViewBuilder
import com.shehuan.wanandroid.R
import kotlinx.android.synthetic.main.toolbar_layout.*

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: BaseActivity

    @LayoutRes
    abstract fun initLayoutResID(): Int

    abstract fun initData()

    abstract fun initView()

    abstract fun initLoad()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResID())
        mContext = this

        initData()
        initView()
        initLoad()
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

    protected fun initToolbar(@StringRes titleId: Int) {
        initToolbar(getString(titleId))
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