package com.lollipop.ktouch

import android.content.Intent
import com.lollipop.ktouch.base.PagerActivity
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.faiz.FaizMenuActivity
import com.lollipop.ktouch.heisei.DcdHeiseiActivity
import com.lollipop.ktouch.main.DecadeRiderPage
import com.lollipop.ktouch.main.FaizRiderPage
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class MainActivity : PagerActivity(), DecadeRiderPage.Callback, FaizRiderPage.Callback {

    override val pageArray: Array<Class<out SubPager>> by lazy {
        arrayOf(
            DecadeRiderPage.DecadeNormal::class.java,
            DecadeRiderPage.Decade21::class.java,
            FaizRiderPage::class.java
        )
    }

    override fun onResume() {
        super.onResume()
        SoundManager.play(SoundKey.DeviceBoot)
    }

    override fun onDecadeIconClick(mode: DecadeRiderPage.Mode) {
        when (mode) {
            DecadeRiderPage.Mode.Normal -> {
                DcdHeiseiActivity.start(this, DcdHeiseiActivity.SubPage.FINALLY)
            }

            DecadeRiderPage.Mode.Number21 -> {
                DcdHeiseiActivity.start(this, DcdHeiseiActivity.SubPage.FINALLY21)
            }
        }
    }

    override fun onRiderClick(rider: Rider) {
        when (rider) {
            Rider.Faiz -> {
                startActivity(Intent(this, FaizMenuActivity::class.java))
            }

            else -> {}
        }
    }

}