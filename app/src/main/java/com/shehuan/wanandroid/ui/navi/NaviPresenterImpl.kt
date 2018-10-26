package com.shehuan.wanandroid.ui.navi

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.navi.NaviBean

class NaviPresenterImpl(view: NaviContract.View) : BasePresenter<NaviContract.View>(view), NaviContract.Presenter {
    override fun navi() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).navi(),
                object : BaseObserver<List<NaviBean>>(true) {
                    override fun onSuccess(data: List<NaviBean>) {
                        view.onNaviSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onNaviError(e)
                    }
                })
    }
}