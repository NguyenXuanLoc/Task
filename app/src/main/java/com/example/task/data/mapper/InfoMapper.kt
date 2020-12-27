package com.example.task.data.mapper

import com.example.task.data.model.InfoModel
import com.example.task.data.response.InfoResponse

fun InfoResponse.convertToModel(): InfoModel {
    var response = this
    var data = response.data ?: response
    return InfoModel().apply {
        accessToken = data.accessToken
        refreshToken = data.refreshToken
        fullname = data.fullname
        avatarImage = data.avatarImage
        msisdn = data.msisdn
        userId = data.userId
    }
}