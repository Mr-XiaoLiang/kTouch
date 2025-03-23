package com.lollipop.resource.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object SoundManager {

    private val soundPlayTimeMap = ConcurrentHashMap<SoundKey, Long>()
    private val soundPoolList = ArrayList<SoundPlayer>()

    private val executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    private fun now(): Long {
        return System.currentTimeMillis()
    }

    private fun findPool(key: SoundKey): SoundPlayer? {
        for (pool in soundPoolList) {
            if (pool.has(key)) {
                return pool
            }
        }
        return null
    }

    fun isPlaying(soundKey: SoundKey): Boolean {
        val startTime = soundPlayTimeMap[soundKey] ?: return false
        return now() - startTime <= soundKey.timeMillis
    }

    private fun rememberPlay(soundKey: SoundKey) {
        soundPlayTimeMap[soundKey] = now()
    }

    fun load(context: Context, soundKey: SoundKey) {
        Log.i("SoundManager", "load sound: $soundKey")
        if (findPool(soundKey) != null) {
            return
        }
        executor.execute {
            loadSync(context, soundKey)
        }
    }

    fun loadSync(context: Context, soundKey: SoundKey) {
        Log.i("SoundManager", "loadSync sound: $soundKey")
        try {
            for (delegate in soundPoolList) {
                if (delegate.optLoadSync(context, soundKey)) {
                    Log.i("SoundManager", "loadSync Success: $delegate")
                    return
                }
            }
            val delegate: SoundPlayer = if (ShortSoundPlayerDelegate.canLoad(soundKey)) {
                ShortSoundPlayerDelegate()
            } else {
                LongSoundPlayerDelegate()
            }
            Log.i("SoundManager", "loadSync new Player: $delegate")
            delegate.optLoadSync(context, soundKey)
            soundPoolList.add(delegate)
        } catch (e: Exception) {
            Log.e("SoundManager", "loadSync sound error", e)
        }
    }

    fun play(soundKey: SoundKey) {
        val pool = findPool(soundKey) ?: return
        pool.play(soundKey)
        rememberPlay(soundKey)
    }

    fun pause(soundKey: SoundKey) {
        findPool(soundKey)?.pause(soundKey)
    }

    fun resume(soundKey: SoundKey) {
        findPool(soundKey)?.resume(soundKey)
    }

    fun stop(soundKey: SoundKey) {
        if (isPlaying(soundKey)) {
            findPool(soundKey)?.stop(soundKey)
            soundPlayTimeMap.remove(soundKey)
        }
    }

    fun preload(context: Context, array: Array<SoundKey>) {
        array.forEach {
            load(context, it)
        }
    }

    private interface SoundPlayer {

        fun has(soundKey: SoundKey): Boolean

        fun canLoad(soundKey: SoundKey): Boolean

        fun play(soundKey: SoundKey)

        fun pause(soundKey: SoundKey)

        fun resume(soundKey: SoundKey)

        fun stop(soundKey: SoundKey)

        fun optLoadSync(context: Context, soundKey: SoundKey): Boolean

    }

    private class ShortSoundPlayerDelegate : SoundPlayer {

        companion object {
            const val MAX_SOUND_TIME_S = 6L

            fun canLoad(soundKey: SoundKey): Boolean {
                return soundKey.timeSecond <= MAX_SOUND_TIME_S
            }

        }

        private val loadSound = HashSet<SoundKey>()
        var soundTimeLength = 0L
            private set
        private val soundPool = createSoundPool()
        private val soundIdMap = ConcurrentHashMap<SoundKey, Int>()
        private val streamIdMap = ConcurrentHashMap<SoundKey, Int>()

        val freeSoundTime: Long
            get() {
                return MAX_SOUND_TIME_S - soundTimeLength
            }

        private fun createSoundPool(): SoundPool {
            val builder = SoundPool.Builder()
            builder.setMaxStreams(10)
            val attrBuilder = AudioAttributes.Builder()
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC)
            builder.setAudioAttributes(attrBuilder.build())
            val pool = builder.build()
            return pool
        }

        override fun has(soundKey: SoundKey): Boolean {
            return loadSound.contains(soundKey)
        }

        override fun canLoad(soundKey: SoundKey): Boolean {
            // 没加载过就不管能不能加载了，毕竟有很多音频超过了10S
            return freeSoundTime >= soundKey.timeSecond
        }

        override fun play(soundKey: SoundKey) {
            val id = soundIdMap[soundKey] ?: return
            val playId = soundPool.play(id, 1f, 1f, 1, 0, 1f)
            streamIdMap[soundKey] = playId
        }

        override fun pause(soundKey: SoundKey) {
            val streamId = streamIdMap[soundKey] ?: return
            soundPool.pause(streamId)
        }

        override fun resume(soundKey: SoundKey) {
            val streamId = streamIdMap[soundKey] ?: return
            soundPool.resume(streamId)
        }

        override fun stop(soundKey: SoundKey) {
            val streamId = streamIdMap[soundKey] ?: return
            soundPool.stop(streamId)
        }

        override fun optLoadSync(context: Context, soundKey: SoundKey): Boolean {
            if (has(soundKey)) {
                return true
            }
            if (canLoad(soundKey)) {
                val id = soundPool.load(context, soundKey.resId, 1)
                soundTimeLength += soundKey.timeSecond
                soundIdMap[soundKey] = id
                loadSound.add(soundKey)
                return true
            }
            return false
        }


    }

    private class LongSoundPlayerDelegate : SoundPlayer {

        private var currentSoundKey: SoundKey? = null

        private var player: MediaPlayer? = null

        override fun has(soundKey: SoundKey): Boolean {
            return soundKey == currentSoundKey
        }

        override fun canLoad(soundKey: SoundKey): Boolean {
            return currentSoundKey == null
        }

        override fun play(soundKey: SoundKey) {
            if (currentSoundKey != soundKey) {
                return
            }
            player?.start()
        }

        override fun pause(soundKey: SoundKey) {
            if (currentSoundKey != soundKey) {
                return
            }
            if (player?.isPlaying == true) {
                player?.pause()
            }
        }

        override fun resume(soundKey: SoundKey) {
            if (currentSoundKey != soundKey) {
                return
            }
            if (player?.isPlaying == false) {
                player?.start()
            }
        }

        override fun stop(soundKey: SoundKey) {
            if (currentSoundKey != soundKey) {
                return
            }
            player?.stop()
        }

        override fun optLoadSync(context: Context, soundKey: SoundKey): Boolean {
            if (currentSoundKey != null) {
                return false
            }
            player?.release()
            currentSoundKey = soundKey
            val mediaPlayer = MediaPlayer.create(context, soundKey.resId)
            player = mediaPlayer
            return true
        }

    }

}