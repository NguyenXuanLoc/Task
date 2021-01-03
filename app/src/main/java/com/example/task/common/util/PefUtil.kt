package com.example.task.common.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.task.common.Constant
import java.lang.reflect.InvocationTargetException

object PefUtil {
    private val sharedPreferences = getApplicationByReflect().getSharedPreferences(Constant.SHARED_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    private fun getApplicationByReflect(): Application {
        try {
            @SuppressLint("PrivateApi")
            val activityThread = Class.forName("android.app.ActivityThread")
            val thread = activityThread.getMethod("currentActivityThread").invoke(null)
            val app = activityThread.getMethod("getApplication").invoke(thread)
                    ?: throw NullPointerException("u should init first")
            return app as Application
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
        throw NullPointerException("u should init first")
    }


    fun putString(name: String, value: String?) {
        editor?.putString(name, value)
        editor?.apply()
    }

    fun getString(name: String): String {
        return sharedPreferences?.getString(name, "") ?: ""
    }

    fun putLong(name: String, value: Long) {
        editor?.putLong(name, value)
        editor?.apply()
    }

    fun getLong(name: String): Long {
        return sharedPreferences?.getLong(name, 0) ?: 0
    }

    fun getLong(name: String, defaultValue: Long): Long {
        return sharedPreferences?.getLong(name, defaultValue) ?: defaultValue
    }

    fun putInt(name: String, value: Int?) {
        editor?.putInt(name, value ?: 0)
        editor?.apply()
    }

    fun getInt(name: String): Int {
        return sharedPreferences?.getInt(name, 0) ?: 0
    }

    fun getInt(name: String, defaultValue: Int): Int {
        return sharedPreferences?.getInt(name, defaultValue) ?: defaultValue
    }

    fun putBoolean(name: String, value: Boolean) {
        editor?.putBoolean(name, value)
        editor?.apply()
    }

    fun getBoolean(name: String): Boolean {
        return sharedPreferences?.getBoolean(name, false) ?: false
    }

    fun clearData() {
        editor?.clear()
        editor?.apply()
    }

    fun remove(key: String) {
        editor?.remove(key)
        editor?.apply()
    }
}