package com.example.task.ui.main

import android.content.Context
import com.example.pview.ui.base.BasePresenterImp
import com.example.task.common.ext.addToCompositeDisposable
import com.example.task.common.ext.applyIOWithAndroidMainThread
import com.example.task.common.ext.networkIsConnected
import com.example.task.data.interactor.TaskInteractor

class HomePresenter(var ctx: Context) : BasePresenterImp<HomeView>(ctx) {
    private val interactor by lazy { TaskInteractor() }

    fun sendData(result: String) {
        view?.also { v ->
            if (ctx.networkIsConnected()) {
                interactor.sendData(result)
                    .applyIOWithAndroidMainThread()
                    .subscribe(
                        {
                            v.sendDataSuccess()
                        },
                        {
                            v.onApiCallError()
                        }
                    ).addToCompositeDisposable(compositeDisposable)
            } else {
                v.onNetworkError()
            }
        }
    }
}