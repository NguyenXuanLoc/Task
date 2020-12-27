package com.example.task.ui.test

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task.R
import kotlinx.android.synthetic.main.activity_list_data.*

class TestActivity : AppCompatActivity() {
    var isTest = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        getExtra()
    }

    private fun getExtra() {
        var bundle = intent.getBundleExtra("Test")
        var user = bundle.getString("Test")
        Log.e("TAG", "USER: $user")
        user?.let { setupWebView(it) }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(user: String) {
        wvContent.settings.apply {
            javaScriptEnabled = true
            @Suppress("DEPRECATION")
            layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            domStorageEnabled = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            useWideViewPort = true
            loadWithOverviewMode = true
            mediaPlaybackRequiresUserGesture = false
            setSupportMultipleWindows(true)
            setAppCacheEnabled(true)
        }

        wvContent.loadUrl("http://onmobi.vn/u/login")

        wvContent.webViewClient = object : WebViewClient() {
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
                    val checkLogin = "window.localStorage.setItem('user', '$user');"
                    Log.e("TAG", "USER: $user")
                    view.evaluateJavascript(checkLogin) {
                        Log.e("TAG", "LOGIN SUCCESS $user")
                     /*   if (isTest) {
                            wvContent.loadUrl("http://onmobi.vn/video/detail/18645/nhung-khoanh-khac-dang-nho-cua-lin-dan-tai-giai-olympic-p1")
                            isTest = false
                        }*/
                        /*isLogged = true
                        viewModel.startAction = System.currentTimeMillis()
                        startVideos()*/
                    }
                }
            }
        }
    }
}