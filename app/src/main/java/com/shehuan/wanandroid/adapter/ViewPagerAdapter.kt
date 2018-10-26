package com.shehuan.wanandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter
import com.shehuan.wanandroid.base.fragment.BaseFragment

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    private lateinit var fragments: List<BaseFragment>
    private lateinit var titles: List<String>

    fun setFragments(fragments: List<BaseFragment>) {
        this.fragments = fragments
    }

    fun setFragmentsAndTitles(fragments: List<BaseFragment>, titles: List<String>) {
        this.fragments = fragments
        this.titles = titles
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}