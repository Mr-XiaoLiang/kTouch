package com.lollipop.ktouch.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
        isPagerScrollEnabled(false)
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

}