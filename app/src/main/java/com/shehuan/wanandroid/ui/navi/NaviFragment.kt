package com.shehuan.wanandroid.ui.navi

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseFragment
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.navi.ArticlesItem
import com.shehuan.wanandroid.bean.navi.NaviBean
import com.shehuan.wanandroid.widget.VerticalTabLayout
import kotlinx.android.synthetic.main.fragment_mine.*

class NaviFragment : BaseMvpFragment<NaviPresenterImpl>(), NaviContract.View {
    private val fragments: ArrayList<BaseFragment> = arrayListOf()

    companion object {
        fun newInstance() = NaviFragment()
    }

    override fun initPresenter(): NaviPresenterImpl {
        return NaviPresenterImpl(this)
    }

    override fun loadData() {
        presenter.navi()
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_mine
    }

    override fun initData() {

    }

    override fun initView() {
        naviTabLayout.setOnTabClickListener(object : VerticalTabLayout.OnTabClickListener {
            override fun onTabClick(oldTabIndex: Int, newTabIndex: Int) {
                fragmentManager!!.beginTransaction()
                        .hide(fragments[oldTabIndex])
                        .show(fragments[newTabIndex])
                        .commit()
            }
        })
    }

    override fun onNaviSuccess(data: List<NaviBean>) {
        val tabNames = arrayListOf<String>()
        for (naviBean in data) {
            tabNames.add(naviBean.name)
            fragments.add(NaviDetailFragment.newInstance(naviBean.articles as ArrayList<ArticlesItem>))
        }
        naviTabLayout.addTabs(tabNames)
        initDetailFragment()
    }

    override fun onNaviError(e: ResponseException) {

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
