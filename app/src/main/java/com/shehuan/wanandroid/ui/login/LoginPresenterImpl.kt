package com.shehuan.wanandroid.ui.login

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.bean.LoginBean

class LoginPresenterImpl(view: LoginContract.View) : BasePresenter<LoginContract.View>(view), LoginContract.Presenter {
    override fun login(username: String, password: String) {
        val params = hashMapOf<String, String>()
        params["username"] = username
        params["password"] = password
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).login(params),
                object : LoadingObserver<LoginBean>(context) {
                    override fun onSuccess(data: LoginBean) {
                        view.onLoginSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onLoginError(e)
                    }
                })
    }

}