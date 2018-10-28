package com.shehuan.wanandroid.ui.chapter.chapterDetail

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.chapter.ChapterArticleBean

interface ChapterDetailContract {
    interface View : BaseView {
        fun onChapterArticleListSuccess(data: ChapterArticleBean)
        fun onChapterArticleListError(e: ResponseException)

        fun onQueryChapterArticleListSuccess(data: ChapterArticleBean)
        fun onQueryChapterArticleListError(e: ResponseException)
    }

    interface Presenter {
        fun getChapterArticleList(chapterId: Int, pageNum: Int)

        fun queryChapterArticle(chapterId: Int, pageNum: Int, k: String)
    }
}