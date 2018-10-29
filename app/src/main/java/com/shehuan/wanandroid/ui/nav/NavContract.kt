package com.shehuan.wanandroid.ui.nav

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.navi.NaviBean

interface NavContract {
    interface View : BaseView {
        fun onNavSuccess(data: List<NaviBean>)
        fun onNavError(e: ResponseException)
    }

    interface Presenter {
        fun nav()
    }
}