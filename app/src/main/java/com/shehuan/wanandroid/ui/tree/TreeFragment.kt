package com.shehuan.wanandroid.ui.tree

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment

class TreeFragment : BaseMvpFragment<TreePresenterImpl>(), TreeContract.View {
    override fun initPresenter(): TreePresenterImpl {
        return TreePresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_tree
    }

    override fun initData() {

    }

    override fun initView() {

    }

    companion object {
        fun newInstance() = TreeFragment()
    }
}
