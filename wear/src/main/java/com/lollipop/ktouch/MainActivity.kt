package com.lollipop.ktouch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.lollipop.ktouch.base.PagerActivity
import com.lollipop.ktouch.base.SingleRiderPage
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.ActivityMainForegroundBinding
import com.lollipop.ktouch.faiz.FaizMenuActivity
import com.lollipop.ktouch.heisei.DcdHeiseiActivity
import com.lollipop.ktouch.main.Decade21RiderPage
import com.lollipop.ktouch.main.DecadeRiderPage
import com.lollipop.ktouch.main.FaizRiderPage
import com.lollipop.ktouch.widget.DeviceHelper
import com.lollipop.resource.Rider
import com.lollipop.resource.sound.SoundKey

class MainActivity : PagerActivity(), SingleRiderPage.Callback {

    override val pageArray: Array<Class<out SubPager>> by lazy {
        arrayOf(
            DecadeRiderPage::class.java,
            Decade21RiderPage::class.java,
            FaizRiderPage::class.java
        )
    }

    private var displayMode = DisplayMode.Wear

    private var foregroundView: ActivityMainForegroundBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        soundPlayer.preload(this, SoundKey.DeviceSpace)
        foregroundView?.also {
            it.root.isVisible = false
            it.modeIconButton.setOnClickListener { onDisplayModeButtonClick() }
        }
    }

    override fun onResume() {
        super.onResume()
        soundPlayer.play(SoundKey.DeviceBoot)
        updateDisplayMode()
    }

    override fun createForegroundView(parent: ViewGroup): View {
        val binding = ActivityMainForegroundBinding.inflate(layoutInflater, parent, false)
        foregroundView = binding
        return binding.root
    }

    private fun onDisplayModeButtonClick() {
        soundPlayer.play(SoundKey.DeviceSpace)
        val newMode = when (displayMode) {
            DisplayMode.Wear -> DisplayMode.Phone
            DisplayMode.Phone -> DisplayMode.Wear
        }
        displayMode = newMode
        updateDisplayModeButton()
    }

    private fun updateDisplayMode() {
        val isPhone = DeviceHelper.isPhone(this)
        foregroundView?.also { it.root.isVisible = isPhone }
        updateDisplayModeButton()
    }

    private fun updateDisplayModeButton() {
        foregroundView?.also {
            val icon = when (displayMode) {
                DisplayMode.Wear -> R.drawable.ic_baseline_watch_24
                DisplayMode.Phone -> R.drawable.ic_baseline_smartphone_24
            }
            it.modeIconButton.setImageResource(icon)
        }
    }

    override fun onRiderClick(rider: Rider) {
        when (rider) {
            Rider.Faiz -> {
                startActivity(Intent(this, FaizMenuActivity::class.java))
            }

            Rider.Decade -> {
                DcdHeiseiActivity.start(this, DcdHeiseiActivity.SubPage.FINALLY)
            }

            Rider.Decade21 -> {
                DcdHeiseiActivity.start(this, DcdHeiseiActivity.SubPage.FINALLY21)
            }

            else -> {}
        }
    }

    private enum class DisplayMode {
        Wear,
        Phone
    }

}