package com.example.task.data.interactor

import com.example.task.common.Api
import com.example.task.data.mapper.convertToModel
import com.example.task.data.model.AccountModel
import com.example.task.data.model.BaseModel
import com.example.task.data.model.DataModel
import com.example.task.data.model.InfoModel
import io.reactivex.Single

class TaskInteractor : BaseInteractor() {

    fun login(ob: AccountModel): Single<InfoModel>? {
        var param = HashMap<String, String>()
        param["grant_type"] = "login"
        param["username"] = ob.phone.toString()
        param["password"] = ob.password.toString()
        param["captcha"] = "1234"
        return loginService?.login(param)?.map { it.convertToModel() }
    }

    fun sendData(data: String): Single<BaseModel> {
        var param = HashMap<String, String>()
        param[Api.DATA] = data
        return service.sendData(param).map {
            it.convertToModel()
        }
    }

    fun getPartnerCode(): Single<List<String>> {
        return service.getPartnerCode().map { it.data; }
    }

    fun getData(partnerCode: String, appCode: String): Single<DataModel> {
        var param = HashMap<String, String>()
        param[Api.PARTNER_CODE] = partnerCode
        param[Api.APP_CODE] = appCode
        return service.getData(param).map { it.convertToModel(); }
    }

    fun logAccount(
        id: String,
        phone: String,
        partnerCode: String,
        appCode: String
    ): Single<BaseModel> {
        var param = HashMap<String, String>()
        param[Api.ID] = id
        param[Api.MSISDN] = phone
        param[Api.PARTNER_CODE] = partnerCode
        param[Api.APP_CODE] = appCode
        return service.logAccount(param).map { it.convertToModel() }
    }

    fun logAction(
        partnerCode: String,
        phone: String,
        loginStatus: String,
        startTime: String,
        endTime: String, viewTime: String, viewLink: String, channel: String, appCode: String
    ): Single<BaseModel> {
        var param = HashMap<String, String>()
        param[Api.PARTNER_CODE] = partnerCode
        param[Api.MSISDN] = phone
        param[Api.LOGIN_STATUS] = loginStatus
        param[Api.ACT_START_TIME] = startTime
        param[Api.ACT_END_TIME] = endTime
        param[Api.VIEW_TIME] = viewTime
        param[Api.VIEW_LINK] = viewLink
        param[Api.CHANNEL] = channel
        param[Api.APP_CODE] = appCode
        return service.logAction(param).map { it.convertToModel() }
    }
}