package com.example.task.data.interactor

import com.example.task.common.Api
import com.example.task.data.mapper.convertToModel
import com.example.task.data.model.BaseModel
import io.reactivex.Single

class TaskInteractor : BaseInteractor() {

    fun sendData(data: String): Single<BaseModel> {
        var param = HashMap<String, String>()
        param[Api.DATA] = data
        return service.sendData(param).map {
            it.convertToModel()
        }
    }
}