package com.shehuan.keasymvp.test.main

import android.graphics.Color
import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.R
import com.shehuan.keasymvp.mvp.activity.BaseMvpActivity
import com.shehuan.keasymvp.mvp.net.exception.ResponseException
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<SamplePresenterImpl>(), SampleContract.View {

    override fun initPresenter(): SamplePresenterImpl {
        return SamplePresenterImpl(this)
    }

    override fun loadData() {
        presenter.getBannerData()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onBannerSuccess(data: List<BannerBean>) {

    }

    override fun onBannerError(e: ResponseException) {

    }

}
