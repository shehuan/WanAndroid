package com.shehuan.wanandroid.ui.nav

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.navi.NaviBean

class NavPresenterImpl(view: NavContract.View) : BasePresenter<NavContract.View>(view), NavContract.Presenter {
    override fun nav() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).nav(),
                object : BaseObserver<List<NaviBean>>() {
                    override fun onSuccess(data: List<NaviBean>) {
                        view.onNavSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onNavError(e)
                    }
                })
    }
}