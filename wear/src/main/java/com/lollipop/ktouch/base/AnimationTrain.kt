package com.lollipop.ktouch.base

import android.animation.Animator
import android.animation.ValueAnimator

class AnimationTrain private constructor() : RiderIconAnimator {

    companion object {
        fun create(block: AnimationTrain.() -> Unit): AnimationTrain {
            val train = AnimationTrain()
            train.block()
            return train
        }
    }

    val animator = ValueAnimator()

    private var prevNode: AnimationTrain? = null
    private var nextNode: AnimationTrain? = null

    private val animationCallbackList by lazy {
        mutableListOf<RiderIconAnimator.AnimationCallback>()
    }

    private val animationUpdateCallback = ValueAnimator.AnimatorUpdateListener { anim ->
        onUpdate(anim)
    }

    private val animationCallback = object : Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            onStart(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            onEnd(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            onCancel(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onRepeat(animation)
        }

    }

    init {
        animator.addUpdateListener(animationUpdateCallback)
        animator.addListener(animationCallback)
    }

    fun next(block: AnimationTrain.() -> Unit): AnimationTrain {
        val next = AnimationTrain()
        next.prevNode = this
        this.nextNode = next
        next.block()
        return next
    }

    private fun onUpdate(anim: ValueAnimator) {
        animationCallbackList.forEach {
            it.onUpdate(anim.animatedValue)
        }
    }

    private fun onStart(animation: Animator) {
        animationCallbackList.forEach {
            it.onStart(animation)
        }
    }

    private fun onEnd(animation: Animator) {
        animationCallbackList.forEach {
            it.onEnd(animation)
        }
        nextNode?.start(false)
    }

    private fun onCancel(animation: Animator) {
        animationCallbackList.forEach {
            it.onCancel(animation)
        }
    }

    private fun onRepeat(animation: Animator) {
        animationCallbackList.forEach {
            it.onRepeat(animation)
        }
    }

    fun first(): AnimationTrain {
        val p = prevNode ?: return this
        return p.first()
    }

    fun last(): AnimationTrain {
        val n = nextNode ?: return this
        return n.last()
    }

    fun start(first: Boolean) {
        if (first) {
            first().start(false)
        } else {
            animator.start()
        }
    }

    fun stop(pre: Boolean, next: Boolean) {
        if (pre) {
            prevNode?.stop(true, false)
        }
        if (next) {
            nextNode?.stop(false, true)
        }
        animator.end()
    }

    fun cancel(pre: Boolean, next: Boolean) {
        if (pre) {
            prevNode?.cancel(true, false)
        }
        if (next) {
            nextNode?.cancel(false, true)
        }
        animator.cancel()
    }

    fun isRunning(): Boolean {
        if (animator.isRunning) {
            return true
        }
        if (prevNode?.isRunning() == true) {
            return true
        }
        if (nextNode?.isRunning() == true) {
            return true
        }
        return false
    }

    fun clearCallbacks() {
        animationCallbackList.clear()
    }

    override fun setIntValues(vararg value: Int) {
        animator.setIntValues(*value)
    }

    override fun setFloatValues(vararg value: Float) {
        animator.setFloatValues(*value)
    }

    override fun setDuration(duration: Long) {
        animator.setDuration(duration)
    }

    override fun addListener(listener: RiderIconAnimator.AnimationCallback) {
        animationCallbackList.add(listener)
    }

    override fun removeListener(listener: RiderIconAnimator.AnimationCallback) {
        animationCallbackList.remove(listener)
    }

    override fun setRepeatCount(count: Int) {
        animator.repeatCount = count
    }

    override fun setRepeatInfinite() {
        animator.repeatCount = ValueAnimator.INFINITE
    }

    override fun setRepeatByRestart() {
        animator.repeatMode = ValueAnimator.RESTART
    }

    override fun setRepeatByReverse() {
        animator.repeatMode = ValueAnimator.REVERSE
    }

}