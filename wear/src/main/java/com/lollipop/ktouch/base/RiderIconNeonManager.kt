package com.lollipop.ktouch.base

import android.util.Log
import com.lollipop.ktouch.base.neon.MerryGoRoundNeon
import com.lollipop.ktouch.base.neon.OrderlyNeon

class RiderIconNeonManager(
    val iconManager: RiderIconManager
) {

    companion object {
        val NEON_STYLE_LIST: List<NeonStep> = listOf(
            OrderlyNeon,
            MerryGoRoundNeon.Default
        )
    }

    private val neonStyleList = ArrayList<NeonStep>()

    private var currentTrain: AnimationTrain? = null

    init {
        neonStyleList.addAll(NEON_STYLE_LIST)
    }

    fun cancel() {
        currentTrain?.cancel(pre = true, next = true)
    }

    fun play(maxTimeSecond: Long): AnimationTrain? {
//        Log.d("RiderIconNeonManager", "play.maxTimeSecond: $maxTimeSecond")
        return play(randomStep(maxTimeSecond))
    }

    fun play(stepList: List<NeonStep>): AnimationTrain? {
        var train: AnimationTrain? = null
        for (step in stepList) {
            val newTrain = train?.next {
                step.buildTrain(iconManager, this)
            } ?: AnimationTrain.create {
                step.buildTrain(iconManager, this)
            }
            train = newTrain
        }
        currentTrain?.cancel(pre = true, next = true)
        currentTrain = train
//        Log.d("RiderIconNeonManager", "play.allDuration: ${train?.allDuration() ?: 0}")
        return train
    }

    fun randomStep(maxTimeSecond: Long): List<NeonStep> {
        var freeTime = maxTimeSecond * 1000
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
                if (resultList.isEmpty()) {
                    if (min != null) {
                        resultList.add(min)
                    }
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