package com.example.task.data.mapper

import com.example.task.data.model.BaseModel
import vn.vano.vicall.data.response.BaseListResponse
import vn.vano.vicall.data.response.BaseResponse
import vn.vano.vicall.data.response.BaseResponseLogin

fun <T> BaseResponse<T>.convertToModel(): BaseModel {
    val response = this
    return BaseModel().apply {
        code = response.code
//        message = response.message ?: ""
    }
}

fun <T> BaseResponseLogin<T>.convertToModel(): BaseModel {
    val response = this
    return BaseModel().apply {
        code = response.code
//        message = response.message ?: ""
    }
}