package com.shehuan.wanandroid.ui.nav

import android.os.Bundle
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.addCommonView
import kotlinx.android.synthetic.main.fragment_navi_detail.*

private const val ARTICLES = "articles"

class NavDetailFragment : BaseFragment() {

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
        for (website in articles) {
            navDetailFL.addCommonView(mContext, website.title, R.color.c2C2C2C, R.drawable.website_selecter) {
                ArticleActivity.start(mContext, website.title, website.link)
            }
        }
    }

    override fun initLoad() {

    }
}
