package com.example.task.data.mapper

import com.example.task.data.model.VideoModel
import com.example.task.data.response.VideoResponse

fun VideoResponse.convertToModel(): VideoModel {
    var response = this
    return VideoModel().apply {
        link = response.link
        channel = response.channel
    }
}

fun List<VideoResponse>.convertToModels(): ArrayList<VideoModel> {
    return ArrayList(map { it ->
        it.convertToModel()
    })
}