package com.shehuan.wanandroid.ui.project

import android.text.Html
import android.view.View
import com.google.android.material.tabs.TabLayout
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

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getProjectCategory()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_project
    }

    override fun initData() {

    }

    override fun initView() {
        initStatusView(R.id.projectViewPager) {
            initLoad()
        }
    }

    override fun onProjectCategorySuccess(data: List<ProjectCategoryBean>) {
        statusView.showContentView()
        projectTabLayout.visibility = View.VISIBLE
        val titles = arrayListOf<String>()
        val fragments = arrayListOf<BaseFragment>()
        titles.add(getString(R.string.new_project))
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
        statusView.showErrorView()
    }
}
