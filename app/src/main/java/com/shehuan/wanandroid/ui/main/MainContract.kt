package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException

interface MainContract {
    interface View : BaseView {
        fun onLogoutSuccess(data: String)
        fun onLogoutError(e: ResponseException)
    }

    interface Presenter {
        fun logout()
    }
}