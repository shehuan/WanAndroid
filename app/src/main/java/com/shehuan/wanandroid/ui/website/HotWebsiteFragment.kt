package com.shehuan.wanandroid.ui.website

import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.addCommonView
import kotlinx.android.synthetic.main.fragment_hot_website.*


class HotWebsiteFragment : BaseMvpFragment<HotWebsitePresenterImpl>(), HotWebsiteContract.View {
    companion object {
        fun newInstance() = HotWebsiteFragment()
    }

    override fun initPresenter(): HotWebsitePresenterImpl {
        return HotWebsitePresenterImpl(this)
    }

    override fun initLayoutResID(): Int {
        return R.layout.fragment_hot_website
    }

    override fun initData() {

    }

    override fun initView() {
        initStatusView(hotWebsiteHomeLayout) {
            initLoad()
        }
    }

    override fun initLoad() {
        statusView.showLoadingView()
        presenter.getFriendData()
    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        statusView.showContentView()
        for (website in data) {
            websiteFL.addCommonView(mContext, website.name, R.color.c2C2C2C, R.drawable.website_selecter) {
                ArticleActivity.start(mContext, website.name, website.link)
            }
        }
    }

    override fun onFriendError(e: ResponseException) {
        statusView.showErrorView()
    }
}
