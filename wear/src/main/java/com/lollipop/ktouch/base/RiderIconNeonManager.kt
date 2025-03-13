package com.lollipop.ktouch.base

import com.lollipop.ktouch.base.neon.OrderlyNeon

class RiderIconNeonManager(
    val iconManager: RiderIconManager
) {

    companion object {
        val NEON_STYLE_LIST: List<NeonStep> = listOf(
            OrderlyNeon
        )
    }

    private val neonStyleList = ArrayList<NeonStep>()

    init {
        neonStyleList.addAll(NEON_STYLE_LIST)
    }

    fun play(maxTime: Long): AnimationTrain? {
        val stepList = randomStep(maxTime)
        var train: AnimationTrain? = null
        for (step in stepList) {
            val newTrain = train?.next {
                step.buildTrain(iconManager, this)
            } ?: AnimationTrain.create {
                step.buildTrain(iconManager, this)
            }
            train = newTrain
        }
        return train
    }

    fun randomStep(maxTime: Long): List<NeonStep> {
        var freeTime = maxTime
        val resultList = ArrayList<NeonStep>()
        val tempList = ArrayList<NeonStep>()
        while (freeTime > 0) {
            var min: NeonStep? = null
            tempList.clear()
            for (step in neonStyleList) {
                if (step.duration <= freeTime) {
                    tempList.add(step)
                } else {
                    if (min == null || step.duration < min.duration) {
                        min = step
                    }
                }
            }
            if (tempList.isEmpty()) {
                if (min != null) {
                    resultList.add(min)
                }
                return resultList
            } else {
                val step = tempList.random()
                freeTime -= step.duration
                resultList.add(step)
            }
        }
        return resultList
    }

    abstract class NeonStep {

        abstract val duration: Long

        abstract fun buildTrain(iconGroup: RiderIconGroup, animator: RiderIconAnimator)

    }

}