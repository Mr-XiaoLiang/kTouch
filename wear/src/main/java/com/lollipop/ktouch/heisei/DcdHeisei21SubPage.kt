package com.lollipop.ktouch.heisei

import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding
import com.lollipop.ktouch.databinding.FragmentCardHeisei21Binding

class DcdHeisei21SubPage: SubPager() {

    private var binding: FragmentCardHeisei21Binding? = null


    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei21Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }
}