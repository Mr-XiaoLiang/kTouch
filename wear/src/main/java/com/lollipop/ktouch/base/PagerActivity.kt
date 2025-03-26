package com.lollipop.ktouch.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.lollipop.ktouch.databinding.ActivityPageManagerBinding

abstract class PagerActivity : AppCompatActivity(), SubPager.Callback {

    private var currentPageMode = SubPageMode.Display

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
        notifyPagerModeChange(SubPageMode.Display)
        binding.pageIndicator.bind(binding.viewPager)
    }

    override fun getSubPageMode(): SubPageMode {
        return currentPageMode
    }

    override fun postPageMode(mode: SubPageMode) {
        notifyPagerModeChange(mode)
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

    private fun notifyPagerModeChange(newMode: SubPageMode) {
        binding.viewPager.isUserInputEnabled = newMode == SubPageMode.Edit
        currentPageMode = newMode
        supportFragmentManager.fragments.forEach {
            if (it is SubPager) {
                if (it.isResumed && it.isVisible) {
                    it.onPageModeChange(newMode)
                }
            }
        }
        postIndicatorChanged()
    }

    override fun onResume() {
        super.onResume()
        postIndicatorChanged()
    }

    private fun postIndicatorChanged() {
        if (currentPageMode == SubPageMode.Display) {
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