package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean

class HomePresenterImpl(view: HomeContract.View) : BasePresenter<HomeContract.View>(view), HomeContract.Presenter {
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

    override fun getArticleList(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).articleList(pageNum),
                object : BaseObserver<ArticleBean>(true) {
                    override fun onSuccess(data: ArticleBean) {
                        view.onArticleListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onArticleListError(e)
                    }
                })
    }
}