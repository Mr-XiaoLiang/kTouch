package com.lollipop.ktouch.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs

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

    private var touchSlop = 0
    private val touchDownPoint = PointF()
    private var touchMode = TouchMode.NONE

    fun addRestrictedZone(rect: Rect) {
        restrictedZoneList.add(rect)
    }

    private fun onTouchDown(event: MotionEvent) {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        touchDownPoint.set(event.x, event.y)
        touchMode = TouchMode.NONE
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                onTouchDown(ev)
                if (needIntercept(ev.x, ev.y)) {
                    requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if (needIntercept(ev.x, ev.y)) {
                    requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
            MotionEvent.ACTION_CANCEL -> {
                touchMode = TouchMode.CANCELED
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                // 超过一个手指之后，放弃吧
                touchMode = TouchMode.CANCELED
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                if (needIntercept(event.x, event.y)) {
                    onTouchMove(event)
                    return true
                }
            }

            MotionEvent.ACTION_POINTER_DOWN -> {
                // 超过一个手指之后，放弃吧
                touchMode = TouchMode.CANCELED
            }

            MotionEvent.ACTION_UP -> {
                touchMode = TouchMode.CANCELED
            }

            MotionEvent.ACTION_CANCEL -> {
                touchMode = TouchMode.CANCELED
            }
        }
        return super.onTouchEvent(event)
    }

    private fun onTouchMove(event: MotionEvent) {
        // TODO
    }

    private fun needIntercept(x: Float, y: Float): Boolean {
        when (touchMode) {
            TouchMode.NONE -> {
                val touchDownX = touchDownPoint.x
                val touchDownY = touchDownPoint.y
                for (rect in restrictedZoneList) {
                    if (isTouchOnRestricted(rect, touchDownX, touchDownY)) {
                        touchMode = TouchMode.CANCELED
                        return false
                    }
                }
                touchMode = TouchMode.PENDING
                return false
            }

            TouchMode.PENDING -> {
                // 等待了，所以只管判断是否超过滑动阈值就行
                if (abs(x - touchDownPoint.x) > touchSlop || abs(y - touchDownPoint.y) > touchSlop) {
                    touchMode = TouchMode.HOLD
                    return true
                } else {
                    return false
                }
            }

            TouchMode.CANCELED -> {
                return false
            }

            TouchMode.HOLD -> {
                return true
            }
        }
    }

    private fun isTouchOnRestricted(rect: Rect, x: Float, y: Float): Boolean {
        val l = getRestrictedPosition(rect.left, true)
        val t = getRestrictedPosition(rect.top, false)
        val r = getRestrictedPosition(rect.right, true)
        val b = getRestrictedPosition(rect.bottom, false)
        return x >= l && x <= r && y >= t && y <= b
    }

    private fun getRestrictedPosition(value: Int, isHorizontal: Boolean): Int {
        when (value) {
            POSITION_PARENT_LEFT -> {
                return 0
            }

            POSITION_PARENT_TOP -> {
                return 0
            }

            POSITION_PARENT_RIGHT -> {
                return width
            }

            POSITION_PARENT_BOTTOM -> {
                return height
            }

            POSITION_PARENT_CENTER -> {
                if (isHorizontal) {
                    return width / 2
                } else {
                    return height / 2
                }
            }

            else -> {
                return value
            }
        }
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

    private enum class TouchMode {
        /**
         * 初始状态，表示还没有开始处理，所以可以继续处理
         * 每次 down 都会重新计算一次
         */
        NONE,

        /**
         * 表示点击位置不在禁区中，可以考虑拦截，但是还没有决定
         */
        PENDING,

        /**
         * 不适合拦截，因此放弃处理
         */
        CANCELED,

        /**
         * 拦截手势，表示自己在处理了
         */
        HOLD,


    }

}