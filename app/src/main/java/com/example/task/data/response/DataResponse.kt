package com.example.task.data.response

import com.google.gson.annotations.SerializedName
import vn.vano.vicall.data.response.BaseResponse

class DataResponse(
    @SerializedName("user_code")
    val userCode: String? = null,
    @SerializedName("accounts")
    val listAccount: List<AccountResponse>? = null,
    @SerializedName("video")
    val listVideo: List<VideoResponse>? = null
) : BaseResponse<DataResponse>()