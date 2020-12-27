package com.example.task.ui.listdata

import com.example.task.data.model.AccountModel
import com.example.task.data.model.InfoModel
import com.example.task.data.model.VideoModel
import com.example.task.ui.base.BaseView

interface ListDataView : BaseView {
    fun loginSuccess(user: InfoModel)
    fun loadDataSuccess(accounts: ArrayList<AccountModel>, videos: ArrayList<VideoModel>)
}