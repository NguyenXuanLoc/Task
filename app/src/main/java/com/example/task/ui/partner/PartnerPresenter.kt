package com.example.task.ui.partner

import android.content.Context
import android.util.Log
import com.example.pview.ui.base.BasePresenterImp
import com.example.task.common.ext.addToCompositeDisposable
import com.example.task.common.ext.applyIOWithAndroidMainThread
import com.example.task.common.ext.networkIsConnected
import com.example.task.data.interactor.TaskInteractor
import com.example.task.data.model.AccountModel

class PartnerPresenter(var ctx: Context) : BasePresenterImp<PartnerView>(ctx) {
    private val interactor by lazy { TaskInteractor() }

    fun sendData(partnerCode: String) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                interactor.sendData(partnerCode)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.sendDataSuccess()
                        },
                        {
                            v.onApiCallError()
                        }
                    )
                    .addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
        }
    }

    fun getPartnerCode(isShowProgress: Boolean) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                if (isShowProgress) showProgressDialog(true)
                interactor.getPartnerCode()
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.loadPartnerSuccess(it)
                        },
                        {
                            v.onApiCallError()
                        }
                    )
                    .addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
            dismissProgressDialog()
        }
    }

    fun login(isShowProgress: Boolean, ob: AccountModel) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                if (isShowProgress) showProgressDialog(true)
                interactor.login(ob)
                    ?.applyIOWithAndroidMainThread()
                    ?.subscribe(
                        {
                            Log.e("TAG", it.toString())
                        },
                        {
                            v.onApiCallError()
                        }
                    )
                    ?.addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
            dismissProgressDialog()
        }
    }

    fun logAccount(
        id: String,
        phone: String,
        partnerCode: String,
        appCode: String
    ) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                interactor.logAccount(id, phone, partnerCode, appCode)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
//                            v.sendDataSuccess()
                        },
                        {
                            v.onApiCallError()
                        }
                    )
                    .addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
        }
    }

    fun logAction(
        partnerCode: String,
        phone: String,
        loginStatus: String,
        startTime: String,
        endTime: String, viewTime: String, viewLink: String, channel: String, appCode: String
    ) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                interactor.logAction(
                    partnerCode,
                    phone,
                    loginStatus,
                    startTime,
                    endTime,
                    viewTime,
                    viewLink,
                    channel,
                    appCode
                )
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.sendDataSuccess()
                        },
                        {
                            Log.e("TAG", it.message);
                            v.onApiCallError()
                        }
                    )
                    .addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
        }
    }

    fun getData(partnerCode: String, appCode: String) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                interactor.getData(partnerCode, appCode)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.sendDataSuccess()
                        },
                        {
                            v.onApiCallError()
                        }
                    )
                    .addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
        }
    }
}

