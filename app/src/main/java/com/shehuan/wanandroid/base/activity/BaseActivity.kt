package com.shehuan.wanandroid.base.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

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
}