package com.shehuan.wanandroid.ui.main

import android.content.Context
import android.content.Intent
import android.support.v4.view.GravityCompat
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.ui.home.HomeFragment
import com.shehuan.wanandroid.ui.nav.NavFragment
import com.shehuan.wanandroid.ui.chapter.ChapterFragment
import com.shehuan.wanandroid.ui.collection.MyCollectionActivity
import com.shehuan.wanandroid.ui.project.ProjectFragment
import com.shehuan.wanandroid.ui.tree.TreeFragment
import com.shehuan.wanandroid.utils.LogUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpActivity<MainPresenterImpl>(), MainContract.View {
    private val TAG: String = MainActivity::class.java.simpleName

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): MainPresenterImpl {
        return MainPresenterImpl(this)
    }

    override fun loadData() {

    }

    override fun initLayoutResID(): Int {
        return R.layout.activity_main
    }

    override fun initData() {

    }

    override fun initView() {
        mainMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_collect -> collection()
                R.id.nav_item_setting -> setting()
                R.id.nav_item_about -> about()
                R.id.nav_item_logout -> about()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }

        mainBottomTabLayout.addTab("首页", R.drawable.homepage, R.drawable.homepage_fill)
        mainBottomTabLayout.addTab("项目", R.drawable.activity, R.drawable.activity_fill)
        mainBottomTabLayout.addTab("体系", R.drawable.manage, R.drawable.manage_fill)
        mainBottomTabLayout.addTab("导航", R.drawable.coordinates, R.drawable.coordinates_fill)
        mainBottomTabLayout.addTab("公众号", R.drawable.chapter, R.drawable.chapter_fill)

        val fragments = arrayListOf<BaseFragment>()
        fragments.add(HomeFragment.newInstance())
        fragments.add(ProjectFragment.newInstance())
        fragments.add(TreeFragment.newInstance())
        fragments.add(NavFragment.newInstance())
        fragments.add(ChapterFragment.newInstance())
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.setFragments(fragments)
        mainViewpager.adapter = viewPagerAdapter
        mainViewpager.offscreenPageLimit = fragments.size
        mainBottomTabLayout.setupWithViewPager(mainViewpager)
    }

    private fun collection() {
        MyCollectionActivity.start(mContext)
    }

    private fun setting() {

    }

    private fun about() {

    }

    private fun logout() {

    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        LogUtil.e(TAG, "onFriedSuccess")
    }

    override fun onFriendError(e: ResponseException) {
        LogUtil.e(TAG, "onFriendError")
    }
}
