package com.lollipop.ktouch.base

import android.animation.Animator
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MaskIconDelegate(
    val iconView: ImageView,
    val maskView: ImageView?
) {

    private var onClickCallback: OnClickCallback? = null

    init {
        iconView.setOnClickListener { onViewClick() }
        val colorList = ContextCompat.getColorStateList(
            iconView.context, com.lollipop.resource.R.color.color_rider_icon
        )
        iconView.imageTintList = colorList
        maskView?.imageTintList = colorList
    }

    private fun onViewClick() {
        onClickCallback?.onClick()
    }

    fun onIconClick(callback: OnClickCallback) {
        onClickCallback = callback
    }

    fun setSelect(isSelected: Boolean) {
        iconView.isSelected = isSelected
        maskView?.isSelected = isSelected
    }

    fun reset() {
        maskView?.animate()?.cancel()
        maskView?.isVisible = false
        maskReset()
    }

    fun playAnimation(onAnimationEnd: (() -> Unit)? = null) {
        maskReset()
        val view = maskView
        if (view == null) {
            onAnimationEnd?.invoke()
            return
        }
        val durationTime = 600L
        view.animate()?.apply {
            cancel()
            scaleX(6F)
            scaleY(6F)
            alpha(0F)
            duration = durationTime
            setListener(
                OnceAnimatorListener(
                    onStartCallback = {
                        view.isVisible = true
                    },
                    onEndCallback = {
                        view.isVisible = false
                    }
                )
            )
            start()
        }
        if (onAnimationEnd != null) {
            view.postDelayed(kotlinx.coroutines.Runnable {
                onAnimationEnd.invoke()
            }, durationTime)
        }
    }

    private fun maskReset() {
        maskView?.alpha = 1F
        maskView?.scaleX = 1F
        maskView?.scaleY = 1F
    }

    private class OnceAnimatorListener(
        private val onStartCallback: (Animator) -> Unit = {},
        private val onEndCallback: (Animator) -> Unit = {},
        private val onCancelCallback: (Animator) -> Unit = {},
        private val onRepeatCallback: (Animator) -> Unit = {}
    ) : Animator.AnimatorListener {

        override fun onAnimationStart(animation: Animator) {
            onStartCallback.invoke(animation)
        }

        override fun onAnimationEnd(animation: Animator) {
            animation.removeListener(this)
            onEndCallback.invoke(animation)
        }

        override fun onAnimationCancel(animation: Animator) {
            animation.removeListener(this)
            onCancelCallback.invoke(animation)
        }

        override fun onAnimationRepeat(animation: Animator) {
            onRepeatCallback.invoke(animation)
        }

    }

    fun interface OnClickCallback {
        fun onClick()
    }

}

