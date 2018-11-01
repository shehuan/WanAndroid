package com.shehuan.wanandroid.ui.project

import android.support.design.widget.TabLayout
import android.text.Html
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.ProjectCategoryBean
import com.shehuan.wanandroid.ui.project.projectDetail.ProjectDetailFragment
import kotlinx.android.synthetic.main.fragment_project.*

class ProjectFragment : BaseMvpFragment<ProjectPresenterImpl>(), ProjectContract.View {
    companion object {
        fun newInstance() = ProjectFragment()
    }

    override fun initPresenter(): ProjectPresenterImpl {
        return ProjectPresenterImpl(this)
    }

    override fun loadData() {
        presenter.getProjectCategory()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {

    }

    override fun initView() {

    }

    override fun onProjectCategorySuccess(data: List<ProjectCategoryBean>) {
        val titles = arrayListOf<String>()
        val fragments = arrayListOf<BaseFragment>()
        titles.add("最新项目")
        fragments.add(ProjectDetailFragment.newInstance(-1))
        for (category in data) {
            titles.add(Html.fromHtml(category.name).toString())
            fragments.add(ProjectDetailFragment.newInstance(category.id))
        }

        val viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter.setFragmentsAndTitles(fragments, titles)
        projectViewPager.offscreenPageLimit = data.size + 1
        projectViewPager.adapter = viewPagerAdapter
        projectTabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        projectTabLayout.setupWithViewPager(projectViewPager)
    }

    override fun onProjectCategoryError(e: ResponseException) {

    }
}
