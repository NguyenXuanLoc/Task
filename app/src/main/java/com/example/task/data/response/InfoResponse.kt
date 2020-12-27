package com.example.task.data.response

import com.google.gson.annotations.SerializedName
import vn.vano.vicall.data.response.BaseResponseLogin

class InfoResponse : BaseResponseLogin<InfoResponse>() {
    @SerializedName("accessToken")
    val accessToken: String? = null

    @SerializedName("refreshToken")
    val refreshToken: String? = null

    @SerializedName("fullname")
    val fullname: String? = null

    @SerializedName("avatarImage")
    val avatarImage: String? = null

    @SerializedName("msisdn")
    val msisdn: String? = null

    @SerializedName("userId")
    val userId: String? = null
}