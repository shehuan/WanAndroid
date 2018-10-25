package com.shehuan.wanandroid.utils.sp

import android.content.Context
import android.content.SharedPreferences
import com.shehuan.wanandroid.App
import kotlin.IllegalArgumentException

object SharedPreferencesHelper {

    private val sharedPreferences: SharedPreferences by lazy {
        App.getApp().getSharedPreferences("ksp", Context.MODE_PRIVATE)
    }

    fun put(key: String, value: Any) = with(sharedPreferences.edit()) {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("This type of value cannot be save!")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    fun <T> get(key: String, defaultValue: T): T = with(sharedPreferences) {
        val value = when (defaultValue) {
            is String -> getString(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Boolean -> getBoolean(key, defaultValue)
            else -> throw IllegalArgumentException("This type of value cannot be get!")
        }

        return value as T
    }

    fun contain(key: String) {
        sharedPreferences.contains(key)
    }

    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}