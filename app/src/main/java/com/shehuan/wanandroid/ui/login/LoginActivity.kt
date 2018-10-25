package com.shehuan.wanandroid.ui.login

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.LoginBean

class LoginActivity : BaseMvpActivity<LoginPresenterImpl>(), LoginContract.View {
    override fun initPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onLoginSuccess(data: LoginBean) {

    }

    override fun onLoginError(e: ResponseException) {

    }
}
