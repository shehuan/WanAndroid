package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean

class HomePresenterImpl(view: HomeContract.View) : BasePresenter<HomeContract.View>(view), HomeContract.Presenter {
    override fun uncollect(id: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).uncollectArticle(id),
                object : LoadingObserver<String>(context, false, true) {
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

    override fun getBannerData() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).banner(),
                object : BaseObserver<List<BannerBean>>() {
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
                object : BaseObserver<ArticleBean>() {
                    override fun onSuccess(data: ArticleBean) {
                        view.onArticleListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onArticleListError(e)
                    }
                })
    }
}