package com.example.task.data.mapper

import com.example.task.data.model.DataModel
import com.example.task.data.response.DataResponse

fun DataResponse.convertToModel(): DataModel {
    var response = this
    var data = response.data
    return DataModel().apply {
        code = response.code
//        message = response.message ?: ""
        data?.also { data ->
                accounts = data.listAccount?.convertToModels()
                videos = data.listVideo?.convertToModels()
            }
    }
}
