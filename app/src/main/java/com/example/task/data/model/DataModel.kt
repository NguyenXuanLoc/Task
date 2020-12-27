package com.example.task.data.model

class DataModel(
    var userCode: String? = null,
    var accounts: List<AccountModel>? = null,
    var videos: List<VideoModel>? = null
) : BaseModel() {

}