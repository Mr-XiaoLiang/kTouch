package com.lollipop.ktouch.base

import android.animation.Animator

interface RiderIconAnimator {

    fun setIntValues(vararg value: Int)
    fun setFloatValues(vararg value: Float)
    fun setDuration(duration: Long)
    fun addListener(listener: AnimationCallback)
    fun removeListener(listener: AnimationCallback)
    fun setRepeatCount(count: Int)
    fun setRepeatInfinite()
    fun setRepeatByRestart()
    fun setRepeatByReverse()
    fun setAllDuration(allDuration: Long, repeatCount: Int) {
        if (repeatCount < 0) {
            throw IllegalArgumentException("repeatCount must be >= 0")
        }
        setDuration(allDuration / (repeatCount + 1))
        setRepeatCount(repeatCount)
    }

    interface AnimationCallback {
        fun onUpdate(value: Any)

        fun onStart(animation: Animator) {}

        fun onEnd(animation: Animator) {}

        fun onCancel(animation: Animator) {}

        fun onRepeat(animation: Animator) {}
    }

    class AnimationCallbackAdapter(
        private val updateCallback: (value: Any) -> Unit = {},
        private val startCallback: (animation: Animator) -> Unit = {},
        private val endCallback: (animation: Animator) -> Unit = {},
        private val cancelCallback: (animation: Animator) -> Unit = {},
        private val repeatCallback: (animation: Animator) -> Unit = {}
    ) : AnimationCallback {
        override fun onUpdate(value: Any) {
            updateCallback.invoke(value)
        }

        override fun onStart(animation: Animator) {
            startCallback.invoke(animation)
        }

        override fun onEnd(animation: Animator) {
            endCallback.invoke(animation)
        }

        override fun onCancel(animation: Animator) {
            cancelCallback.invoke(animation)
        }

        override fun onRepeat(animation: Animator) {
            repeatCallback.invoke(animation)
        }

    }

}