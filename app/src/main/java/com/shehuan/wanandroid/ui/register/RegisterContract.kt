package com.shehuan.wanandroid.ui.register

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.LoginBean
import com.shehuan.wanandroid.bean.RegisterBean

interface RegisterContract {
    interface View : BaseView {
        fun onRegisterSuccess(data: RegisterBean)
        fun onRegisterError(e: ResponseException)
    }

    interface Presenter {
        fun register(username: String, password: String, repassword: String)
    }
}