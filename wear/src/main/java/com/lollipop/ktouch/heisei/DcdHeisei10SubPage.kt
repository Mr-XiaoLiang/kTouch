package com.lollipop.ktouch.heisei

import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding

class DcdHeisei10SubPage: SubPager() {

    private var binding: FragmentCardHeisei10Binding? = null


    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei10Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }
}