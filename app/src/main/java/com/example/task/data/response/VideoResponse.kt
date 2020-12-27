package com.example.task.data.response

import com.google.gson.annotations.SerializedName

class VideoResponse(
    @SerializedName("link") var link: String,
    @SerializedName("channel") var channel: String
)