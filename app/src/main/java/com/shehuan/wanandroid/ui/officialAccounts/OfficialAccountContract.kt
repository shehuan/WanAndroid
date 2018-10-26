package com.shehuan.wanandroid.ui.officialAccounts

import com.shehuan.wanandroid.base.BaseView
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.OfficialAccountBean
import com.shehuan.wanandroid.bean.officialAccountArticle.OfficialAccountArticleBean

interface OfficialAccountContract {
    interface View : BaseView {
        fun onOfficialAccountSuccess(data: List<OfficialAccountBean>)
        fun onOfficialAccountError(e: ResponseException)

        fun onOfficialAccountArticleListSuccess(data: OfficialAccountArticleBean)
        fun onOfficialAccountArticleListError(e: ResponseException)

        fun onQueryOfficialAccountArticleListSuccess(data: OfficialAccountArticleBean)
        fun onQueryOfficialAccountArticleListError(e: ResponseException)
    }

    interface Presenter {
        fun getOfficialAccount()

        fun getOfficialAccountArticleList(officialAccountId: Int, pageNum: Int)

        fun queryOfficialAccountArticle(officialAccountId: Int, pageNum: Int, k: String)
    }
}