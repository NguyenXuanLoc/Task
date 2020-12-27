package vn.vano.vicall.data.response

import com.google.gson.annotations.SerializedName

open class BaseResponseLogin<out T>(
    @SerializedName("responseCode")
    val code: String? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: T? = null
)