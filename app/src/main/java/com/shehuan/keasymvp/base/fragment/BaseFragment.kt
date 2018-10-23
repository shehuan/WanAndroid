package com.shehuan.keasymvp.base.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shehuan.keasymvp.base.activity.BaseActivity

abstract class BaseFragment : Fragment() {
    lateinit var activity: BaseActivity

    lateinit var rootView: View

    abstract fun initLayoutResID(): Int

    abstract fun initData()

    abstract fun initView()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(initLayoutResID(), container, false)
        initView()
        return rootView
    }
}