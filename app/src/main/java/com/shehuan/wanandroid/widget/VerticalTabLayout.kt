package com.shehuan.wanandroid.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.shehuan.wanandroid.R

class VerticalTabLayout : LinearLayout {
    private var currentTabIndex = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        orientation = VERTICAL
    }

    fun addTabs(tabNames: List<String>) {
        for (tabName in tabNames) {
            addTab(tabName)
        }
    }

    private fun addTab(tabName: String) {
        val tab = TabItem(context, tabName)
        tab.tag = childCount
        tab.setOnClickListener {
            val tag: Int = it.tag as Int
            if (tag != currentTabIndex) {
                listener.onTabClick(currentTabIndex, tag)
                switchTab(tag)
            }
        }

        if (childCount == 0)
            tab.select()

        addView(tab)
    }

    private fun switchTab(destTabIndex: Int) {
        val destTab: TabItem = getChildAt(destTabIndex) as TabItem
        destTab.select()
        val currentTab: TabItem = getChildAt(currentTabIndex) as TabItem
        currentTab.unSelect()
        currentTabIndex = destTabIndex
    }

    interface OnTabClickListener {
        fun onTabClick(oldTabIndex: Int, newTabIndex: Int)
    }

    lateinit var listener: OnTabClickListener

    fun setOnTabClickListener(listener: OnTabClickListener) {
        this.listener = listener
    }

    @SuppressLint("ViewConstructor")
    private class TabItem(context: Context?, tabName: String) : LinearLayout(context) {
        private val indicatorView: View
        private val nameTv: TextView
        private val lineView: View

        init {
            val tab: LinearLayout = inflate(R.layout.tab_item_nav_layout) as LinearLayout
            nameTv = tab.findViewById(R.id.nameTv)
            nameTv.text = tabName
            indicatorView = tab.findViewById(R.id.indicatorView)
            lineView = tab.findViewById(R.id.lineView)
        }

        fun select() {
            nameTv.setTextColor(resources.getColor(R.color.cFE6243))
            indicatorView.visibility = View.VISIBLE
            lineView.visibility = View.GONE
        }

        fun unSelect() {
            nameTv.setTextColor(resources.getColor(R.color.c2C2C2C))
            indicatorView.visibility = View.INVISIBLE
            lineView.visibility = View.VISIBLE
        }

        private fun inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this)
    }
}