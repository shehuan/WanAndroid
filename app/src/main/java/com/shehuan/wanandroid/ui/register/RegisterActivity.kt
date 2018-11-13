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

    override fun initLoad() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initView() {
        initToolbar(R.string.register)

        registerPasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerRepasswordTTL.isPasswordVisibilityToggleEnabled = true
        registerUsernameET.addTextChangedListener(WrapTextWatcher(registerUsernameTTL))
        registerPasswordET.addTextChangedListener(WrapTextWatcher(registerPasswordTTL))
        registerRepasswordET.addTextChangedListener(WrapTextWatcher(registerRepasswordTTL))

        registerBtn.setOnClickListener {
            if (registerUsernameET.text.isEmpty()) {
                registerUsernameTTL.error = getString(R.string.username_empty)
                registerUsernameTTL.isErrorEnabled = true
                return@setOnClickListener
            }
            if (registerPasswordET.text.isEmpty()) {
                registerPasswordTTL.error = getString(R.string.password_empty)
                return@setOnClickListener
            }

            if (registerRepasswordET.text.isEmpty()) {
                registerRepasswordTTL.error = getString(R.string.repassword_empty)
                return@setOnClickListener
            }

            if (registerPasswordET.text.toString() != registerRepasswordET.text.toString()) {
                registerPasswordTTL.error = getString(R.string.password_unequal)
                registerRepasswordTTL.error = getString(R.string.password_unequal)
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
