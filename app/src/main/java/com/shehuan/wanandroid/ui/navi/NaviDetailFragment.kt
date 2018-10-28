package com.shehuan.wanandroid.ui.navi

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.NaviDetailAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import kotlinx.android.synthetic.main.fragment_navi_detail.*

private const val ARTICLES = "articles"

class NaviDetailFragment : BaseFragment() {
    private lateinit var naviDetailAdapter: NaviDetailAdapter

    private lateinit var articles: ArrayList<ArticlesItem>

    companion object {
        fun newInstance(param: ArrayList<ArticlesItem>) =
                NaviDetailFragment().apply {
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
        naviDetailAdapter = NaviDetailAdapter(context, articles, false)
        val gridLayoutManager = GridLayoutManager(context, 2)
        naviDetailRv.layoutManager = gridLayoutManager
        naviDetailRv.adapter = naviDetailAdapter
    }

    override fun loadData() {

    }
}
