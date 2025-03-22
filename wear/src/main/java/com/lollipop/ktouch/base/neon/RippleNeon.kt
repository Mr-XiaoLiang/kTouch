package com.lollipop.ktouch.base.neon

import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.RiderIconGroup
import com.lollipop.ktouch.base.RiderIconNeonManager

class RippleNeon(
    private val startIndex: Int,
    private val repeatCount: Int
) : RiderIconNeonManager.NeonStep() {

    companion object {

        private const val DURATION = 1000L

        fun create(startIndex: Int, duration: Long): RippleNeon {
            return RippleNeon(startIndex, (duration / DURATION).toInt())
        }
    }

    override val duration: Long = repeatCount * DURATION

    override fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator) {
        val iconCount = iconGroup.playerIconCount
        animator.setIntValues(0, (iconCount / 2))
        animator.setDuration(DURATION)
        animator.setRepeatCount(repeatCount)
        animator.setRepeatByRestart()
        animator.addListener(IconGroupAnimationUpdateListener(startIndex, iconGroup))
    }

    private class IconGroupAnimationUpdateListener(
        val startIndex: Int,
        val iconGroup: RiderIconGroup
    ) : RiderIconAnimator.AnimationCallback {

        override fun onUpdate(value: Any) {
            if (value is Int) {
                val iconCount = iconGroup.playerIconCount
                var selectIndexLeft = startIndex - value
                if (selectIndexLeft < 0) {
                    selectIndexLeft += iconCount
                }
                var selectIndexRight = startIndex + value
                if (selectIndexRight >= iconCount) {
                    selectIndexRight -= iconCount
                }
                for (i in 0 until iconCount) {
                    if (i == selectIndexLeft || i == selectIndexRight) {
                        iconGroup.setSelect(i, true)
                    } else {
                        iconGroup.setSelect(i, false)
                    }
                }
            }
        }

    }

}