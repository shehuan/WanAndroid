package com.shehuan.keasymvp.test.main

import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.R
import com.shehuan.keasymvp.mvp.activity.BaseMvpActivity
import com.shehuan.keasymvp.mvp.net.exception.ResponseException
import com.shehuan.keasymvp.test.bean.FriendBean
import com.shehuan.keasymvp.test.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<SamplePresenterImpl>(), SampleContract.View {
    private val TAG: String = MainActivity::class.java.simpleName

    override fun initPresenter(): SamplePresenterImpl {
        return SamplePresenterImpl(this)
    }

    override fun loadData() {
        presenter.getBannerData()
        presenter.getFriendData()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onBannerSuccess(data: List<BannerBean>) {
        LogUtil.e(TAG, "onBannerSuccess")
    }

    override fun onBannerError(e: ResponseException) {
        LogUtil.e(TAG, "onBannerError")
    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        LogUtil.e(TAG, "onFriedSuccess")
    }

    override fun onFriendError(e: ResponseException) {
        LogUtil.e(TAG, "onFriendError")
    }
}
