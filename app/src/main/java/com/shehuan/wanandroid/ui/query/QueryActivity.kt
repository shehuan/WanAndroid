package com.shehuan.wanandroid.ui.query

import android.content.Intent
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean

class QueryActivity : BaseMvpActivity<QueryPresenterImpl>(), QueryContract.View {
    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, QueryActivity::class.java)
            context.startActivity(intent)
        }
    }

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

    override fun onFriedSuccess(data: List<FriendBean>) {

    }

    override fun onFriendError(e: ResponseException) {

    }
}
