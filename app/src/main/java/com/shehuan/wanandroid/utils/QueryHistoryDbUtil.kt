package com.shehuan.wanandroid.utils

import com.shehuan.wanandroid.bean.db.QueryHistoryBean
import org.litepal.LitePal
import org.litepal.extension.deleteAll
import org.litepal.extension.find
import org.litepal.extension.findAll

class QueryHistoryDbUtil {
    companion object {
        fun save(name: String) {
            val bean = QueryHistoryBean(System.currentTimeMillis(), name)
            bean.save()
        }

        fun query(): List<QueryHistoryBean> = LitePal.order("time").find()

        fun clear() = LitePal.deleteAll<QueryHistoryBean>()

        fun update(name: String) {
            val bean: QueryHistoryBean = LitePal.where("name = ?", name).find<QueryHistoryBean>()[0]
            bean.time = System.currentTimeMillis()
            bean.save()
        }
    }
}