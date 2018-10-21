package com.shehuan.keasymvp.test.main

import com.shehuan.keasymvp.test.bean.BannerBean
import com.shehuan.keasymvp.mvp.BasePresenter
import com.shehuan.keasymvp.mvp.net.BaseObserver
import com.shehuan.keasymvp.mvp.net.RequestManager
import com.shehuan.keasymvp.mvp.net.RetrofitManager
import com.shehuan.keasymvp.mvp.net.exception.ResponseException
import com.shehuan.keasymvp.test.apis.WanAndroidApis

class SamplePresenterImpl(view: SampleContract.View) : BasePresenter<SampleContract.View>(view), SampleContract.Presenter {
    override fun getBannerData() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).banner(),
                object : BaseObserver<List<BannerBean>>(true) {
                    override fun onSuccess(data: List<BannerBean>) {
                        view.onBannerSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onBannerError(e)
                    }
                })
    }
}