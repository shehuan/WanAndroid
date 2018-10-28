package com.shehuan.wanandroid.bean.navi

import android.os.Parcel
import android.os.Parcelable

data class ArticlesItem(val superChapterName: String = "",
                        val publishTime: Long = 0,
                        val visible: Int = 0,
                        val niceDate: String = "",
                        val projectLink: String = "",
                        val author: String = "",
                        val zan: Int = 0,
                        val origin: String = "",
                        val chapterName: String = "",
                        val link: String = "",
                        val title: String = "",
                        val type: Int = 0,
                        val userId: Int = 0,
                        val apkLink: String = "",
                        val envelopePic: String = "",
                        val chapterId: Int = 0,
                        val superChapterId: Int = 0,
                        val id: Int = 0,
                        val fresh: Boolean = false,
                        val collect: Boolean = false,
                        val courseId: Int = 0,
                        val desc: String = "") : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readLong(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            1 == source.readInt(),
            1 == source.readInt(),
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(superChapterName)
        writeLong(publishTime)
        writeInt(visible)
        writeString(niceDate)
        writeString(projectLink)
        writeString(author)
        writeInt(zan)
        writeString(origin)
        writeString(chapterName)
        writeString(link)
        writeString(title)
        writeInt(type)
        writeInt(userId)
        writeString(apkLink)
        writeString(envelopePic)
        writeInt(chapterId)
        writeInt(superChapterId)
        writeInt(id)
        writeInt((if (fresh) 1 else 0))
        writeInt((if (collect) 1 else 0))
        writeInt(courseId)
        writeString(desc)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ArticlesItem> = object : Parcelable.Creator<ArticlesItem> {
            override fun createFromParcel(source: Parcel): ArticlesItem = ArticlesItem(source)
            override fun newArray(size: Int): Array<ArticlesItem?> = arrayOfNulls(size)
        }
    }
}