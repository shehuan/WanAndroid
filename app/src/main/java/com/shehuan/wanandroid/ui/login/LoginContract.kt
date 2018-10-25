package com.shehuan.wanandroid.ui.login

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.LoginBean

interface LoginContract {
    interface View : BaseView {
        fun onLoginSuccess(data: LoginBean)
        fun onLoginError(e: ResponseException)
    }

    interface Presenter {
        fun login(username: String, password: String)
    }
}