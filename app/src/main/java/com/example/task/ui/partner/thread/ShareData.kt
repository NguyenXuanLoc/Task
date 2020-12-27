package com.example.task.ui.partner.thread

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.*
import androidx.core.app.ActivityCompat
import com.example.task.R
import com.example.task.common.Constant

class ShareData(var bm: BatteryManager, var locationManager: LocationManager, var ctx: Context) :
    Object() {

    var list: ArrayList<String>
    var flat: Int? = null
    var isStop: Boolean? = null

    init {
        flat = 1
        isStop = false
        list = ArrayList()
    }

    fun getListSize(): Int {
        return list.size
    }

    @Synchronized
    fun clearList() {
        list.clear()
    }

    @Synchronized
    fun addData(result: String) {
        list.add(result)
    }

    fun sendList(handler: Handler, what: Int = Constant.WHAT_PUSH_TO_SERVER) {
        var result = ""
        list.forEach { it ->
            result += "$it\n"
        }
        val message = Message()
        val bundle = Bundle()
        message.what = what
        bundle.putString(Constant.RESULT, result)
        message.data = bundle
        handler.sendMessage(message)
    }

    fun sendResult(handler: Handler, result: String, what: Int = Constant.WHAT_RESULT) {
        val message = Message()
        val bundle = Bundle()
        message.what = what
        bundle.putString(Constant.RESULT, result)
        message.data = bundle
        handler.sendMessage(message)
    }

    fun getBattery(): String {
        var percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return "${ctx.getString(R.string.battery_percentage)} $percentage %"
    }

    fun getLocation(listener: LocationListener) {
        if (ActivityCompat.checkSelfPermission(
                ctx,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                ctx,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager?.requestSingleUpdate(Constant.GPS, listener, Looper.getMainLooper())
    }

}