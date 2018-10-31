package com.shehuan.wanandroid.ui.query

import android.content.Intent
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.bean.HotKeyBean
import com.shehuan.wanandroid.bean.query.QueryBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.CommonUtil
import com.shehuan.wanandroid.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_query.*
import kotlinx.android.synthetic.main.toolbar_layout.*

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
        presenter.getHotKey()
        presenter.getFriendData()
    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_query
    }

    override fun initData() {

    }

    override fun initView() {
        toolbar.title = "搜索"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_query_menu_layout, menu)
        val queryItem = menu?.findItem(R.id.action_query)
        val searchView: SearchView = MenuItemCompat.getActionView(queryItem) as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.isIconified = false
        searchView.queryHint = "多个关键词，用空格隔开"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                ToastUtil.showToast(mContext, "submit")
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQuerySuccess(data: QueryBean) {

    }

    override fun onQueryError(e: ResponseException) {

    }

    override fun onHotKeySuccess(data: List<HotKeyBean>) {
        hotKeyFL.setHotKeyData(data)
    }

    override fun onHotKeyError(e: ResponseException) {

    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        websiteFL.setWebsiteData(data)
    }

    override fun onFriendError(e: ResponseException) {

    }

    private fun FlexboxLayout.setHotKeyData(data: List<HotKeyBean>) {
        for (hotKey in data) {
            val view = TextView(mContext)
            view.setOnClickListener {

            }
            view.text = hotKey.name
            view.background = resources.getDrawable(R.drawable.hotkey_selector)
            val padding1 = CommonUtil.dp2px(mContext, 10)
            val padding2 = CommonUtil.dp2px(mContext, 3)
            view.setPadding(padding1, padding2, padding1, padding2)
            val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 6)
            val margin2 = CommonUtil.dp2px(mContext, 10)
            params.setMargins(margin2, margin1, margin2, margin1)
            hotKeyFL.addView(view, params)
        }
    }

    private fun FlexboxLayout.setWebsiteData(data: List<FriendBean>) {
        for (website in data) {
            val view = TextView(mContext)
            view.setOnClickListener {
                ArticleActivity.start(mContext, website.name, website.link)
            }
            view.text = website.name
            view.background = resources.getDrawable(R.drawable.website_selecter)
            val padding1 = CommonUtil.dp2px(mContext, 12)
            val padding2 = CommonUtil.dp2px(mContext, 5)
            view.setPadding(padding1, padding2, padding1, padding2)
            val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 6)
            val margin2 = CommonUtil.dp2px(mContext, 10)
            params.setMargins(margin2, margin1, margin2, margin1)
            websiteFL.addView(view, params)
        }
    }
}
