package com.lollipop.ktouch.main

import com.lollipop.ktouch.base.SingleRiderPage
import com.lollipop.resource.Rider

class FaizRiderPage : SingleRiderPage() {

    override fun optRider(): Rider {
        return Rider.Faiz
    }

}