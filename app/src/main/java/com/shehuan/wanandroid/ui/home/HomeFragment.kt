package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.ui.article.ArticleFragment
import com.shehuan.wanandroid.ui.project.ProjectFragment
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseMvpFragment<HomePresenterImpl>(), HomeContract.View {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initPresenter(): HomePresenterImpl {
        return HomePresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

    }

    override fun initView() {
        val fragments = arrayListOf<BaseFragment>()
        val titles = arrayListOf<String>()

        fragments.add(ArticleFragment.newInstance())
        fragments.add(ProjectFragment.newInstance())

        titles.add("文章")
        titles.add("项目")

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.setFragmentsAndTitles(fragments, titles)
        homeViewPager.adapter = viewPagerAdapter
        homeTabLayout.setupWithViewPager(homeViewPager)
    }
}
