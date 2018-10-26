package com.shehuan.wanandroid.ui.officialAccounts

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.OfficialAccountBean
import com.shehuan.wanandroid.bean.officialAccountArticle.OfficialAccountArticleBean

class OfficialAccountFragment : BaseMvpFragment<OfficialAccountPresenterImpl>(), OfficialAccountContract.View {
    companion object {
        fun newInstance() = OfficialAccountFragment()
    }

    override fun initPresenter(): OfficialAccountPresenterImpl {
        return OfficialAccountPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_official_accounts
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onOfficialAccountSuccess(data: List<OfficialAccountBean>) {

    }

    override fun onOfficialAccountError(e: ResponseException) {

    }

    override fun onOfficialAccountArticleListSuccess(data: OfficialAccountArticleBean) {

    }

    override fun onOfficialAccountArticleListError(e: ResponseException) {

    }

    override fun onQueryOfficialAccountArticleListSuccess(data: OfficialAccountArticleBean) {

    }

    override fun onQueryOfficialAccountArticleListError(e: ResponseException) {

    }
}
