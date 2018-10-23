package com.shehuan.keasymvp.test.utils.sp

class SpUtil {
    companion object {
        fun getVersion(): String {
            return SharedPreferencesHelper.get("version", "1.0")
        }

        fun setVersion(version: String) {
            SharedPreferencesHelper.put("version", version)
        }
    }
}