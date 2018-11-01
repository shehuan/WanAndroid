package com.shehuan.wanandroid.bean.db

import org.litepal.annotation.Column
import org.litepal.crud.LitePalSupport

data class QueryHistoryBean(@Column(nullable = false) var time: Long,
                            @Column(nullable = false, unique = true) val name: String) : LitePalSupport()