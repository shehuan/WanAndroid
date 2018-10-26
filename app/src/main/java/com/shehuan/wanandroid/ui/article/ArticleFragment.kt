package com.shehuan.wanandroid.ui.article

import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ArticleListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean
import kotlinx.android.synthetic.main.fragment_article.*

class ArticleFragment : BaseMvpFragment<ArticlePresenterImpl>(), ArticleContract.View {
    private lateinit var articleListAdapter: ArticleListAdapter

    companion object {
        fun newInstance() = ArticleFragment()
    }

    override fun initPresenter(): ArticlePresenterImpl {
        return ArticlePresenterImpl(this)
    }

    override fun loadData() {
        presenter.getArticleList(0)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_article
    }

    override fun initData() {

    }

    override fun initView() {
        articleListAdapter = ArticleListAdapter(context, null, true)
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        articleRv.layoutManager = linearLayoutManager
        articleRv.adapter = articleListAdapter
    }

    override fun onBannerSuccess(data: List<BannerBean>) {

    }

    override fun onBannerError(e: ResponseException) {

    }

    override fun onArticleListSuccess(data: ArticleBean) {
        articleListAdapter.setNewData(data.datas)
    }

    override fun onArticleListError(e: ResponseException) {

    }
}
