package com.example.task.data.response

import com.google.gson.annotations.SerializedName

class AccountResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("msisdn")
    val phone: String? = null,
    @SerializedName("password")
    val password: String? = null,
    @SerializedName("partnerCode")
    val partnerCode: String? = null
)
