package com.example.task.data.response

import com.google.gson.annotations.SerializedName
import vn.vano.vicall.data.response.BaseResponse

class SettingResponse(
    @SerializedName("appCode") var appCode: String,
    @SerializedName("durationWatching") var durationWatching: String,
    @SerializedName("waitTimes") var waitTimes: String
) : BaseResponse<SettingResponse>() {

}