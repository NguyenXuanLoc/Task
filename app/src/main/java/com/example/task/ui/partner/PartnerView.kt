package com.example.task.ui.partner

import com.example.task.ui.base.BaseView

interface PartnerView : BaseView {
    fun sendDataSuccess()
    fun loadPartnerSuccess(partners: List<String>)
}