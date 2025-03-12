package com.lollipop.ktouch.base

import android.widget.ImageView
import com.lollipop.resource.sound.Rider

class RiderIconManager(
    private var onRiderClickCallback: OnRiderClickCallback? = null
) {

    private val maskIconDelegateMap = HashMap<Rider, MaskIconDelegate>()

    private var animationList = ArrayList<MaskIconDelegate>()

    val animationIconCount: Int
        get() {
            return animationList.size
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
        animationList.clear()
    }

    fun putAnimationList(rider: Rider) {
        maskIconDelegateMap[rider]?.let {
            animationList.add(it)
        }
    }

    fun selectOnly(index: Int) {
        for (i in animationList.indices) {
            val maskIconDelegate = animationList[i]
            maskIconDelegate.setSelect(i == index)
        }
    }

    fun selectTo(index: Int) {
        for (i in animationList.indices) {
            val maskIconDelegate = animationList[i]
            maskIconDelegate.setSelect(i <= index)
        }
    }

    fun selectFrom(index: Int) {
        for (i in animationList.indices) {
            val maskIconDelegate = animationList[i]
            maskIconDelegate.setSelect(i >= index)
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