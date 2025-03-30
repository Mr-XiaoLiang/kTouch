package com.lollipop.ktouch.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

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

        private fun getLength(
            x1: Float,
            y1: Float,
            x2: Float,
            y2: Float
        ): Float {
            return kotlin.math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
        }

    }

    private val restrictedZoneList = ArrayList<RestrictedZone>()
    private val touchCircleCenter = PointF()
    private var arcTouchListener: OnArcTouchListener? = null

    private var touchSlop = 0
    private val touchDownPoint = PointF()
    private var touchMode = TouchMode.NONE

    fun addRestrictedZone(zone: RestrictedZone) {
        restrictedZoneList.add(zone)
    }

    fun clearRestrictedZone() {
        restrictedZoneList.clear()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val contentLeft = paddingLeft
        val contentRight = width - paddingRight
        val contentTop = paddingTop
        val contentBottom = height - paddingBottom
        touchCircleCenter.set(
            (contentRight + contentLeft) * 0.5F,
            (contentBottom + contentTop) * 0.5F
        )
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
        return super.onInterceptTouchEvent(ev) || touchMode == TouchMode.HOLD
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
        return touchMode == TouchMode.HOLD || super.onTouchEvent(event)
    }

    private fun onTouchMove(event: MotionEvent) {
        val listener = arcTouchListener ?: return
        val touchX = event.x
        val touchY = event.y
        val radius = getTouchPointRadius(touchX, touchY)
        val angle = getTouchPointAngle(touchX, touchY)
        listener.onTouch(
            this,
            angle,
            radius,
            touchCircleCenter.x,
            touchCircleCenter.y,
            touchX,
            touchY
        )
    }

    private fun needIntercept(x: Float, y: Float): Boolean {
        when (touchMode) {
            TouchMode.NONE -> {
                val touchDownX = touchDownPoint.x
                val touchDownY = touchDownPoint.y
                val viewWidth = width
                val viewHeight = height
                for (zone in restrictedZoneList) {
                    if (zone.isTouchOnRestricted(viewWidth, viewHeight, touchDownX, touchDownY)) {
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

    private fun getTouchPointRadius(touchX: Float, touchY: Float): Float {
        val centerX = touchCircleCenter.x
        val centerY = touchCircleCenter.y
        return getLength(touchX, touchY, centerX, centerY)
    }

    private fun getTouchPointAngle(touchX: Float, touchY: Float): Float {
        val centerX = touchCircleCenter.x
        val centerY = touchCircleCenter.y
        return DeviceHelper.calculateCentralAngle(
            centerX, centerY, // 圆心
            centerX, 0F, // 锚点是View12点钟的点
            touchX, touchY
        )
    }

    interface OnArcTouchListener {
        fun onTouch(
            view: ArcTouchLayout,
            angle: Float,
            radius: Float,
            centerX: Float,
            centerY: Float,
            touchX: Float,
            touchY: Float
        )
    }

    sealed class RestrictedZone {

        abstract fun isTouchOnRestricted(
            viewWidth: Int,
            viewHeight: Int,
            x: Float,
            y: Float
        ): Boolean

        class Rect(
            val left: Int,
            val top: Int,
            val right: Int,
            val bottom: Int
        ) : RestrictedZone() {

            override fun isTouchOnRestricted(
                viewWidth: Int,
                viewHeight: Int,
                x: Float,
                y: Float
            ): Boolean {
                val l = getRestrictedPosition(viewWidth, viewHeight, left, true)
                val t = getRestrictedPosition(viewWidth, viewHeight, top, false)
                val r = getRestrictedPosition(viewWidth, viewHeight, right, true)
                val b = getRestrictedPosition(viewWidth, viewHeight, bottom, false)
                return x >= l && x <= r && y >= t && y <= b
            }

            private fun getRestrictedPosition(
                viewWidth: Int,
                viewHeight: Int,
                value: Int,
                isHorizontal: Boolean
            ): Int {
                when (value) {
                    POSITION_PARENT_LEFT -> {
                        return 0
                    }

                    POSITION_PARENT_TOP -> {
                        return 0
                    }

                    POSITION_PARENT_RIGHT -> {
                        return viewWidth
                    }

                    POSITION_PARENT_BOTTOM -> {
                        return viewHeight
                    }

                    POSITION_PARENT_CENTER -> {
                        return if (isHorizontal) {
                            viewWidth / 2
                        } else {
                            viewHeight / 2
                        }
                    }

                    else -> {
                        return value
                    }
                }
            }

        }

        class Circle(
            val radiusFrom: Int,
            val radiusTo: Int,
        ) : RestrictedZone() {

            override fun isTouchOnRestricted(
                viewWidth: Int,
                viewHeight: Int,
                x: Float,
                y: Float
            ): Boolean {
                val centerX = viewWidth * 0.5F
                val centerY = viewHeight * 0.5F
                val radius = getLength(centerX, centerY, x, y)
                return radius >= radiusFrom && radius <= radiusTo
            }

        }

        class Annulus(
            val width: Int,
            val offset: Int,
            val insideMode: Boolean,
        ) : RestrictedZone() {

            override fun isTouchOnRestricted(
                viewWidth: Int,
                viewHeight: Int,
                x: Float,
                y: Float
            ): Boolean {
                val maxRadius = if (insideMode) {
                    min(viewWidth, viewHeight) / 2
                } else {
                    max(viewWidth, viewHeight) / 2
                }
                val radiusTo = maxRadius - offset
                val radiusFrom = radiusTo - width
                val centerX = viewWidth * 0.5F
                val centerY = viewHeight * 0.5F
                val radius = getLength(centerX, centerY, x, y)
                return radius >= radiusFrom && radius <= radiusTo
            }

        }

    }

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