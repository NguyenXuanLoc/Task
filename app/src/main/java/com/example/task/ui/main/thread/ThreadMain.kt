package com.example.task.ui.main.thread

import android.content.Context
import android.os.Handler
import android.util.Log
import com.example.task.common.Constant
import com.example.task.common.util.TimeUtil

class ThreadMain(var shareData: ShareData, var handler: Handler, var ctx: Context) : Thread() {
    override fun run() {
        sleep(TimeUtil.getSecond(1))
        while (!shareData.isStop!!) {
            Log.e("TAG", "\t\tMAIN")
            synchronized(shareData) {
                if (shareData.getListSize() <= 5) {
                    if (shareData.list.size > 0) {
                        shareData.sendList(handler, Constant.WHAT_NOTIFY)
                    }
                    if (shareData.flat == 1) {
                        shareData.flat = 2
                    }
                    shareData.notifyAll()
                    shareData.wait()
                } else {
                    shareData.sendList(handler)
                    shareData.clearList()
                    shareData.notifyAll()
                }
            }
        }
        shareData.sendResult(handler, Constant.TOTAL_STOP)
        synchronized(shareData) { shareData.notifyAll() }
    }
}