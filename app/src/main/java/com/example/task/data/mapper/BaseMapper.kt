package com.example.task.data.mapper

import com.example.task.data.model.BaseModel
import vn.vano.vicall.data.response.BaseResponse

fun <T> BaseResponse<T>.convertToModel(): BaseModel {
    val response = this
    return BaseModel().apply {
        code = response.code
        message = response.message ?: ""
    }
}