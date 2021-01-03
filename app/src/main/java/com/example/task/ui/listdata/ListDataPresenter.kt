package com.example.task.ui.listdata

import android.content.Context
import android.util.Log
import com.example.pview.ui.base.BasePresenterImp
import com.example.task.common.ext.addToCompositeDisposable
import com.example.task.common.ext.applyIOWithAndroidMainThread
import com.example.task.common.ext.networkIsConnected
import com.example.task.data.interactor.TaskInteractor
import com.example.task.data.model.AccountModel
import com.example.task.data.model.VideoModel


class ListDataPresenter(var ctx: Context) : BasePresenterImp<ListDataView>(ctx) {
    private val interactor by lazy { TaskInteractor() }
    fun showLoading() {
        view.also { showProgressDialog() }
    }

    fun hideLoading() {
        view.also { dismissProgressDialog() }
    }

    fun logAccount(
        id: String,
        phone: String,
        partnerCode: String,
        appCode: String, isShowProgress: Boolean = false
    ) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                if (isShowProgress) showProgressDialog()
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
                dismissProgressDialog()
            } else {
                v.onNetworkError()
            }
        }
    }

    fun getData(partnerCode: String, appCode: String, isProgress: Boolean) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                if (isProgress) showProgressDialog()
                interactor.getData(partnerCode, appCode)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            if (it.accounts != null && it.videos != null) {
                                v.loadDataSuccess(
                                    it.accounts as ArrayList<AccountModel>,
                                    it.videos as ArrayList<VideoModel>
                                )
                            } else {
                                v.dataNull()
                            }
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
                            v.loginSuccess(it)
                        },
                        {
                            Log.e("TAG", it.message)
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
                            v.onSendDataSuccess()
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