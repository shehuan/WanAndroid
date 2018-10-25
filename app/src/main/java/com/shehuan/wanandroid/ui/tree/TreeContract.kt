package com.shehuan.wanandroid.ui.tree

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.tree.TreeBean

interface TreeContract {
    interface View : BaseView {
        fun onTreeSuccess(data: List<TreeBean>)
        fun onTreeError(e: ResponseException)
    }

    interface Presenter {
        fun getTree()
    }
}