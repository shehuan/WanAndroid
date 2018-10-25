package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment

class HomeFragment : BaseMvpFragment<HomePresenterImpl>(), HomeContract.View {
    override fun initPresenter(): HomePresenterImpl {
        return HomePresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

    }

    override fun initView() {

    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
