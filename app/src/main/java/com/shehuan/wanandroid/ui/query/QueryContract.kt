package com.shehuan.wanandroid.ui.query

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean
import com.shehuan.wanandroid.ui.collection.CollectContract

interface QueryContract {
    interface View : BaseView, CollectContract.View {
        fun onQuerySuccess(data: QueryBean)
        fun onQueryError(e: ResponseException)

        fun onHotKeySuccess(data: List<HotKeyBean>)
        fun onHotKeyError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun query(pageNum: Int, k: String, showLoading: Boolean)

        fun getHotKey()
    }
}