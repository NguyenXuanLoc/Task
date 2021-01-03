package com.example.task.data.response

import vn.vano.vicall.data.response.BaseResponse

class BaseListResponse<out T>() : BaseResponse<List<T>>()