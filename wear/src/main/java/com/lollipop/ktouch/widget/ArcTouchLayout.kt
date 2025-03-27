package com.lollipop.ktouch.widget

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.util.AttributeSet

class ArcTouchLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ArcLayout(context, attributeSet) {

    companion object {
        const val POSITION_PARENT_LEFT = -1
        const val POSITION_PARENT_TOP = -2
        const val POSITION_PARENT_RIGHT = -3
        const val POSITION_PARENT_BOTTOM = -4
        const val POSITION_PARENT_CENTER = -5
    }

    private val restrictedZoneList = ArrayList<Rect>()
    private val touchCircleCenter = Point()

    fun addRestrictedZone(rect: Rect) {
        restrictedZoneList.add(rect)
    }

    sealed class TouchCircleCenter {

        abstract fun getAngle(
            viewWidth: Int,
            viewHeight: Int,
            touchX: Int,
            touchY: Int,
            outInfo: TouchCircle
        )

        class Absolute(val x: Int, val y: Int) : TouchCircleCenter() {
            override fun getAngle(
                viewWidth: Int,
                viewHeight: Int,
                touchX: Int,
                touchY: Int,
                outInfo: TouchCircle
            ) {
                TODO("Not yet implemented")
            }
        }

        class Weight(val x: Float, val y: Float, val padding: Rect) : TouchCircleCenter() {
            override fun getAngle(
                viewWidth: Int,
                viewHeight: Int,
                touchX: Int,
                touchY: Int,
                outInfo: TouchCircle
            ) {
                TODO("Not yet implemented")
            }
        }

    }

    class TouchCircle(
        var angle: Float,
        var radius: Float,
        var centerX: Float,
        var centerY: Float
    )

}