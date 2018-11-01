package com.shehuan.wanandroid.ui.tree.treeDetail

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.bean.treeDetail.TreeDetailBean

class TreeDetailPresenterImpl(view: TreeDetailContract.View) : BasePresenter<TreeDetailContract.View>(view), TreeDetailContract.Presenter {
    override fun uncollect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
                object : LoadingObserver<String>(context) {
                    override fun onSuccess(data: String) {
                        view.onUncollectSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onUncollectError(e)
                    }
                })
    }

    override fun collect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).collectArticle(id),
                object : LoadingObserver<String>(context) {
                    override fun onSuccess(data: String) {
                        view.onCollectSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onCollectError(e)
                    }
                })
    }

    override fun getTreeDetail(pageNum: Int, cid: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).treeDetail(pageNum, cid),
                object : BaseObserver<TreeDetailBean>() {
                    override fun onSuccess(data: TreeDetailBean) {
                        view.onTreeDetailSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onTreeDetailError(e)
                    }
                })
    }

}