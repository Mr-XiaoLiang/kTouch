package com.lollipop.ktouch

import android.app.Application
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class LApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        // 这个启动音效需要同步加载，因为这个启动音效需要立即播放
        SoundManager.loadSync(this, SoundKey.DeviceBoot)
        SoundManager.preload(
            this,
            arrayOf(
                SoundKey.DeviceBoot21,
                SoundKey.DeviceExit21,
                SoundKey.DeviceSpace,
            )
        )
    }

}