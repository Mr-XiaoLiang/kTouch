package com.lollipop.ktouch.base.neon

import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.RiderIconGroup
import com.lollipop.ktouch.base.RiderIconNeonManager

object OrderlyNeon : RiderIconNeonManager.NeonStep() {

    override val duration: Long = 1600L
    override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
        val iconCount = iconGroup.playerIconCount
        animator.setIntValues(0, (iconCount * 2))
        animator.setDuration(duration)
        animator.addListener(IconGroupAnimationUpdateListener(iconGroup))
    }

    private class IconGroupAnimationUpdateListener(
        val iconGroup: RiderIconGroup
    ) : RiderIconAnimator.AnimationCallback {

        override fun onUpdate(value: Any) {
            if (value is Int) {
                if (value < iconGroup.playerIconCount) {
                    iconGroup.selectTo(value)
                } else {
                    iconGroup.selectFrom(value - iconGroup.playerIconCount)
                }
            }
        }

    }

}