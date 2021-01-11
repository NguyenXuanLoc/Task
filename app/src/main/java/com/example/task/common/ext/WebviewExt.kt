package com.example.task.common.ext

import android.util.Log
import android.util.TimeUtils
import android.webkit.*
import com.example.task.common.util.TimeUtil

fun WebView.settingss() {
    this.settings.apply {
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

    /* this.webViewClient = WebViewClient()
     this.settings.domStorageEnabled = true
     this.settings.loadWithOverviewMode = true
     this.settings.useWideViewPort = true
     this.settings.builtInZoomControls = true
     this.settings.mediaPlaybackRequiresUserGesture = true*/
}

fun WebView.openWebView(url: String, v: WebViewInterface) {
    this.loadUrl(url)
    this.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            //save acc to browser
            view?.loadUrl(
                "javascript:(function() { " +
                        "var element = document.getElementById('hplogo');"
                        + "element.parentNode.removeChild(element);" +
                        "})()"
            )
            super.onPageFinished(view, url)
        }
    }
}

fun WebView.loadUrlAutoPlay(url: String, v: WebViewInterface) {
    v.timeWatching(0)
    this.loadUrl(url)
    this.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            v.getStartTime(TimeUtil.getCurrentTime())
            v.readyPlayVideo()
            view?.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()")
            super.onPageFinished(view, url)
        }

        override fun shouldInterceptRequest(
            view: WebView?,
            request: WebResourceRequest?
        ): WebResourceResponse? {
            if (request?.url?.toString()?.contains("push-log") == true) {
                request.url.getQueryParameter("current_time")?.let { currentTime ->
                    var times = currentTime.split(".")
                    v.timeWatching(times[0].toInt())
                }
            }
            return super.shouldInterceptRequest(view, request)
        }
    }
}

fun WebView.login(url: String, v: WebViewInterface) {
    this.loadUrl(url)
    this.webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            v.readyPlayVideo()
            Log.e("TAG", "LOGIN")
            //Auto play when loadVideo success
//            view?.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
            /*      Handler().postDelayed({
                      var javascript = "javascript: "
                      javascript += "document.getElementsByName('username')[0].nodeValue = '0782970859'; "
                      javascript += "document.getElementsByName('password')[0].nodeValue = '920167'; "
                      javascript += "document.getElementsByName('captcha')[0].nodeValue = '1234'; "
                      view?.evaluateJavascript(javascript, null)
      //                view?.loadUrl(javascript)
              }, 2000)
                      Handler().postDelayed({
                          val login =
                              "javascript: document.forms[0].lastElementChild.firstElementChild.click();"
                          view?.evaluateJavascript(login, null)
                      }, 1000)*/
//        */
            var javascript = "javascript: "
            javascript += "document.getElementsByName('LoginForm[username]')[0].value = '0865518724'; "
            javascript += "document.getElementsByName('LoginForm[password]')[0].value = '574281'; "
            view?.evaluateJavascript(javascript, null)

            /*       if (webView.url?.contains("auth/login") == true) {
           if (!isLoginToExit) {
               var javascript = "javascript: "
               javascript += if (user?.msisdn?.startsWith("0") == true) {
                   "document.getElementsByName('LoginForm[username]')[0].value = '${user?.msisdn}'; "
               } else {
                   "document.getElementsByName('LoginForm[username]')[0].value = '0${user?.msisdn}'; "
               }
               javascript += "document.getElementsByName('LoginForm[password]')[0].value = '${user?.password}'; "
               webView.evaluateJavascript(javascript, null)

               Handler(Looper.getMainLooper()).postDelayed({
                   val loginScript = "javascript: " +
                           "var buttons = document.getElementsByTagName('button');" +
                           "for (var i = 0; i < buttons.length; i++) {" +
//                                                "    if (buttons[i].firstChild.nodeValue == 'Đăng nhập') {" +
                           "        buttons[i].click();" +
//                                                "    }" +
                           "};"
                   webView.evaluateJavascript(loginScript, null)
               }, 1000)
           } else {
               var javascript = "javascript: "
               javascript += "document.getElementsByName('LoginForm[username]')[0].value = '0865518724'; "
               javascript += "document.getElementsByName('LoginForm[password]')[0].value = '574281'; "
               webView.evaluateJavascript(javascript, null)

               Handler(Looper.getMainLooper()).postDelayed({
                   val loginScript = "javascript: " +
                           "var buttons = document.getElementsByTagName('button');" +
                           "for (var i = 0; i < buttons.length; i++) {" +
//                                                "    if (buttons[i].firstChild.nodeValue == 'Đăng nhập') {" +
                           "        buttons[i].click();" +
//                                                "    }" +
                           "};"
                   webView.evaluateJavascript(loginScript, null)
               }, 1000)
           }
       } else {
           if (!isLoginToExit) {
               isLogged = true
               startVideos()
           } else {
               loginError()
           }
       }*/
            super.onPageFinished(view, url)
        }
    }
}

interface WebViewInterface {
    fun readyPlayVideo()
    fun getStartTime(startTime: String)
    fun timeWatching(time: Int)
}