package com.example.task.ui.partner.thread

import android.os.Handler
import android.util.Log
import com.example.task.common.Constant
import com.example.task.common.util.TimeUtil

class ThreadPin(var shareData: ShareData, var time: Int, var handler: Handler) : Thread() {

    override fun run() {
        while (!shareData.isStop!!) {
            Log.e("TAG", "PIN")
            synchronized(shareData) {
                shareData.notifyAll()
                while (shareData.flat != 2 && !shareData.isStop!!) {
                    shareData.wait()
                }
                shareData.addData(shareData.getBattery())
                shareData.flat = 1
                sleep(TimeUtil.getSecond(time))
            }
        }

        shareData.sendResult(handler, Constant.PIN_STOP)
        synchronized(shareData) { shareData.notifyAll() }
    }
}