package com.example.task.data.mapper

import android.util.Log
import com.example.task.data.model.PartnerModel
import com.example.task.data.response.PartnerResponse
import vn.vano.vicall.data.response.BaseResponse

fun BaseResponse<PartnerModel>.convertToModel(): PartnerModel {
    var response = this
    val data = response.data
    return PartnerModel().apply {
        code = this.code
//        message = this.message
        Log.e("TAG", "RESPONSE: ${data}")
       /* data?.also { data ->
            listPartner = data.listPartner as List<String>
        }*/
    }
}