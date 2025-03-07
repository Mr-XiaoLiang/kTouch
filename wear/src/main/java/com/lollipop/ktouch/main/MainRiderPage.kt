package com.lollipop.ktouch.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentMainRiderBinding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundManager

/**
 * 暂时特效不全，先忽略吧
 */
class MainRiderPage : SubPager() {

    companion object {
        private const val ARGUMENTS_ICON = "rider_icon"

        fun putArguments(argument: Bundle, riderIcon: Int) {
            argument.putInt(ARGUMENTS_ICON, riderIcon)
        }

        fun getRiderIcon(argument: Bundle): Int {
            return argument.getInt(ARGUMENTS_ICON)
        }

    }

    private var riderIcon: Int = Rider.Decade.icon
    private var contentBinding: FragmentMainRiderBinding? = null

    private fun optRider(): Rider {
        return Rider.Decade
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val icon = getRiderIcon(arguments ?: Bundle())
        riderIcon = if (icon != 0) {
            icon
        } else {
            Rider.Decade.icon
        }

        context?.let { c ->
            SoundManager.load(c, optRider().nameSound)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding?.apply {
            riderIconView.setOnClickListener {
                onRiderIconClick()
            }
            if (riderIcon != 0) {
                riderIconView.setImageResource(riderIcon)
            }
        }
    }

    private fun onRiderIconClick() {
        // TODO
        Rider.findByIcon(riderIcon)?.let {
            SoundManager.play(it.nameSound)
        }
    }

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentMainRiderBinding.inflate(layoutInflater, parent, false)
        contentBinding = newBinding
        return newBinding.root
    }


}