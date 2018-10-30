package com.shehuan.wanandroid.ui.main

import android.content.Intent
import android.os.Handler
import android.support.v4.view.GravityCompat
import android.widget.TextView
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ViewPagerAdapter
import com.shehuan.wanandroid.base.activity.BaseActivity
import com.shehuan.wanandroid.base.activity.BaseMvpActivity
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.event.AccountEvent
import com.shehuan.wanandroid.ui.home.HomeFragment
import com.shehuan.wanandroid.ui.nav.NavFragment
import com.shehuan.wanandroid.ui.chapter.ChapterFragment
import com.shehuan.wanandroid.ui.collection.MyCollectionActivity
import com.shehuan.wanandroid.ui.login.LoginActivity
import com.shehuan.wanandroid.ui.project.ProjectFragment
import com.shehuan.wanandroid.ui.tree.TreeFragment
import com.shehuan.wanandroid.utils.ToastUtil
import com.shehuan.wanandroid.utils.sp.SpUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseMvpActivity<MainPresenterImpl>(), MainContract.View {
    private val TAG: String = MainActivity::class.java.simpleName

    private var isBackPressed: Boolean = false

    private lateinit var usernameTv: TextView

    companion object {
        fun start(context: BaseActivity) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun initPresenter(): MainPresenterImpl {
        return MainPresenterImpl(this)
    }

    override fun loadData() {
        EventBus.getDefault().register(this)
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

        usernameTv = navigationView.getHeaderView(0).findViewById(R.id.usernameTv)
        usernameTv.text = SpUtil.getUsername()
        usernameTv.setOnClickListener {
            if ("登录" == SpUtil.getUsername()) {
                LoginActivity.start(this)
            }
        }

        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_item_collect -> collection()
                R.id.nav_item_setting -> setting()
                R.id.nav_item_about -> about()
                R.id.nav_item_logout -> logout()
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
        if (SpUtil.getCookies().isEmpty()) {
            ToastUtil.showToast(mContext, "请先登录哦")
            return
        }
        MyCollectionActivity.start(mContext)
    }

    private fun setting() {

    }

    private fun about() {

    }

    private fun logout() {
        if (SpUtil.getCookies().isEmpty()) {
            ToastUtil.showToast(mContext, "没登录还想退出？")
            return
        }

        SpUtil.removeCookies()
        SpUtil.removeUsername()
        usernameTv.text = SpUtil.getUsername()
        ToastUtil.showToast(mContext, "退出成功")
    }

    override fun onLogoutSuccess(data: Any) {

    }

    override fun onLogoutError(e: ResponseException) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAccountEvent(event: AccountEvent) {
        usernameTv.text = SpUtil.getUsername()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (isBackPressed) {
                super.onBackPressed()
                return
            }
            isBackPressed = true
            ToastUtil.showToast(mContext, "再按一次退出")
            Handler().postDelayed({ isBackPressed = false }, 2000)
        }
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }
}
