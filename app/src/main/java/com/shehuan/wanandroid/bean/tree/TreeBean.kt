package com.shehuan.wanandroid.bean.tree

data class TreeBean(val visible: Int = 0,
                    val children: ArrayList<ChildrenItem>,
                    val name: String = "",
                    val userControlSetTop: Boolean = false,
                    val id: Int = 0,
                    val courseId: Int = 0,
                    val parentChapterId: Int = 0,
                    val order: Int = 0)