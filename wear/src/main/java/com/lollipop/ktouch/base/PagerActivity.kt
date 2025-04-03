package com.lollipop.ktouch.base

import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.lollipop.ktouch.databinding.ActivityPageManagerBinding

abstract class PagerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityPageManagerBinding.inflate(layoutInflater)
    }

    protected abstract val pageArray: Array<Class<out SubPager>>

    private val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    private val autoHideIndicatorTask = Runnable {
        hideIndicator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(binding.root)
        binding.viewPager.adapter = PagerAdapter(this, pageArray)
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.pageIndicator.bind(binding.viewPager)
        binding.viewPager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    postIndicatorChanged(state == ViewPager2.SCROLL_STATE_IDLE)
                }
            }
        )
        binding.viewPager.setPageTransformer(ScaledPageTransformer())
        binding.copyrightView.setTypeface(
            Typeface.createFromAsset(
                assets,
                "fonts/MeowScript.ttf"
            ),
            Typeface.NORMAL
        )
        isPagerScrollEnabled(true)
    }

    protected fun isPagerScrollEnabled(enable: Boolean) {
        binding.viewPager.isUserInputEnabled = enable
    }

    private fun hideIndicator() {
        binding.pageIndicator.animate().apply {
            cancel()
            alpha(0f)
            duration = 300L
            start()
        }
    }

    private fun showIndicator() {
        binding.pageIndicator.animate().apply {
            cancel()
            alpha(1f)
            duration = 300L
            start()
        }
    }

    override fun onResume() {
        super.onResume()
        postIndicatorChanged(true)
    }

    private fun postIndicatorChanged(isIdle: Boolean) {
        if (isIdle) {
            mainHandler.removeCallbacks(autoHideIndicatorTask)
            mainHandler.postDelayed(autoHideIndicatorTask, 3000)
        } else {
            mainHandler.removeCallbacks(autoHideIndicatorTask)
            showIndicator()
        }
    }

    class PagerAdapter(
        activity: AppCompatActivity,
        private val pageArray: Array<Class<out SubPager>>
    ) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int {
            return pageArray.size
        }

        override fun createFragment(position: Int): Fragment {
            return pageArray[position].getDeclaredConstructor().newInstance()
        }

    }

    private class ScaledPageTransformer(
        val minScale: Float = 0.8F,
        val minAlpha: Float = 1F
    ) : ViewPager2.PageTransformer {

        override fun transformPage(page: View, position: Float) {
            page.apply {
                val pageWidth = width
                val pageHeight = height
                when {
                    position < -1 -> { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        alpha = 0f
                    }

                    position <= 1 -> { // [-1,1]
                        // Modify the default slide transition to shrink the page as well.
                        val scaleFactor = Math.max(minScale, 1 - Math.abs(position))
                        val vertMargin = pageHeight * (1 - scaleFactor) / 2
                        val horzMargin = pageWidth * (1 - scaleFactor) / 2
                        translationX = if (position < 0) {
                            horzMargin - vertMargin / 2
                        } else {
                            horzMargin + vertMargin / 2
                        }

                        // Scale the page down (between MIN_SCALE and 1).
                        scaleX = scaleFactor
                        scaleY = scaleFactor

                        // Fade the page relative to its size.
                        alpha =
                            (minAlpha + (((scaleFactor - minScale) / (1 - minScale)) * (1 - minAlpha)))
                    }

                    else -> { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        alpha = 0f
                    }
                }
            }
        }

    }
}