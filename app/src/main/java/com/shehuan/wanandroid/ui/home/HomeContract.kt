package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.articleList.ArticleListBean

interface HomeContract {
    interface View : BaseView {
        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onArticleListSuccess(data: ArticleListBean)
        fun onArticleListError(e: ResponseException)
    }

    interface Presenter {
        fun getBannerData()

        fun getArticleList(pageNum: Int)
    }
}