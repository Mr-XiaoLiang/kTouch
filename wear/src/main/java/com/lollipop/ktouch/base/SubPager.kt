package com.lollipop.ktouch.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.lollipop.ktouch.databinding.FragmentSubPageBinding

abstract class SubPager : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPageModeChange(getSubPageMode())
        binding?.langTouchMask?.setOnLongClickListener {
            if (getSubPageMode() == SubPageMode.Display) {
                onPageLongClick()
                true
            } else {
                false
            }
        }
        binding?.langTouchMask?.setOnClickListener {
            if (getSubPageMode() == SubPageMode.Edit) {
                postDisplayMode()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.contentGroup?.apply {
            val scale = when (getSubPageMode()) {
                SubPageMode.Display -> 1F
                SubPageMode.Edit -> 0.9F
            }
            scaleX = scale
            scaleY = scale
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onPageLongClick() {
        val pageMode = getSubPageMode()
        when (pageMode) {
            SubPageMode.Display -> {
                postEditMode()
            }

            SubPageMode.Edit -> {
                postDisplayMode()
            }
        }
    }

    abstract fun createContentView(parent: ViewGroup): View

    @CallSuper
    open fun onPageModeChange(mode: SubPageMode) {
        when (mode) {
            SubPageMode.Display -> {
                binding?.contentGroup?.animate()?.apply {
                    cancel()
                    scaleX(1F)
                    scaleY(1F)
                    start()
                }
            }

            SubPageMode.Edit -> {
                binding?.contentGroup?.animate()?.apply {
                    cancel()
                    scaleX(0.9F)
                    scaleY(0.9F)
                    start()
                }
            }
        }
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