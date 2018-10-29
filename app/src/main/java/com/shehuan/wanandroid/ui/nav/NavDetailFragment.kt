package com.shehuan.wanandroid.ui.nav

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.NavDetailAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import kotlinx.android.synthetic.main.fragment_navi_detail.*

private const val ARTICLES = "articles"

class NavDetailFragment : BaseFragment() {
    private lateinit var navDetailAdapter: NavDetailAdapter

    private lateinit var articles: ArrayList<ArticlesItem>

    companion object {
        fun newInstance(param: ArrayList<ArticlesItem>) =
                NavDetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARTICLES, param)
                    }
                }
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_navi_detail
    }

    override fun initData() {
        arguments?.let {
            articles = it.getParcelableArrayList<ArticlesItem>(ARTICLES)
        }
    }

    override fun initView() {
        navDetailAdapter = NavDetailAdapter(context, articles, false)
        navDetailAdapter.setOnItemClickListener { _, data, _ ->
            ArticleActivity.start(mContext, data.title, data.link)
        }
        val gridLayoutManager = GridLayoutManager(context, 2)
        naviDetailRv.layoutManager = gridLayoutManager
        naviDetailRv.adapter = navDetailAdapter
    }

    override fun loadData() {

    }
}
