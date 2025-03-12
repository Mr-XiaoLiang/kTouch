package com.lollipop.ktouch.base

import android.animation.Animator
import android.animation.ValueAnimator

class AnimationTrain {

    val animator = ValueAnimator()

    private var prevNode: AnimationTrain? = null
    private var nextNode: AnimationTrain? = null

    private val animationCallbackList by lazy {
        mutableListOf<AnimationCallback>()
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
        TODO("还没写完")
    }

    private fun onUpdate(anim: ValueAnimator) {
        animationCallbackList.forEach {
            it.onUpdate(anim)
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

    fun cache(pre: Boolean, next: Boolean) {
        if (pre) {
            prevNode?.cache(true, false)
        }
        if (next) {
            nextNode?.cache(false, true)
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

    fun registerCallback(callback: AnimationCallback) {
        animationCallbackList.add(callback)
    }

    fun unregisterCallback(callback: AnimationCallback): Boolean {
        return animationCallbackList.remove(callback)
    }

    fun clearCallbacks() {
        animationCallbackList.clear()
    }

    interface AnimationCallback {
        fun onUpdate(anim: ValueAnimator)

        fun onStart(animation: Animator)

        fun onEnd(animation: Animator)

        fun onCancel(animation: Animator)

        fun onRepeat(animation: Animator)
    }

}