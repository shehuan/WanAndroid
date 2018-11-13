package com.shehuan.wanandroid.ui.nav

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.bean.navi.NaviBean
import com.shehuan.wanandroid.ui.website.HotWebsiteFragment
import com.shehuan.wanandroid.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_navi.*

class NavFragment : BaseMvpFragment<NavPresenterImpl>(), NavContract.View {
    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    companion object {
        fun newInstance() = NavFragment()
    }

    override fun initPresenter(): NavPresenterImpl {
        return NavPresenterImpl(this)
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.nav()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_navi
    }

    override fun initData() {

    }

    override fun initView() {
        naviTabLayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager?.beginTransaction()
                        ?.hide(fragments[oldTabIndex])
                        ?.show(fragments[newTabIndex])
                        ?.commit()
            }
        })

        initStatusView(navRootLayout) {
            initLoad()
        }
    }

    override fun onNavSuccess(data: List<NaviBean>) {
        statusView.showContentView()
        val tabNames = arrayListOf<String>()
        tabNames.add(getString(R.string.hot_website))
        fragments.add(HotWebsiteFragment.newInstance())
        for (navBean in data) {
            tabNames.add(navBean.name)
            fragments.add(NavDetailFragment.newInstance(navBean.articles as ArrayList<ArticlesItem>))
        }
        naviTabLayout.addTabs(tabNames)
        initDetailFragment()
    }

    override fun onNavError(e: ResponseException) {

    }

    private fun initDetailFragment() {
        val transition = fragmentManager!!.beginTransaction()
        for (f in fragments) {
            transition.add(R.id.naviDetailContainer, f).hide(f)
        }
        transition.show(fragments[0])
        transition.commit()
    }
}
