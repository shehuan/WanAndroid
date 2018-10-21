package com.shehuan.keasymvp.mvp.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    lateinit var context: Context

    abstract fun initLayoutResID(): Int

    abstract fun initData()

    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResID())
        context = this

        initData()
        initView()
    }
}