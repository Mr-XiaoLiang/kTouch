package com.lollipop.ktouch

import android.content.Intent
import com.lollipop.ktouch.base.PagerActivity
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.main.MainRiderPage
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class MainActivity : PagerActivity(), MainRiderPage.Callback {

    override val pageArray: Array<Class<out SubPager>> by lazy {
        arrayOf(
            MainRiderPage.Decade::class.java
        )
    }

    override fun onResume() {
        super.onResume()
        SoundManager.play(SoundKey.DeviceBoot)
    }

    override fun onRiderClick(rider: Rider) {
        if (rider == Rider.Decade) {
            startActivity(Intent(this, DcdHeiseiActivity::class.java))
        }
    }

}