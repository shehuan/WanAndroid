package com.shehuan.wanandroid.base.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.shehuan.wanandroid.base.BasePresenter

abstract class BaseMvpFragment<P : BasePresenter<*>> : BaseFragment() {
    lateinit var presenter: P

    private var isViewCreated: Boolean = false // 界面是否已创建完成
    private var isVisibleToUser: Boolean = false // 是否对用户可见
    private var isDataLoaded: Boolean = false// 数据是否已请求, isNeedReload()返回false的时起作用
    private var isFragmentHidden: Boolean = true // 记录当前fragment的是否隐藏

    abstract fun initPresenter(): P

    abstract fun loadData()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewCreated = true

        presenter = initPresenter()

        tryLoadData()
    }

    /**
     * 使用ViewPager嵌套fragment时，切换ViewPager回调该方法
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        tryLoadData()
    }

    /**
     * 使用show()、hide()控制fragment显示、隐藏时回调该方法
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isFragmentHidden = hidden
        if (!hidden) {
            tryLoadData1()
        }
    }

    /**
     * ViewPager场景下，判断父fragment是否可见
     */
    private fun isParentVisible(): Boolean {
        val fragment: Fragment? = parentFragment
        return fragment == null || (fragment is BaseMvpFragment<*> && fragment.isVisibleToUser)
    }

    /**
     * ViewPager场景下，当前fragment可见，如果其子fragment也可见，则尝试让子fragment加载请求
     */
    private fun dispatchParentVisibleState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseMvpFragment<*> && child.isVisibleToUser) {
                child.tryLoadData()
            }
        }
    }

    /**
     * fragment再次可见时，是否重新请求数据，默认为flase则只请求一次数据
     */
    protected fun isNeedReload(): Boolean = false

    /**
     * ViewPager场景下，尝试请求数据
     */
    private fun tryLoadData() {
        if (isViewCreated && isVisibleToUser && isParentVisible() && (isNeedReload() || !isDataLoaded)) {
            loadData()
            isDataLoaded = true
            dispatchParentVisibleState()
        }
    }

    /**
     * show()、hide()场景下，当前fragment没隐藏，如果其子fragment也没隐藏，则尝试让子fragment请求数据
     */
    private fun dispatchParentHiddenState() {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        if (fragments.isEmpty()) {
            return
        }
        for (child in fragments) {
            if (child is BaseMvpFragment<*> && !child.isHidden) {
                child.tryLoadData1()
            }
        }
    }

    /**
     * show()、hide()场景下，父fragment是否隐藏
     */
    private fun isParentHidden(): Boolean {
        val fragment: Fragment? = parentFragment
        if (fragment == null) {
            return false
        } else if (fragment is BaseMvpFragment<*> && !fragment.isHidden) {
            return false
        }
        return true
    }

    /**
     * show()、hide()场景下，尝试请求数据
     */
    private fun tryLoadData1() {
        if (!isParentHidden() && (isNeedReload() || !isDataLoaded)) {
            loadData()
            isDataLoaded = true
            dispatchParentHiddenState()
        }
    }

    override fun onDestroy() {
        isViewCreated = false
        isVisibleToUser = false
        isDataLoaded = false
        isFragmentHidden = true

        presenter.detach()

        super.onDestroy()
    }
}