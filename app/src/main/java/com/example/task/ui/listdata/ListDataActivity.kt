package com.example.task.ui.listdata

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.*
import com.example.task.R
import com.example.task.common.Api
import com.example.task.common.Key
import com.example.task.common.MemoryHelper
import com.example.task.common.ext.WebViewInterface
import com.example.task.common.ext.loadUrlAutoPlay
import com.example.task.common.ext.settingss
import com.example.task.common.util.TimeUtil
import com.example.task.data.model.AccountModel
import com.example.task.data.model.InfoModel
import com.example.task.data.model.VideoModel
import com.example.task.ui.base.BaseActivity
import com.example.task.ui.test.TestActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_list_data.*

class ListDataActivity : BaseActivity<ListDataView, ListDataPresenter>(), ListDataView,
    WebViewInterface {
    var partnerCode: String? = null
    var accounts: ArrayList<AccountModel> = ArrayList()
    var videos: ArrayList<VideoModel> = ArrayList()
    var index = 0;
    override fun initView(): ListDataView {
        return this
    }

    override fun initPresenter(): ListDataPresenter {
        return ListDataPresenter(this)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_list_data
    }

    override fun initWidgets() {
        applyToolbar()
        showTitle("Loading.....")
        partnerCode?.let { presenter.getData(it, Api.ON_MOBI, true) }
        wvContent.settingss()
    }

    override fun loginSuccess(user: InfoModel) {
        Log.e("TAG", "LOGIN SUCCESSSSSSSSS")
               val gson = Gson()
        val json = gson.toJson(user)
        Log.e("TAG", "JSON: $json")
        var bundel = Bundle()
        bundel.putString("Test", json.toString())
        var intent = Intent(this, TestActivity::class.java)
        intent.putExtra("Test", bundel)
        startActivity(intent)
/*        wvContent.loadUrl("http://onmobi.vn/u/login")
        wvContent.webViewClient = object : WebViewClient(), WebViewInterface {
            override fun readyPlayVideo() {
                Handler().postDelayed(Runnable {
                    if (videos.size > index) {
                        showTitle("Loading video $index/${videos.size}")
                        wvContent.loadUrlAutoPlay(videos[index].link.toString(), this)
                        index++
                    }
                }, TimeUtil.getSecond(5))
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                Log.e("logUrl", request?.url?.toString() + " - " + request?.requestHeaders)
                if (request?.url?.toString()?.contains("push-log") == true) {
                    request.url.getQueryParameter("current_time")?.let { currentTime ->
                        try {
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                return super.shouldInterceptRequest(view, request)
            }

            override fun onPageFinished(view: WebView, url: String?) {
                super.onPageFinished(view, url)
                Log.e("logUrl", "finish: $url")
                if (url?.contains("http") == true) {
                    val checkLogin = "window.localStorage.setItem('user', '$json');"
                    Log.e("TAG", "USER: $json")
                    view.evaluateJavascript(checkLogin) {
                        Log.e("TAG", "LOGIN SUCCESS: USER$json")
                        view?.loadUrl(
                            "javascript:(function() { " +
                                    "var element = document.getElementById('hplogo');"
                                    + "element.parentNode.removeChild(element);" +
                                    "})()"
                        )
                        if (index <= videos.size - 1) {
                            showTitle("Play video $index/${videos.size}")
                            Handler().postDelayed(Runnable {
                                wvContent.loadUrlAutoPlay(videos[index].link.toString(), this)
                            }, 1000)
                            index++
                        }
                    }
                }
            }
        }*/
//        setupWebView(user)
    }

    override fun loadDataSuccess(
        accountsResponse: ArrayList<AccountModel>,
        videosResponse: ArrayList<VideoModel>
    ) {
        accounts.addAll(accountsResponse)
        videos.addAll(videosResponse)
        Log.e("TAG", "ACCOUST SIZE: ${accounts.size} VIDEO: ${videos.size}")
        presenter.login(true, accounts[0])

    }

    override fun getExtrasValue() {
        intent.extras?.run {
            if (containsKey(Key.PARTNER_CODE)) {
                partnerCode = getString(Key.PARTNER_CODE)
            }
        }
    }

    override fun readyPlayVideo() {
        Handler().postDelayed(Runnable {
            if (videos.size > index) {
                showTitle("Loading video $index/${videos.size}")
                wvContent.loadUrlAutoPlay(videos[index].link.toString(), this)
                index++
            }
        }, TimeUtil.getSecond(5))
    }
}