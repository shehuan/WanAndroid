package com.shehuan.wanandroid.ui.query

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean

class QueryActivity : BaseMvpActivity<QueryPresenterImpl>(), QueryContract.View {
    override fun initPresenter(): QueryPresenterImpl {
        return QueryPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_query
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onQuerySuccess(data: QueryBean) {

    }

    override fun onQueryError(e: ResponseException) {

    }

    override fun onHotKeySuccess(data: List<HotKeyBean>) {

    }

    override fun onHotKeyError(e: ResponseException) {

    }
}
