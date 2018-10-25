package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.articleList.ArticleListBean
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.bean.newProject.NewProjectBean

interface SampleContract {
    interface View : BaseView {
        fun onBannerSuccess(data: List<BannerBean>)
        fun onBannerError(e: ResponseException)

        fun onFriedSuccess(data: List<FriendBean>)
        fun onFriendError(e: ResponseException)

        fun onArticleListSuccess(data: ArticleListBean)
        fun onArticleListError(e: ResponseException)

        fun onNewProjectSuccess(data: NewProjectBean)
        fun onNewProjectError(e: ResponseException)

//        fun onHotKeySuccess(data: NewProjectBean)
//        fun onHotKeyError(e: ResponseException)
    }

    interface Presenter {
        fun getBannerData()

        fun getFriendData()

        fun getArticleList(pageNum: Int)

        fun getNewProject(pageNum: Int)

//        fun hotKeySearch(hotKey: String)
    }
}