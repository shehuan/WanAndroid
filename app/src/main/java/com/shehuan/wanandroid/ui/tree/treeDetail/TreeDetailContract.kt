package com.shehuan.wanandroid.ui.tree.treeDetail

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.treeDetail.TreeDetailBean

interface TreeDetailContract {
    interface View : BaseView {
        fun onTreeDetailSuccess(data: TreeDetailBean)
        fun onTreeDetailError(e: ResponseException)
    }

    interface Presenter {
        fun getTreeDetail(pageNum: Int, cid: Int)
    }
}