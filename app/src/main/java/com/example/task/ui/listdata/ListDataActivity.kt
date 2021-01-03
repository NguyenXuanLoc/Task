package com.example.task.ui.listdata

import android.os.Build
import android.os.Handler
import android.util.Log
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.task.R
import com.example.task.common.Api
import com.example.task.common.Constant
import com.example.task.common.Key
import com.example.task.common.ext.WebViewInterface
import com.example.task.common.ext.loadUrlAutoPlay
import com.example.task.common.ext.settingss
import com.example.task.common.util.TimeUtil
import com.example.task.data.model.AccountModel
import com.example.task.data.model.InfoModel
import com.example.task.data.model.SettingModel
import com.example.task.data.model.VideoModel
import com.example.task.ui.base.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_data.*
import org.jetbrains.anko.toast
import java.io.File

class ListDataActivity : BaseActivity<ListDataView, ListDataPresenter>(), ListDataView {
    private var durationWatching = ""
    private var partnerCode: String? = null
    private var accounts: ArrayList<AccountModel> = ArrayList()
    private var videos: ArrayList<VideoModel> = ArrayList()
    private var index = 0
    private var startTime = ""
    override fun initView(): ListDataView {
        return this
    }

    private var isLogin = false
    override fun initPresenter(): ListDataPresenter {
        return ListDataPresenter(this)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_list_data
    }

    override fun initWidgets() {
        clearCookies()
        clearCache()
        applyToolbar()
        showTitle("Loading.....")
        partnerCode?.let { presenter.getData(it, Api.ON_MOBI, true) }
        wvContent.settingss()
        presenter.showLoading()
    }

    override fun dataNull() {
        toast(R.string.data_null)
        finish()
    }

    override fun loginSuccess(user: InfoModel) {
        presenter.showLoading()
        if (user.accessToken != null) {
            val json = Gson().toJson(user)
            wvContent.loadUrl(Constant.URL_LOGIN)
            wvContent.webViewClient = object : WebViewClient(), WebViewInterface {

                override fun readyPlayVideo() {
                    Handler().postDelayed(
                        Runnable {
                            if (videos.size > index) {
                                Log.e("TAG", "PLAY VIDEO AT: ${index}")
                                var acc = accounts[0]
                                presenter.logAction(
                                    acc.partnerCode.toString(),
                                    acc.phone.toString(),
                                    isLogin.toString(),
                                    startTime,
                                    TimeUtil.getCurrentTime(),
                                    TimeUtil.getSecond().toString(),
                                    videos[index].link.toString(),
                                    videos[index].channel.toString(),
                                    Api.ON_MOBI
                                )
                                showTitle("Play video ${index + 1}/${videos.size}")
                                wvContent.loadUrlAutoPlay(videos[index].link.toString(), this)
                                index++
                            } else {
                                var acc = accounts[0]
                                presenter.logAccount(
                                    acc.id.toString(),
                                    acc.phone.toString(),
                                    acc.partnerCode.toString(),
                                    Api.ON_MOBI
                                )
                                clearCookies()
                                clearCache()
                                partnerCode?.let { presenter.getData(it, Api.ON_MOBI, true) }
                            }
                        }, TimeUtil.getSecond()
                    )
                }

                override fun getStartTime(time: String) {
                    startTime = time
                }

                override fun onPageFinished(view: WebView, url: String?) {
                    super.onPageFinished(view, url)
                    if (url?.contains("http") == true) {
                        val checkLogin = "window.localStorage.setItem('user', '$json');"
                        view.evaluateJavascript(checkLogin) {
                            Log.e("TAG", "LOGIN SUCCESS: USER$json")
                            if (json.isNotEmpty()) {
                                isLogin = true
                                view?.loadUrl(
                                    "javascript:(function() { " +
                                            "var element = document.getElementById('hplogo');"
                                            + "element.parentNode.removeChild(element);" +
                                            "})()"
                                )
                                if (index <= videos.size - 1) {
                                    startTime = TimeUtil.getCurrentTime()
                                    presenter.hideLoading()
                                    showTitle("Play video ${index + 1}/${videos.size}")
                                    Handler().postDelayed(Runnable {
                                        wvContent.loadUrlAutoPlay(
                                            videos[index].link.toString(),
                                            this
                                        )
                                    }, 1000)
                                    index++
                                }
                            } else {
                                clearCookies()
                                clearCache()
                                presenter.login(true, accounts[0])
                            }
                        }
                    }
                }
            }
        } else {
            toast(R.string.info_invalid)
            presenter.showLoading()
            partnerCode?.let { presenter.getData(it, Api.ON_MOBI, true) }
        }
    }

    override fun loadDataSuccess(
        accountsResponse: ArrayList<AccountModel>,
        videosResponse: ArrayList<VideoModel>
    ) {
        accounts.clear()
        videos.clear()
        accounts.addAll(accountsResponse)
        videos.addAll(videosResponse)
        var acc = accounts[0]
        if (acc.password!!.length < 6) {
            while (acc.password!!.length < 6) acc.password += 0
        }
        accounts[0] = acc
        presenter.login(true, acc)
    }

    override fun getExtrasValue() {
        intent.extras?.run {
            if (containsKey(Key.PARTNER_CODE)) {
                partnerCode = getString(Key.PARTNER_CODE)
            }
            if (containsKey(Key.GET_SETTING)) {
                durationWatching = getString(Key.DURATION, "30")
            }
        }
    }

    private fun clearCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
        } else {
            val cookieSyncManager = CookieSyncManager.createInstance(this)
            cookieSyncManager.startSync()
            val cookieManager: CookieManager = CookieManager.getInstance()
            cookieManager.removeAllCookie()
            cookieManager.removeSessionCookie()
            cookieSyncManager.stopSync()
            cookieSyncManager.sync()
        }
    }

    private fun clearCache() {
        try {
            deleteDir(cacheDir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun deleteDir(dir: File?): Boolean {
        return if (dir != null && dir.isDirectory) {
            val children: Array<String> = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            dir.delete()
        } else if (dir != null && dir.isFile()) {
            dir.delete()
        } else {
            false
        }
    }

    override fun onStop() {
        super.onStop()
        finish()
    }
}