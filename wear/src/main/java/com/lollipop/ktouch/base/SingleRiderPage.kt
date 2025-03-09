package com.lollipop.ktouch.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.databinding.FragmentSingleRiderBinding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundManager

/**
 * 暂时特效不全，先忽略吧
 */
abstract class SingleRiderPage : SubPager() {

    private var contentBinding: FragmentSingleRiderBinding? = null

    protected abstract fun optRider(): Rider

    protected var callback: Callback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = findCallback(context)
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            riderIconView.setImageResource(optRider().icon)
        }
    }

    protected open fun onRiderIconClick() {
        callback?.onRiderClick(optRider())
    }

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentSingleRiderBinding.inflate(layoutInflater, parent, false)
        contentBinding = newBinding
        return newBinding.root
    }

    interface Callback {

        fun onRiderClick(rider: Rider)

    }

}