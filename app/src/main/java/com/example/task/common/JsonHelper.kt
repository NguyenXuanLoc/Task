package com.example.task.common

import com.google.gson.GsonBuilder

object JsonHelper {

    val gson = GsonBuilder().serializeNulls().excludeFieldsWithoutExposeAnnotation().create()

    fun toJson(obj: Any?): String? {
        return if (obj != null) {
            gson.toJson(obj)
        } else {
            null
        }
    }

    fun <T> toJson(list: MutableList<T>): String {
        return gson.toJson(list)
    }

    fun <T> parseJson(json: String?, clazz: Class<T>): T? {
        if (json.isNullOrEmpty())
            return null

        return try {
            gson.fromJson(json, clazz)
        } catch (e: Exception) {
            null
        }
    }

}