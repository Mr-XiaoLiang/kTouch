package com.lollipop.ktouch.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.databinding.FragmentSingleRiderBinding
import com.lollipop.resource.Rider

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentSingleRiderBinding.inflate(
            inflater, container, false
        )
        contentBinding = newBinding
        return newBinding.root
    }

    interface Callback {

        fun onRiderClick(rider: Rider)

    }

}