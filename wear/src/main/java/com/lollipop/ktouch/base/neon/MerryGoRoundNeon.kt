package com.lollipop.ktouch.base.neon

import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.RiderIconGroup
import com.lollipop.ktouch.base.RiderIconNeonManager

sealed class MerryGoRoundNeon : RiderIconNeonManager.NeonStep() {

    override val duration: Long = 1500

    override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
        val iconCount = iconGroup.playerIconCount
        animator.setIntValues(0, iconCount - 1)
        animator.setDuration(duration)
        animator.addListener(
            IconGroupAnimationUpdateListener(iconGroup)
        )
    }

    private class IconGroupAnimationUpdateListener(
        val iconGroup: RiderIconGroup
    ) : RiderIconAnimator.AnimationCallback {

        override fun onUpdate(value: Any) {
            if (value is Int) {
                iconGroup.selectOnly(value)
            }
        }

    }

    object Default : MerryGoRoundNeon() {
        override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
            super.buildTrain(iconGroup, animator)
            animator.setRepeatCount(1)
        }
    }

    object Infinite : MerryGoRoundNeon() {
        override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
            super.buildTrain(iconGroup, animator)
            animator.setRepeatInfinite()
            animator.setRepeatByRestart()
        }
    }

}