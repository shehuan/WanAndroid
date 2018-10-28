package com.shehuan.wanandroid.bean.tree

import android.os.Parcel
import android.os.Parcelable

data class ChildrenItem(val visible: Int = 0,
                        val name: String = "",
                        val userControlSetTop: Boolean = false,
                        val id: Int = 0,
                        val courseId: Int = 0,
                        val parentChapterId: Int = 0,
                        val order: Int = 0) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            1 == source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(visible)
        writeString(name)
        writeInt((if (userControlSetTop) 1 else 0))
        writeInt(id)
        writeInt(courseId)
        writeInt(parentChapterId)
        writeInt(order)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ChildrenItem> = object : Parcelable.Creator<ChildrenItem> {
            override fun createFromParcel(source: Parcel): ChildrenItem = ChildrenItem(source)
            override fun newArray(size: Int): Array<ChildrenItem?> = arrayOfNulls(size)
        }
    }
}