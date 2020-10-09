package com.example.pview.ui.base

import android.content.Context
import com.example.task.ui.base.BaseView
import io.reactivex.disposables.CompositeDisposable

open class BasePresenterImp<T : BaseView>(private val context: Context) : BasePresenter<T>() {

    protected var view: T? = null
    protected val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    override fun attachView(view: T) {
        view.getExtrasValue()
        this.view = view
    }

    override fun detachView() {
        compositeDisposable.clear()
        view = null
    }
}