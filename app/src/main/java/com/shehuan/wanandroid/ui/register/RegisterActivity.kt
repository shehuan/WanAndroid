package com.shehuan.wanandroid.ui.register

import android.content.Intent
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.RegisterBean
import com.shehuan.wanandroid.bean.event.AccountEvent
import com.shehuan.wanandroid.utils.sp.SpUtil
import com.shehuan.wanandroid.widget.WrapTextWatcher
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus

class RegisterActivity : BaseMvpActivity<RegisterPresenterImpl>(), RegisterContract.View {
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): RegisterPresenterImpl {
        return RegisterPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar("注册")

        registerPasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerRepasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerUsernameET.addTextChangedListener(WrapTextWatcher(registerUsernameTTL))
        registerPasswordET.addTextChangedListener(WrapTextWatcher(registerPasswordTTL))
        registerRepasswordET.addTextChangedListener(WrapTextWatcher(registerRepasswordTTL))

        registerBtn.setOnClickListener {
            if (registerUsernameET.text.isEmpty()) {
                registerUsernameTTL.error = "用户名不能为空"
                registerUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (registerPasswordET.text.isEmpty()) {
                registerPasswordTTL.error = "密码不能为空"
                return@setOnClickListener
            }

            if (registerRepasswordET.text.isEmpty()) {
                registerRepasswordTTL.error = "确认密码不能为空"
                return@setOnClickListener
            }

            if (registerPasswordET.text.toString() != registerRepasswordET.text.toString()) {
                registerPasswordTTL.error = "密码不一致"
                registerRepasswordTTL.error = "密码不一致"
                return@setOnClickListener
            }

            presenter.register(registerUsernameET.text.toString(),
                    registerPasswordET.text.toString(),
                    registerRepasswordET.text.toString())
        }
    }

    override fun onRegisterSuccess(data: RegisterBean) {
        SpUtil.setUsername(data.username)
        EventBus.getDefault().post(AccountEvent())
        finish()
    }

    override fun onRegisterError(e: ResponseException) {

    }
}
