package com.shehuan.wanandroid.widget

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DividerItemDecoration : RecyclerView.ItemDecoration() {
    // 分割线高度（px）
    private var dividerHeight = 1

    private var mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#CDCDCD")
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val manager = parent.layoutManager

        //只处理线性垂直类型的列表
        if (manager is LinearLayoutManager && LinearLayoutManager.VERTICAL != manager.orientation) {
            return
        }
        outRect.set(0, 0, 0, dividerHeight)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        for (i in 0 until parent.childCount) {
            val view = parent.getChildAt(i)
            drawDivider(c, parent, view)
        }
    }

    private fun drawDivider(c: Canvas, parent: RecyclerView, view: View) {
        val params = view.layoutParams as RecyclerView.LayoutParams
        val left = parent.paddingLeft
        val right = parent.width
        val top = view.bottom + params.bottomMargin
        val bottom = top + dividerHeight
        c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), mPaint)
    }

    fun setDividerColor(divideColor: Int): DividerItemDecoration {
        mPaint.color = divideColor
        return this
    }

    fun setDividerColor(divideColor: String): DividerItemDecoration {
        mPaint.color = Color.parseColor(divideColor)
        return this
    }

    fun setDividerHeight(divideHeight: Int): DividerItemDecoration {
        this.dividerHeight = divideHeight
        return this
    }
}