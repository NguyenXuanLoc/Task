package com.example.task.common.util

object TimeUtil {

    fun getMinute(minute: Int): Long {
        return (minute * 60 * 1000).toLong()
    }

    fun getSecond(second: Int): Long {
        return (second * 1000).toLong()
    }
}