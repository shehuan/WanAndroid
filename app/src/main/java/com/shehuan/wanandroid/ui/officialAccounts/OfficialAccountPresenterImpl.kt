package com.shehuan.wanandroid.ui.officialAccounts

import com.shehuan.wanandroid.apis.WanAndroidApis
import com.shehuan.wanandroid.base.BasePresenter
import com.shehuan.wanandroid.base.net.RequestManager
import com.shehuan.wanandroid.base.net.RetrofitManager
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.base.net.observer.BaseObserver
import com.shehuan.wanandroid.bean.OfficialAccountBean
import com.shehuan.wanandroid.bean.officialAccountArticle.OfficialAccountArticleBean

class OfficialAccountPresenterImpl(view: OfficialAccountContract.View) : BasePresenter<OfficialAccountContract.View>(view), OfficialAccountContract.Presenter {
    override fun getOfficialAccount() {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).officialAccount(),
                object : BaseObserver<List<OfficialAccountBean>>(true) {
                    override fun onSuccess(data: List<OfficialAccountBean>) {
                        view.onOfficialAccountSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onOfficialAccountError(e)
                    }
                })
    }

    override fun getOfficialAccountArticleList(officialAccountId: Int, pageNum: Int) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).officialAccountArticleList(officialAccountId, pageNum),
                object : BaseObserver<OfficialAccountArticleBean>(true) {
                    override fun onSuccess(data: OfficialAccountArticleBean) {
                        view.onOfficialAccountArticleListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onOfficialAccountArticleListError(e)
                    }
                })
    }

    override fun queryOfficialAccountArticle(officialAccountId: Int, pageNum: Int, k: String) {
        RequestManager.execute(this, RetrofitManager.create(WanAndroidApis::class.java).queryOfficialAccountArticle(officialAccountId, pageNum, k),
                object : BaseObserver<OfficialAccountArticleBean>(true) {
                    override fun onSuccess(data: OfficialAccountArticleBean) {
                        view.onQueryOfficialAccountArticleListSuccess(data)
                    }

                    override fun onError(e: ResponseException) {
                        view.onQueryOfficialAccountArticleListError(e)
                    }
                })
    }
}