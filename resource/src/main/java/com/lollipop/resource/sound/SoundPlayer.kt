package com.lollipop.resource.sound

import android.content.Context

sealed class SoundPlayer {

    protected val soundKeyList = HashSet<SoundKey>()

    class GroupPlayer : SoundPlayer(), SoundPageParent {

        private val subPageList = ArrayList<SoundPage>()

        override fun addSubPage(subPage: SoundPage) {
            subPageList.add(subPage)
        }

        override fun removeSubPage(subPage: SoundPage) {
            subPageList.remove(subPage)
        }

        override fun stopAll() {
            soundKeyList.forEach {
                SoundManager.stop(it)
            }
            subPageList.forEach {
                it.stopAll()
            }
        }

    }

    class ChildPlayer : SoundPlayer(), SoundPage {
        override fun stopAll() {
            soundKeyList.forEach {
                SoundManager.stop(it)
            }
        }
    }

    fun preload(context: Context, vararg soundKey: SoundKey) {
        SoundManager.preload(context, soundKey)
        soundKeyList.addAll(soundKey.toSet())
    }

    fun play(soundKey: SoundKey, isLooping: Boolean = false) {
        if (!soundKeyList.contains(soundKey)) {
            return
        }
        SoundManager.play(soundKey, isLooping)
    }

    fun pause(soundKey: SoundKey) {
        if (!soundKeyList.contains(soundKey)) {
            return
        }
        SoundManager.pause(soundKey)
    }

    fun resume(soundKey: SoundKey) {
        if (!soundKeyList.contains(soundKey)) {
            return
        }
        SoundManager.resume(soundKey)
    }

    fun stop(soundKey: SoundKey) {
        if (!soundKeyList.contains(soundKey)) {
            return
        }
        SoundManager.stop(soundKey)
    }

}