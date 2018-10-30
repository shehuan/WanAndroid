package com.shehuan.wanandroid.bean

data class LoginBean(val password: String = "",
                     val icon: String = "",
                     val collectIds: List<Int>?,
                     val id: Int = 0,
                     val type: Int = 0,
                     val email: String = "",
                     val token: String = "",
                     val username: String = "")