package com.example.task.ui.main.thread

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Handler
import com.example.task.R
import com.example.task.common.Constant
import com.example.task.common.util.TimeUtil

class ThreadGps(var shareData: ShareData, var handler: Handler, var time: Int, var ctx: Context) :
    Thread(),
    LocationListener {

    override fun run() {
        while (!shareData.isStop!!) {
            synchronized(shareData) {
                shareData.notifyAll()
                while (!shareData.isStop!! && shareData.flat != 2) {
                    shareData.wait()
                }
                shareData.addData(Constant.GPS)
                shareData.getLocation(this)
                shareData.flat = 1
                sleep(TimeUtil.getSecond(time))
            }
        }
        shareData.sendResult(handler, Constant.GPS_STOP)
        synchronized(shareData) { shareData.notifyAll() }
    }

    override fun onLocationChanged(location: Location?) {
        shareData.list.removeAt(shareData.getListSize() - 1)
        var result =
            "${ctx.getString(R.string.location)} ${location?.longitude} x ${location?.latitude}"
        shareData.list.add(result)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}

