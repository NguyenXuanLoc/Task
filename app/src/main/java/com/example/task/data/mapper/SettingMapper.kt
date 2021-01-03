package com.example.task.data.mapper

import com.example.task.data.model.SettingModel
import com.example.task.data.response.SettingResponse

fun SettingResponse.convertToModel(): SettingModel {
    val response = this
    val res = response.data ?: response
    return SettingModel().apply {
        this.appCode = res?.appCode
        this.duration = res?.durationWatching
        this.waitTime = res?.waitTimes
    }
}

fun List<SettingResponse>.convertToModels(): List<SettingModel> {
    return ArrayList(map { it -> it.convertToModel() })
}