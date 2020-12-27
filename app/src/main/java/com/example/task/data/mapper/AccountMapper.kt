package com.example.task.data.mapper

import com.example.task.data.model.AccountModel
import com.example.task.data.response.AccountResponse

fun AccountResponse.convertToModel(): AccountModel {
    var res = this;
    return AccountModel().apply {
        id = res.id
        phone = res.phone
        password = res.password
        partnerCode = res.partnerCode
    }
}

fun List<AccountResponse>.convertToModels(): ArrayList<AccountModel> {
    return ArrayList(map { it -> it.convertToModel() })
}