package com.lollipop.ktouch.faiz

import android.app.Activity

/**
 * 体感处理器，怎么做呢？
 */
class FireMotionSensing(
    private val activity: Activity,
    private val fireCallback: FireCallback
) {

    fun onStart() {
        // TODO
    }

    fun onStop() {
        // TODO
    }

    fun interface FireCallback {
        fun onFire()
    }

}