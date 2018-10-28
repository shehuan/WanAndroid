package com.shehuan.wanandroid.ui.chapter

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.ChapterBean

interface ChapterContract {
    interface View : BaseView {
        fun onChapterSuccess(data: List<ChapterBean>)
        fun onChapterError(e: ResponseException)
    }

    interface Presenter {
        fun getChapter()
    }
}