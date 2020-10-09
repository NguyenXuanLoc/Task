package com.example.task.common

object Constant {
    const val GPS = "gps"
    const val RESULT = "result"
    const val TOTAL_STOP = "Total STOP"
    const val GPS_STOP = "GPS STOP"
    const val PIN_STOP = "PIN STOP"
    const val WHAT_PUSH_TO_SERVER = 1
    const val WHAT_RESULT = 2
    const val WHAT_NOTIFY = 3
}

object ErrorCode {
    const val API_ERROR = 1 // Api call error (response code is different 200)
}

object ResponseCode {
    const val SUCCESS = "1"
    const val NOT_FOUND = "4"

}

object Api {
    const val DATA = "data"
}
