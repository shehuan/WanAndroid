package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean
import com.shehuan.wanandroid.ui.collection.CollectContract

interface HomeContract {
    interface View : BaseView, CollectContract.View {
        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onArticleListSuccess(data: ArticleBean)
        fun onArticleListError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun getBannerData()

        fun getArticleList(pageNum: Int)
    }
}