package com.lollipop.ktouch.heisei

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding
import com.lollipop.resource.sound.Rider

class DcdHeisei10SubPage : HeiseiSubPage() {

    private var binding: FragmentCardHeisei10Binding? = null

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei10Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            iconManager.bind(Rider.Kuuga, kuugaImageView, kuugaMaskView)
            iconManager.bind(Rider.Kiva, kivaImageView, kivaMaskView)
            iconManager.bind(Rider.Kabuto, kabutoImageView, kabutoMaskView)
            iconManager.bind(Rider.Hibiki, hibikiImageView, hibikiMaskView)
            iconManager.bind(Rider.Ryuki, ryukiImageView, ryukiMaskView)
            iconManager.bind(Rider.Faiz, faizImageView, faizMaskView)
            iconManager.bind(Rider.Deno, denoImageView, denoMaskView)
            iconManager.bind(Rider.Decade, dcdImageView, dcdMaskView)
            iconManager.bind(Rider.Agito, agitoImageView, agitoMaskView)
            iconManager.bind(Rider.Blade, bladeImageView, bladeMaskView)
        }
    }

}