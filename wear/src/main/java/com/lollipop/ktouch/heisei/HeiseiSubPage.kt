package com.lollipop.ktouch.heisei

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.lollipop.ktouch.base.RiderIconManager
import com.lollipop.ktouch.base.RiderIconNeonManager
import com.lollipop.ktouch.base.SubPager
import com.lollipop.resource.sound.Rider

abstract class HeiseiSubPage : SubPager() {


    protected val iconManager by lazy {
        RiderIconManager()
    }

    protected var neonManager: RiderIconNeonManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iconManager.bindCallback(::onRiderClick)
    }

    protected fun bindRider(rider: Rider, iconView: ImageView, maskView: ImageView?) {
        iconManager.bind(rider, iconView, maskView)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconManager.removeAllRider()
    }

    protected abstract fun onRiderClick(rider: Rider)

}