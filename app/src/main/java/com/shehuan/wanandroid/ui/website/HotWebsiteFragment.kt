package com.shehuan.wanandroid.ui.website


import android.view.ViewGroup
import android.widget.TextView
import com.google.android.flexbox.FlexboxLayout
import com.shehuan.wanandroid.R
import com.shehuan.wanandroid.base.fragment.BaseMvpFragment
import com.shehuan.wanandroid.base.net.exception.ResponseException
import com.shehuan.wanandroid.bean.FriendBean
import com.shehuan.wanandroid.ui.article.ArticleActivity
import com.shehuan.wanandroid.utils.CommonUtil
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

    }

    override fun loadData() {
        presenter.getFriendData()
    }

    override fun onFriedSuccess(data: List<FriendBean>) {
        websiteFL.setWebsiteData(data)
    }

    override fun onFriendError(e: ResponseException) {

    }

    private fun FlexboxLayout.setWebsiteData(data: List<FriendBean>) {
        for (website in data) {
            val view = TextView(mContext)
            view.setOnClickListener {
                ArticleActivity.start(mContext, website.name, website.link)
            }
            view.text = website.name
            view.background = resources.getDrawable(R.drawable.website_selecter)
            val padding1 = CommonUtil.dp2px(mContext, 12)
            val padding2 = CommonUtil.dp2px(mContext, 5)
            view.setPadding(padding1, padding2, padding1, padding2)
            val params = FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val margin1 = CommonUtil.dp2px(mContext, 6)
            val margin2 = CommonUtil.dp2px(mContext, 10)
            params.setMargins(margin2, margin1, margin2, margin1)
            websiteFL.addView(view, params)
        }
    }
}
