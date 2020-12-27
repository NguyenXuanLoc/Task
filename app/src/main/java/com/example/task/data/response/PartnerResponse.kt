package com.example.task.data.response

import com.example.task.data.model.PartnerItemModel
import vn.vano.vicall.data.response.BaseResponse

class PartnerResponse(
    val listPartner: List<PartnerItemModel>? = null
) : BaseResponse<PartnerResponse>()