package com.lollipop.ktouch.widget

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import kotlin.math.abs

class LongClickFrameLayout @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private var touchSlop = -1
    private val touchDownPoint = PointF()
    private var isLongTouch = false
    private var isTouchMove = false
    private var longTouchTimeout = 500L

    init {
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop
        longTouchTimeout = ViewConfiguration.getLongPressTimeout().toLong()
    }

    private val longPressTimer = Runnable {
        checkLongTouch()
    }

    fun cancelLongTouch() {
        if (!isTouchMove) {
            isTouchMove = true
            removeCallbacks(longPressTimer)
        }
    }

    private fun checkLongTouch() {
        if (!isLongTouch && !isTouchMove) {
            isLongTouch = true
            performLongClick()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        event ?: return super.dispatchTouchEvent(event)
        var isTouchDown = false
        val actionMasked = event.actionMasked
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            touchDownPoint.x = event.x
            touchDownPoint.y = event.y
            isTouchMove = false
            isLongTouch = false
            isTouchDown = true
            postDelayed(longPressTimer, longTouchTimeout)
        }
        if (isLongTouch) {
            return true
        }
        when (actionMasked) {
            MotionEvent.ACTION_MOVE -> {
                if (isTouchMove) {
                    return false
                }
                val x = event.x
                val y = event.y
                if (abs(x - touchDownPoint.x) > touchSlop || abs(y - touchDownPoint.y) > touchSlop) {
                    cancelLongTouch()
                }
            }

            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP, MotionEvent.ACTION_OUTSIDE, MotionEvent.ACTION_POINTER_DOWN -> {
                cancelLongTouch()
            }
        }
        val touchResult = super.dispatchTouchEvent(event)
        return touchResult || isTouchDown
    }

}