package com.lollipop.ktouch.heisei

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.lollipop.ktouch.base.RiderIconManager
import com.lollipop.ktouch.base.RiderIconNeonManager
import com.lollipop.ktouch.base.SubPager
import com.lollipop.resource.Rider

abstract class HeiseiSubPage : SubPager() {


    protected val iconManager by lazy {
        RiderIconManager()
    }

    protected val selectedRiderList = ArrayList<Rider>()

    protected var currentState = State.INIT

    protected var neonManager: RiderIconNeonManager? = null

    private val mainHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    protected fun preloadRider(context: Context, vararg riderArray: Rider) {
        for (rider in riderArray) {
            soundPlayer.preload(context, rider.nameSound, rider.skillSound)
        }
    }

    protected fun newNeon(clearOld: Boolean = true): RiderIconNeonManager {
        if (clearOld) {
            neonManager?.cancel()
            neonManager = null
        }
        val neon = iconManager.neon()
        neonManager = neon
        return neon
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iconManager.bindCallback(::onRiderClick)
    }

    protected fun bindRider(rider: Rider, iconView: ImageView, maskView: ImageView?) {
        iconManager.bind(rider, iconView, maskView)
        iconManager.putPlayer(rider)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconManager.removeAllRider()
    }

    protected fun postDelay(delay: Long, action: () -> Unit) {
        mainHandler.postDelayed({
            action()
        }, delay)
    }

    protected abstract fun onRiderClick(rider: Rider)

    enum class State {
        INIT,
        READY,
    }

}