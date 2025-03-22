package com.lollipop.resource.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors

object SoundManager {

    private val soundIdMap = ConcurrentHashMap<SoundKey, Int>()
    private val soundPlayTimeMap = ConcurrentHashMap<SoundKey, Long>()
    private val streamIdMap = ConcurrentHashMap<SoundKey, Int>()

    private val soundPool by lazy {
        createSoundPool()
    }

    private val executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    private fun now(): Long {
        return System.currentTimeMillis()
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

    fun isPlaying(soundKey: SoundKey): Boolean {
        soundIdMap[soundKey] ?: return false
        val startTime = soundPlayTimeMap[soundKey] ?: return false
        return now() - startTime <= soundKey.timeMillis
    }

    private fun rememberPlay(soundKey: SoundKey, streamId: Int) {
        soundPlayTimeMap[soundKey] = now()
        streamIdMap[soundKey] = streamId
    }

    fun load(context: Context, soundKey: SoundKey) {
        if (soundIdMap.containsKey(soundKey)) {
            return
        }
        executor.execute {
            loadSync(context, soundKey)
        }
    }

    fun loadSync(context: Context, soundKey: SoundKey): Int {
        try {
            if (soundIdMap.containsKey(soundKey)) {
                return soundIdMap[soundKey] ?: 0
            }
            val id = soundPool.load(context, soundKey.resId, 1)
            soundIdMap[soundKey] = id
        } catch (e: Exception) {
            Log.e("SoundManager", "load sound error", e)
        }
        return 0
    }

    fun play(soundKey: SoundKey): Int {
        val id = soundIdMap[soundKey] ?: return 0
        val playId = soundPool.play(id, 1f, 1f, 1, 0, 1f)
        rememberPlay(soundKey, playId)
        return playId
    }

    fun pause(streamID: Int) {
        soundPool.pause(streamID)
    }

    fun resume(streamID: Int) {
        soundPool.resume(streamID)
    }

    fun stop(streamID: Int) {
        soundPool.stop(streamID)
    }

    fun optStop(soundKey: SoundKey) {
        if (isPlaying(soundKey)) {
            val streamId = streamIdMap[soundKey] ?: return
            stop(streamId)
        }
    }

    fun preload(context: Context, array: Array<SoundKey>) {
        array.forEach {
            load(context, it)
        }
    }

}