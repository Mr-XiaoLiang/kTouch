package com.lollipop.ktouch.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.lollipop.resource.sound.SoundPage
import com.lollipop.resource.sound.SoundPlayer

abstract class SubPager : Fragment(), SoundPage {

    protected val soundPlayer = SoundPlayer.GroupPlayer()

    override fun stopAll() {
        soundPlayer.stopAll()
    }

    protected inline fun <reified T> findCallback(c: Context): T? {
        parentFragment?.let {
            if (it is T) {
                return it
            }
        }
        if (c is T) {
            return c
        }
        activity?.let {
            if (it is T) {
                return it
            }
        }
        context?.let {
            if (it is T) {
                return it
            }
        }
        return null
    }

}