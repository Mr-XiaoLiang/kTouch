package com.lollipop.ktouch.base.neon

import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.RiderIconGroup
import com.lollipop.ktouch.base.RiderIconNeonManager

object OddEvenNeon : RiderIconNeonManager.NeonStep() {

    override val duration: Long = 1000L
    override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
        animator.setIntValues(0, 2)
        animator.setAllDuration(duration, 2)
        animator.addListener(IconGroupAnimationUpdateListener(iconGroup))
    }

    private class IconGroupAnimationUpdateListener(
        val iconGroup: RiderIconGroup
    ) : RiderIconAnimator.AnimationCallback {

        override fun onUpdate(value: Any) {
            if (value is Int) {
                val playerIconCount = iconGroup.playerIconCount
                for (i in 0 until playerIconCount) {
                    iconGroup.setSelect(i, i % 2 == value % 2)
                }
            }
        }

    }

}