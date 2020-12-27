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
import com.google.gson.Gson


class ListDataPresenter(var ctx: Context) : BasePresenterImp<ListDataView>(ctx) {
    private val interactor by lazy { TaskInteractor() }
    fun getData(partnerCode: String, appCode: String, isProgress: Boolean) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                if (isProgress) showProgressDialog(true)
                interactor.getData(partnerCode, appCode)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.loadDataSuccess(
                                it.accounts as ArrayList<AccountModel>,
                                it.videos as ArrayList<VideoModel>
                            )
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

}