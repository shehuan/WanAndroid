package com.shehuan.wanandroid.ui.nav

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.CommonUtil
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
        navDetailFL.setWebsiteData(articles)
    }

    override fun loadData() {

    }

    private fun FlexboxLayout.setWebsiteData(data: List<ArticlesItem>) {
        for (website in data) {
            val view = TextView(mContext)
            view.setOnClickListener {
                ArticleActivity.start(mContext, website.title, website.link)
            }
            view.text = website.title
            view.setTextColor(resources.getColor(R.color.c515151))
            view.background = resources.getDrawable(R.drawable.website_selecter)
            val padding1 = CommonUtil.dp2px(mContext, 10)
            val padding2 = CommonUtil.dp2px(mContext, 3)
            view.setPadding(padding1, padding2, padding1, padding2)
            val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 12)
            val margin2 = CommonUtil.dp2px(mContext, 10)
            params.setMargins(margin2, margin1, margin2, margin1)
            this.addView(view, params)
        }
    }
}
