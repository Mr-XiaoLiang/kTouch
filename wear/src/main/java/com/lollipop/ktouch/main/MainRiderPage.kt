package com.lollipop.ktouch.main

import com.lollipop.ktouch.base.SingleRiderPage
import com.lollipop.resource.sound.Rider

/**
 * 暂时特效不全，先忽略吧
 */
sealed class MainRiderPage : SingleRiderPage() {

    class Decade : MainRiderPage() {
        override fun optRider(): Rider = Rider.Decade
    }

    interface Callback : SingleRiderPage.Callback

}