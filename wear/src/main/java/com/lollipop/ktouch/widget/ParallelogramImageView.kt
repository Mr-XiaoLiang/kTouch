package com.lollipop.ktouch.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class ParallelogramImageView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null
) : AppCompatImageView(context, attributeSet) {

    private val parallelogramDrawable = ParallelogramDrawable()

    init {
        background?.let {
            if (it is ColorDrawable) {
                parallelogramDrawable.color = it.color
            }
        }
        setBackgroundDrawable(parallelogramDrawable)
    }

    private class ParallelogramDrawable : Drawable() {

        var offsetWeight = 0.2F
            set(value) {
                field = value
                buildPath()
            }

        private val paint = Paint().apply {
            isAntiAlias = true
            isDither = true
            style = Paint.Style.FILL
        }

        var color: Int
            get() {
                return paint.color
            }
            set(value) {
                paint.color = value
            }

        private val rectPath = Path()

        override fun onBoundsChange(bounds: Rect) {
            super.onBoundsChange(bounds)
            buildPath()
        }

        private fun buildPath() {
            val rect = bounds
            rectPath.reset()
            if (rect.isEmpty) {
                return
            }
            val offset = rect.height() * offsetWeight
            rectPath.moveTo(offset, rect.top.toFloat())
            rectPath.lineTo(rect.right.toFloat(), rect.top.toFloat())
            rectPath.lineTo(rect.right - offset, rect.bottom.toFloat())
            rectPath.lineTo(rect.left.toFloat(), rect.bottom.toFloat())
            rectPath.close()
            invalidateSelf()
        }

        override fun draw(canvas: Canvas) {
            canvas.drawPath(rectPath, paint)
        }

        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        override fun getOpacity(): Int {
            return PixelFormat.TRANSPARENT
        }

    }

}