package com.shehuan.wanandroid.ui.login

import android.content.Context
import android.content.Intent
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.LoginBean
import com.shehuan.wanandroid.ui.main.MainActivity

class LoginActivity : BaseMvpActivity<LoginPresenterImpl>(), LoginContract.View {
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl(this)
    }

    override fun loadData() {
        presenter.login("shehuan", "wanandroid320955")
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onLoginSuccess(data: LoginBean) {
        MainActivity.start(mContext)
        finish()
    }

    override fun onLoginError(e: ResponseException) {

    }
}
