package com.shehuan.wanandroid.ui.home

import android.support.v7.widget.LinearLayoutManager
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.adapter.ArticleListAdapter
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.BannerBean
import com.shehuan.wanandroid.bean.article.ArticleBean
import com.shehuan.wanandroid.widget.DivideItemDecoration
import kotlinx.android.synthetic.main.fragment_home.*
import android.view.LayoutInflater
import com.youth.banner.Banner
import com.shehuan.wanandroid.widget.BannerImageLoader
import com.youth.banner.BannerConfig


class HomeFragment : BaseMvpFragment<HomePresenterImpl>(), HomeContract.View {
    private var pageNum: Int = 0
    private lateinit var articleListAdapter: ArticleListAdapter

    private lateinit var banner: Banner

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initPresenter(): HomePresenterImpl {
        return HomePresenterImpl(this)
    }

    override fun loadData() {
        presenter.getBannerData()
        presenter.getArticleList(pageNum)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_home
    }

    override fun initData() {

    }

    override fun initView() {
        banner = LayoutInflater.from(context).inflate(R.layout.home_banner_layout, homeRootLayout, false) as Banner
        banner.setImageLoader(BannerImageLoader())
        banner.setDelayTime(3000)
        banner.setIndicatorGravity(BannerConfig.RIGHT)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
        banner.setOnBannerListener {

        }

        articleListAdapter = ArticleListAdapter(context, null, true)

        // 添加banner
        articleListAdapter.addHeaderView(banner)

        articleListAdapter.setOnItemClickListener { _, data, position ->

        }
        articleListAdapter.setOnLoadMoreListener {
            presenter.getArticleList(pageNum)
        }
        val linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        articleRv.layoutManager = linearLayoutManager
        articleRv.addItemDecoration(DivideItemDecoration())
        articleRv.adapter = articleListAdapter
    }

    override fun onBannerSuccess(data: List<BannerBean>) {
        val images = arrayListOf<String>()
        val titles = arrayListOf<String>()

        for (bannerBean in data) {
            images.add(bannerBean.imagePath)
            titles.add(bannerBean.title)
        }

        banner.setImages(images)
        banner.setBannerTitles(titles)
        banner.start()
    }

    override fun onBannerError(e: ResponseException) {

    }

    override fun onArticleListSuccess(data: ArticleBean) {
        if (pageNum == 0) {
            articleListAdapter.setNewData(data.datas)
        } else {
            articleListAdapter.setLoadMoreData(data.datas)
        }
        pageNum++
        if (pageNum == data.pageCount) {
            articleListAdapter.loadEnd()
            return
        }
    }

    override fun onArticleListError(e: ResponseException) {

    }
}
