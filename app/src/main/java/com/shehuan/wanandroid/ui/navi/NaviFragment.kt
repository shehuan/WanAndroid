package com.shehuan.wanandroid.ui.navi

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment

class NaviFragment : BaseMvpFragment<NaviPresenterImpl>(), NaviContract.View {
    companion object {
        fun newInstance() = NaviFragment()
    }

    override fun initPresenter(): NaviPresenterImpl {
        return NaviPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_mine
    }

    override fun initData() {

    }

    override fun initView() {

    }
}
