package com.example.task.ui.main

import android.annotation.SuppressLint
import android.location.LocationManager
import android.os.BatteryManager
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.MenuItem
import com.example.task.R
import com.example.task.common.Constant
import com.example.task.common.ext.gone
import com.example.task.common.ext.visible
import com.example.task.common.util.PermissionUtil
import com.example.task.ui.base.BaseActivity
import com.example.task.ui.main.thread.ShareData
import com.example.task.ui.main.thread.ThreadGps
import com.example.task.ui.main.thread.ThreadPin
import com.example.task.ui.main.thread.ThreadMain
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.toast


class HomeActivity : BaseActivity<HomeView, HomePresenter>(), HomeView,
    BottomNavigationView.OnNavigationItemSelectedListener {
    lateinit var locationManager: LocationManager
    private lateinit var threadTotal: Thread
    private lateinit var threadPin: Thread
    private lateinit var threadGps: Thread
    private lateinit var bm: BatteryManager
    private lateinit var shareData: ShareData
    private var RC_ACCESS_FINE_LOCATION = 1
    private val RC_ACCESS_COARSE_LOCATION = 2
    private val mHandler: Handler by lazy {
        @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    Constant.WHAT_PUSH_TO_SERVER -> {
                        var result = msg.data.getString(Constant.RESULT)
                        result?.let { presenter.sendData(it) }
                        lblResult.text = result
                    }
                    Constant.WHAT_RESULT -> {
                        toast(msg.data.getString(Constant.RESULT).toString())
                        lblResult.append("\n" + msg.data.getString(Constant.RESULT))
                    }
                    Constant.WHAT_NOTIFY -> {
                        lblResult.text = msg.data.getString(Constant.RESULT) + "\n"
                    }
                }
            }
        }
    }

    override fun initView(): HomeView {
        return this
    }

    override fun initPresenter(): HomePresenter {
        return HomePresenter(this)
    }

    override fun getLayoutId(): Int? {
        return R.layout.activity_home
    }

    override fun initWidgets() {
        applyToolbar()
        showTitle(R.string.home_page)
        bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        locationManager = (getSystemService(LOCATION_SERVICE) as LocationManager)
        shareData = ShareData(bm, locationManager, self)
        bnvMain.setOnNavigationItemSelectedListener(this)
    }

    override fun sendDataSuccess() {
        toast(R.string.send_success)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_start -> {
                if (edtTimeGps.text.isNotEmpty() && edtTimePin.text.isNotEmpty() &&
                    PermissionUtil.isGranted(
                        self,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        RC_ACCESS_FINE_LOCATION,
                        true
                    ) && PermissionUtil.isGranted(
                        self,
                        arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                        RC_ACCESS_COARSE_LOCATION,
                        true
                    )
                ) {
                    threadGps =
                        ThreadGps(shareData, mHandler, edtTimeGps.text.toString().toInt(), self)
                    threadPin = ThreadPin(shareData, edtTimePin.text.toString().toInt(), mHandler)
                    threadTotal = ThreadMain(shareData, mHandler, self)

                    lblResult.text = ctx.getString(R.string.start_to_run)
                    pbLoading.visible()
                    imgNotify.gone()
                    if (shareData.isStop!!) {
                        toast(R.string.keep_running)
                        shareData.isStop = false
                    } else {
                        toast(R.string.start)
                    }
                    threadTotal.start()
                    threadGps.start()
                    threadPin.start()
                } else {
                    toast(R.string.notify_input_time_or_permission)
                }
            }
            R.id.menu_stop -> {
                pbLoading.gone()
                lblResult.text = ctx.getString(R.string.stopping_thread)
                shareData.isStop = true
                toast(R.string.stop)
            }
        }
        return false
    }

}