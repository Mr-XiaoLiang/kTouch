package com.lollipop.ktouch.heisei

import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.widget.ArcTouchLayout
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

abstract class DcdHeiseiSubPage : HeiseiSubPage() {

    override fun onRiderClick(rider: Rider) {
        if (rider == Rider.Decade) {
            onDcdClick()
            return
        }
        when (currentState) {
            State.INIT -> {
                if (!selectedRiderList.contains(rider)) {
                    selectedRiderList.add(rider)
                    iconManager.select(rider, true)
                    SoundManager.play(rider.nameSound)
                    iconManager.playAnimation(rider)
                }
            }

            State.READY -> {
                if (!selectedRiderList.contains(rider)) {
                    selectedRiderList.add(rider)
                    iconManager.select(rider, true)
                    SoundManager.play(rider.nameSound)
                    iconManager.playAnimation(rider)
                } else {
                    SoundManager.play(SoundKey.DeviceSpace)
                }
            }
        }
    }

    protected open fun heiseiReady(): Boolean {
        return selectedRiderList.size == iconManager.riderIconCount - 1
    }

    protected abstract fun heiseiSound(): SoundKey

    private fun onDcdClick() {
        when (currentState) {
            State.INIT -> {
                if (heiseiReady()) {
                    currentState = State.READY
                    val rider = Rider.Decade
                    iconManager.select(rider, true)
                    iconManager.playAnimation(rider) {
                        val sound = heiseiSound()
                        SoundManager.play(sound)
                        newNeon().play(sound.timeMillis)?.also {
                            it.addListener(
                                RiderIconAnimator.AnimationCallbackAdapter(
                                    endCallback = {
                                        iconManager.selectOnly(-1)
                                    }
                                )
                            )
                            it.start(true)
                        }
                    }
                    selectedRiderList.clear()
                } else {
                    // 这种情况下，就不操作吧
                    SoundManager.play(SoundKey.DeviceSpace)
                }
            }

            State.READY -> {
                SoundManager.play(SoundKey.NameDecade)
            }
        }
    }

    protected fun bindRiderTouch(
        arcTouchLayout: ArcTouchLayout,
        vararg rider: Rider
    ): RiderTouchAdapter {
        val touchAdapter = RiderTouchAdapter(rider) {
            onRiderClick(it)
        }
        arcTouchLayout.setOnArcTouchListener(touchAdapter)
        return touchAdapter
    }

    protected class RiderTouchAdapter(
        private val riderArray: Array<out Rider>,
        private val listener: (Rider) -> Unit
    ) : ArcTouchLayout.OnArcTouchListener {

        private val stepAngle = 360F / riderArray.size
        private val halfAngle = stepAngle / 2F

        private var selectIndex = -1

        override fun onTouch(
            view: ArcTouchLayout,
            angle: Float,
            radius: Float,
            centerX: Float,
            centerY: Float,
            touchX: Float,
            touchY: Float
        ) {
            var index = ((angle + halfAngle) / stepAngle + 1).toInt()
            index %= riderArray.size
            if (selectIndex != index) {
                selectIndex = index
                listener(riderArray[index])
            }
        }

    }

}