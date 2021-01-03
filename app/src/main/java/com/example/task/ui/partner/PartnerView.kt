package com.example.task.ui.partner

import com.example.task.data.model.SettingModel
import com.example.task.ui.base.BaseView

interface PartnerView : BaseView {
    fun loadSettingSuccess(settings: List<SettingModel>)
    fun sendDataSuccess()
    fun loadPartnerSuccess(partners: List<String>)
}