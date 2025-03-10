package com.lollipop.ktouch.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.lollipop.ktouch.databinding.FragmentSubPageBinding

abstract class SubPager : Fragment() {

    companion object {
        const val SCALE_FULL = 1F
        const val SCALE_SMALL = 0.8F
    }

    private var callback: Callback? = null

    private var binding: FragmentSubPageBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = findCallback<Callback>(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val newBinding = FragmentSubPageBinding.inflate(inflater, container, false)
        binding = newBinding
        newBinding.contentGroup.addView(
            createContentView(newBinding.contentGroup),
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        return newBinding.root
    }

    protected fun onMaskClick() {
        if (getSubPageMode() == SubPageMode.Edit) {
            postDisplayMode()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateByMode(getSubPageMode(), false)
        binding?.contentGroup?.setOnLongClickListener {
            if (getSubPageMode() == SubPageMode.Display) {
                onPageLongClick()
                true
            } else {
                false
            }
        }
        binding?.touchMask?.setOnClickListener {
            onMaskClick()
        }
    }

    override fun onResume() {
        super.onResume()
        updateByMode(getSubPageMode(), false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onPageLongClick() {
        if (getSubPageMode() == SubPageMode.Display) {
            postEditMode()
        }
    }

    abstract fun createContentView(parent: ViewGroup): View

    @CallSuper
    open fun onPageModeChange(mode: SubPageMode) {
        updateByMode(mode, true)
    }

    private fun updateByMode(mode: SubPageMode, animation: Boolean) {
        if (animation) {
            when (mode) {
                SubPageMode.Display -> {
                    binding?.contentGroup?.animate()?.apply {
                        cancel()
                        scaleX(SCALE_FULL)
                        scaleY(SCALE_FULL)
                        start()
                    }
                }

                SubPageMode.Edit -> {
                    binding?.contentGroup?.animate()?.apply {
                        cancel()
                        scaleX(SCALE_SMALL)
                        scaleY(SCALE_SMALL)
                        start()
                    }
                }
            }
        } else {
            binding?.contentGroup?.apply {
                val scale = when (getSubPageMode()) {
                    SubPageMode.Display -> SCALE_FULL
                    SubPageMode.Edit -> SCALE_SMALL
                }
                scaleX = scale
                scaleY = scale
            }
        }
        binding?.touchMask?.isVisible = mode == SubPageMode.Edit
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    protected fun getSubPageMode(): SubPageMode {
        return callback?.getSubPageMode() ?: SubPageMode.Display
    }

    protected fun postEditMode() {
        postPageMode(SubPageMode.Edit)
    }

    protected fun postDisplayMode() {
        postPageMode(SubPageMode.Display)
    }

    protected fun postPageMode(mode: SubPageMode) {
        callback?.postPageMode(mode)
    }

    protected inline fun <reified T> findCallback(c: Context): T? {
        parentFragment?.let {
            if (it is T) {
                return it
            }
        }
        if (c is T) {
            return c
        }
        activity?.let {
            if (it is T) {
                return it
            }
        }
        context?.let {
            if (it is T) {
                return it
            }
        }
        return null
    }

    interface Callback {

        fun getSubPageMode(): SubPageMode

        fun postPageMode(mode: SubPageMode)

    }

}