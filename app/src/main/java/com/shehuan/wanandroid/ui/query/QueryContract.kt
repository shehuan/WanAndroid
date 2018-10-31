package com.shehuan.wanandroid.ui.query

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean

interface QueryContract {
    interface View : BaseView {
        fun onQuerySuccess(data: QueryBean)
        fun onQueryError(e: ResponseException)

        fun onHotKeySuccess(data: List<HotKeyBean>)
        fun onHotKeyError(e: ResponseException)
    }

    interface Presenter {
        fun query(pageNum: Int, k: String)

        fun getHotKey()
    }
}