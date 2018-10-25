package com.shehuan.wanandroid.ui.main

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.bean.newProject.NewProjectBean
import com.shehuan.wanandroid.ui.home.HomeFragment
import com.shehuan.wanandroid.ui.mine.MineFragment
import com.shehuan.wanandroid.ui.tree.TreeFragment
import com.shehuan.wanandroid.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<SamplePresenterImpl>(), SampleContract.View {
    private val TAG: String = MainActivity::class.java.simpleName

    override fun initPresenter(): SamplePresenterImpl {
        return SamplePresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        bottomTabLayout.addTab("主页", R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        bottomTabLayout.addTab("体系", R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        bottomTabLayout.addTab("我的", R.mipmap.ic_launcher, R.mipmap.ic_launcher)

        val fragments = arrayListOf<BaseFragment>()
        fragments.add(HomeFragment.newInstance())
        fragments.add(TreeFragment.newInstance())
        fragments.add(MineFragment.newInstance())
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.setFragments(fragments)
        mainViewpager.adapter = viewPagerAdapter
        bottomTabLayout.setupWithViewPager(mainViewpager)
    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        LogUtil.e(TAG, "onFriedSuccess")
    }

    override fun onFriendError(e: ResponseException) {
        LogUtil.e(TAG, "onFriendError")
    }

    override fun onNewProjectSuccess(data: NewProjectBean) {

    }

    override fun onNewProjectError(e: ResponseException) {

    }
}
