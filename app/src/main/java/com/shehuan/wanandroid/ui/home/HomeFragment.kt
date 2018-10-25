package com.shehuan.wanandroid.ui.home

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.articleList.ArticleListBean

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

    }

    override fun onBannerSuccess(data: List<BannerBean>) {

    }

    override fun onBannerError(e: ResponseException) {

    }

    override fun onArticleListSuccess(data: ArticleListBean) {

    }

    override fun onArticleListError(e: ResponseException) {

    }
}
