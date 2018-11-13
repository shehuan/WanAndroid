package com.shehuan.wanandroid.ui.login

import android.content.Intent
import android.graphics.Paint
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.LoginBean
import com.shehuan.wanandroid.bean.event.AccountEvent
import com.shehuan.wanandroid.ui.register.RegisterActivity
import com.shehuan.wanandroid.utils.sp.SpUtil
import com.shehuan.wanandroid.widget.WrapTextWatcher
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : BaseMvpActivity<LoginPresenterImpl>(), LoginContract.View {
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): LoginPresenterImpl {
        return LoginPresenterImpl(this)
    }

    override fun initLoad() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar(R.string.login)

        registerTv.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        registerTv.setOnClickListener {
            RegisterActivity.start(this)
            finish()
        }

        loginPasswordTTL.isPasswordVisibilityToggleEnabled = true
        loginUsernameET.addTextChangedListener(WrapTextWatcher(loginUsernameTTL))
        loginPasswordET.addTextChangedListener(WrapTextWatcher(loginPasswordTTL))

        loginBtn.setOnClickListener {
            if (loginUsernameET.text.isEmpty()) {
                loginUsernameTTL.error = getString(R.string.username_empty)
                loginUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (loginPasswordET.text.isEmpty()) {
                loginPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }

            presenter.login(loginUsernameET.text.toString(), loginPasswordET.text.toString())
        }
    }

    override fun onLoginSuccess(data: LoginBean) {
        SpUtil.setUsername(data.username)
        EventBus.getDefault().post(AccountEvent())
        finish()
    }

    override fun onLoginError(e: ResponseException) {

    }
}
