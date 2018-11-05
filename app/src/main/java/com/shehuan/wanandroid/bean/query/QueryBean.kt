package com.shehuan.wanandroid.bean.query

data class QueryBean(val over: Boolean = false,
                     val pageCount: Int = 0,
                     val total: Int = 0,
                     val curPage: Int = 0,
                     val offset: Int = 0,
                     val size: Int = 0,
                     val datas: List<DatasItem>)