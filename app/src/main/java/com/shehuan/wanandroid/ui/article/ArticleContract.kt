package com.shehuan.wanandroid.ui.article

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean

interface ArticleContract {
    interface View : BaseView {
        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onArticleListSuccess(data: ArticleBean)
        fun onArticleListError(e: ResponseException)
    }

    interface Presenter {
        fun getBannerData()

        fun getArticleList(pageNum: Int)
    }
}