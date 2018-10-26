package com.shehuan.wanandroid.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.shehuan.wanandroid.R

class BottomTabLayout : LinearLayout {

    private var currentTabIndex = 0
    private lateinit var viewPager: ViewPager

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = HORIZONTAL
    }

    fun addTab(nameStr: String, defaultIconId: Int, selectIconId: Int) {
        val tab = TabItem(context, nameStr, defaultIconId, selectIconId)
        tab.tag = childCount
        tab.setOnClickListener {
            val tag: Int = it.tag as Int
            if (tag != currentTabIndex) {
                viewPager.setCurrentItem(tag, true)
            }
        }

        if (childCount == 0)
            tab.select()

        val params = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
        params.weight = 1f

        addView(tab, params)
    }

    fun setupWithViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(i: Int) {

            }

            override fun onPageScrolled(i: Int, v: Float, i1: Int) {

            }

            override fun onPageSelected(i: Int) {
                if (i != currentTabIndex)
                    switchTab(i)
            }
        })
    }

    fun switchTab(destTabIndex: Int) {
        val destTab: TabItem = getChildAt(destTabIndex) as TabItem
        destTab.select()
        val currentTab: TabItem = getChildAt(currentTabIndex) as TabItem
        currentTab.unSelect()
        currentTabIndex = destTabIndex
    }

    @SuppressLint("ViewConstructor")
    private class TabItem(context: Context?, nameStr: String, private val defaultIconId: Int, private val selectIconId: Int) : LinearLayout(context) {
        private val icon: ImageView
        private val name: TextView

        init {
            orientation = VERTICAL
            val tab: LinearLayout = inflate(R.layout.bottom_tab_item_layout) as LinearLayout
            icon = tab.findViewById(R.id.icon)
            icon.setImageResource(defaultIconId)
            name = tab.findViewById(R.id.name)
            name.text = nameStr
        }

        fun select() {
            icon.setImageResource(selectIconId)
            name.setTextColor(resources.getColor(R.color.cFF534D))
        }

        fun unSelect() {
            icon.setImageResource(defaultIconId)
            name.setTextColor(resources.getColor(R.color.c707070))
        }

        private fun inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this)

    }
}