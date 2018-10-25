package com.shehuan.wanandroid.ui.tree

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.tree.TreeBean

class TreePresenterImpl(view: TreeContract.View) : BasePresenter<TreeContract.View>(view), TreeContract.Presenter {
    override fun getTree() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).tree(),
                object : BaseObserver<List<TreeBean>>(true) {
                    override fun onSuccess(data: List<TreeBean>) {
                        view.onTreeSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onTreeError(e)
                    }
                })
    }
}