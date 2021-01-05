package com.example.task.common.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    fun getCurrentTime(): String {
        return SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
    }

    fun getMinute(minute: Int): Long {
        return (minute * 60 * 1000).toLong()
    }

    fun getSecond(second: Int = 10): Long {
        return (second * 1000).toLong()
    }
}