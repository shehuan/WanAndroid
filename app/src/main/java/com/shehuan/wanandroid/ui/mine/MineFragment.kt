package com.shehuan.wanandroid.ui.mine

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment

class MineFragment : BaseMvpFragment<MinePresenterImpl>(), MineContract.View {
    override fun initPresenter(): MinePresenterImpl {
        return MinePresenterImpl(this)
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

    companion object {
        fun newInstance() = MineFragment()
    }
}
