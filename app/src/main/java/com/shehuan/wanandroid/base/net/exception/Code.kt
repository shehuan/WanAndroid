package com.shehuan.wanandroid.base.net.exception

class Code {
    companion object {
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val REQUEST_TIMEOUT = 408
        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504


        /* 自约定的响应码 */
        // 未知错误
        const val UNKNOWN_ERROR = 1000
        // 解析错误
        const val PARSE_ERROR = 1001
        // 网络错误
        const val NET_ERROR = 1002
        // 协议出错
        const val HTTP_ERROR = 1003
        // 证书出错
        const val SSL_ERROR = 1005
        // 连接超时
        const val TIMEOUT_ERROR = 1006
    }
}