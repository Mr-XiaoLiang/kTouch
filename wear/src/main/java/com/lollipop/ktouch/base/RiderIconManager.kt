package com.lollipop.ktouch.base

import android.widget.ImageView
import com.lollipop.resource.sound.Rider

class RiderIconManager(
    private var onRiderClickCallback: OnRiderClickCallback? = null
): RiderIconGroup {

    private val maskIconDelegateMap = HashMap<Rider, MaskIconDelegate>()

    private var playerList = ArrayList<MaskIconDelegate>()

    override val playerIconCount: Int
        get() {
            return playerList.size
        }

    fun bind(
        rider: Rider,
        iconView: ImageView,
        maskView: ImageView?,
    ): MaskIconDelegate {
        val maskIconDelegate = MaskIconDelegate(iconView, maskView)
        maskIconDelegate.setSelect(false)
        maskIconDelegate.onIconClick(RiderClickCallback(rider, ::onRiderClick))
        maskIconDelegateMap[rider] = maskIconDelegate
        return maskIconDelegate
    }

    fun neon(): RiderIconNeonManager {
        return RiderIconNeonManager(this)
    }

    fun bindCallback(callback: OnRiderClickCallback) {
        onRiderClickCallback = callback
    }

    fun find(rider: Rider): MaskIconDelegate? {
        return maskIconDelegateMap[rider]
    }

    fun playAnimation(rider: Rider) {
        maskIconDelegateMap[rider]?.playAnimation()
    }

    fun reset(rider: Rider) {
        maskIconDelegateMap[rider]?.reset()
    }

    fun resetAll() {
        maskIconDelegateMap.values.forEach {
            it.reset()
        }
    }

    fun clearAnimationList() {
        playerList.clear()
    }

    fun putAnimationList(rider: Rider) {
        maskIconDelegateMap[rider]?.let {
            playerList.add(it)
        }
    }

    override fun selectOnly(index: Int) {
        for (i in playerList.indices) {
            val maskIconDelegate = playerList[i]
            maskIconDelegate.setSelect(i == index)
        }
    }

    override fun selectTo(index: Int) {
        for (i in playerList.indices) {
            val maskIconDelegate = playerList[i]
            maskIconDelegate.setSelect(i <= index)
        }
    }

    override fun selectFrom(index: Int) {
        for (i in playerList.indices) {
            val maskIconDelegate = playerList[i]
            maskIconDelegate.setSelect(i >= index)
        }
    }

    override fun setSelect(index: Int, isSelected: Boolean) {
        if (index >= 0 && index < playerList.size) {
            playerList[index].setSelect(isSelected)
        }
    }

    private fun onRiderClick(rider: Rider) {
        onRiderClickCallback?.onRiderClick(rider)
    }

    private class RiderClickCallback(
        private val rider: Rider,
        private val callback: (Rider) -> Unit
    ) : MaskIconDelegate.OnClickCallback {
        override fun onClick() {
            callback(rider)
        }
    }

    fun interface OnRiderClickCallback {
        fun onRiderClick(rider: Rider)
    }

}