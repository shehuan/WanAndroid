package com.shehuan.wanandroid.ui.navi

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.navi.NaviBean

interface NaviContract {
    interface View : BaseView {
        fun onNaviSuccess(data: List<NaviBean>)
        fun onNaviError(e: ResponseException)
    }

    interface Presenter {
        fun navi()
    }
}