package com.lollipop.ktouch.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentMainDecadeBinding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundManager

/**
 * 暂时特效不全，先忽略吧
 */
sealed class DecadeRiderPage : SubPager() {

    class DecadeNormal : DecadeRiderPage() {
        override fun updateView(binding: FragmentMainDecadeBinding) {
            binding.number21View.isVisible = false
        }

        override fun getMode(): Mode {
            return Mode.Normal
        }
    }

    class Decade21 : DecadeRiderPage() {
        override fun updateView(binding: FragmentMainDecadeBinding) {
            binding.number21View.isVisible = true
        }

        override fun getMode(): Mode {
            return Mode.Number21
        }
    }

    private var contentBinding: FragmentMainDecadeBinding? = null

    protected var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = findCallback(context)
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contentBinding?.apply {
            riderIconView.setOnClickListener {
                onRiderIconClick()
            }
            riderIconView.setImageResource(Rider.Decade.icon)
            updateView(this)
        }
    }

    protected abstract fun updateView(binding: FragmentMainDecadeBinding)

    protected open fun onRiderIconClick() {
        callback?.onDecadeIconClick(getMode())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentMainDecadeBinding.inflate(
            inflater, container, false
        )
        contentBinding = newBinding
        return newBinding.root
    }

    protected abstract fun getMode(): Mode

    interface Callback {

        fun onDecadeIconClick(mode: Mode)

    }

    enum class Mode {
        Normal,
        Number21
    }

}