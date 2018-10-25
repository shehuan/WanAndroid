package com.shehuan.wanandroid.ui.tree

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.tree.TreeBean

class TreeFragment : BaseMvpFragment<TreePresenterImpl>(), TreeContract.View {
    companion object {
        fun newInstance() = TreeFragment()
    }

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

    override fun onTreeSuccess(data: List<TreeBean>) {

    }

    override fun onTreeError(e: ResponseException) {

    }
}
