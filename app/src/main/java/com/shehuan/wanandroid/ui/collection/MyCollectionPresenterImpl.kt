package com.shehuan.wanandroid.ui.collection

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.base.net.observer.LoadingObserver
import com.shehuan.wanandroid.bean.article.ArticleBean

class MyCollectionPresenterImpl(view: MyCollectionContract.View) : BasePresenter<MyCollectionContract.View>(view), MyCollectionContract.Presenter {
    override fun cancelCollection(id: Int, originId: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).cancelMyCollection(id, originId),
                object : LoadingObserver<String>(context) {
                    override fun onSuccess(data: String) {
                        view.onCancelCollectionSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onCancelCollectionError(e)
                    }
                })
    }

    override fun getCollectionList(pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).collectArticleList(pageNum),
                object : BaseObserver<ArticleBean>() {
                    override fun onSuccess(data: ArticleBean) {
                        view.onCollectionListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onCollectionListError(e)
                    }
                })
    }
}