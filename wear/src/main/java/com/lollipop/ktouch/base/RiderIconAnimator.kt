package com.lollipop.ktouch.base

import android.animation.Animator

interface RiderIconAnimator {

    fun setIntValues(vararg value: Int)
    fun setFloatValues(vararg value: Float)
    fun setDuration(duration: Long)
    fun addListener(listener: AnimationCallback)
    fun removeListener(listener: AnimationCallback)

    interface AnimationCallback {
        fun onUpdate(value: Any)

        fun onStart(animation: Animator) {}

        fun onEnd(animation: Animator) {}

        fun onCancel(animation: Animator) {}

        fun onRepeat(animation: Animator) {}
    }

}