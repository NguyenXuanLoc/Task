package com.example.pview.ui.base

import android.content.Context
import com.example.task.ui.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import vn.vano.vicall.widget.progressbar.MyProgressDialog

open class BasePresenterImp<T : BaseView>(private val context: Context) : BasePresenter<T>() {
    private val progressDialog: MyProgressDialog by lazy { MyProgressDialog(context) }

    protected var view: T? = null
    protected val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun showProgressDialog(cancelable: Boolean = false) {
        if (!progressDialog.isShowing) {
            progressDialog.setCancelable(cancelable)
            progressDialog.show()
        }
    }

    protected fun dismissProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    override fun attachView(view: T) {
        view.getExtrasValue()
        this.view = view
    }

    override fun detachView() {
        compositeDisposable.clear()
        view = null
    }
}