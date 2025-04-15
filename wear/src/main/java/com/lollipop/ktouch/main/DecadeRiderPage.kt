package com.lollipop.ktouch.main

import com.lollipop.ktouch.base.SingleRiderPage
import com.lollipop.resource.Rider

/**
 * 暂时特效不全，先忽略吧
 */
class DecadeRiderPage : SingleRiderPage() {

    override fun optRider(): Rider {
        return Rider.Decade
    }

}

class Decade21RiderPage : SingleRiderPage() {

    override fun optRider(): Rider {
        return Rider.Decade21
    }

}