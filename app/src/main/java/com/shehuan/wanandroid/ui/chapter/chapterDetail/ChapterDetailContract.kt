package com.shehuan.wanandroid.ui.chapter.chapterDetail

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean
import com.shehuan.wanandroid.ui.collection.CollectContract

interface ChapterDetailContract {
    interface View : BaseView, CollectContract.View {
        fun onChapterArticleListSuccess(data: ChapterArticleBean)
        fun onChapterArticleListError(e: ResponseException)

        fun onQueryChapterArticleListSuccess(data: ChapterArticleBean)
        fun onQueryChapterArticleListError(e: ResponseException)
    }

    interface Presenter : CollectContract.Presenter {
        fun getChapterArticleList(chapterId: Int, pageNum: Int)

        fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String)
    }
}