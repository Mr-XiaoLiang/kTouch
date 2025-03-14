package com.lollipop.ktouch.heisei

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.RiderIconManager
import com.lollipop.ktouch.base.SubPager
import com.lollipop.ktouch.databinding.FragmentCardHeisei21Binding
import com.lollipop.resource.sound.Rider

class DcdHeisei21SubPage : SubPager() {

    private var binding: FragmentCardHeisei21Binding? = null

    private val iconManager by lazy {
        RiderIconManager()
    }

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei21Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            iconManager.bind(Rider.Ghost, ghostImageView, ghostMaskView)
            iconManager.bind(Rider.Drive, driveImageView, driveMaskView)
            iconManager.bind(Rider.Gaim, gaimImageView, gaimMaskView)
            iconManager.bind(Rider.Fourze, fourzeImageView, fourzeMaskView)
            iconManager.bind(Rider.Wizard, wizardImageView, wizardMaskView)
            iconManager.bind(Rider.Double, doubleImageView, doubleMaskView)
            iconManager.bind(Rider.Build, buildImageView, buildMaskView)
            iconManager.bind(Rider.Ooo, oooImageView, oooMaskView)
            iconManager.bind(Rider.Zio, zioImageView, zioMaskView)
            iconManager.bind(Rider.ExAid, exaidImageView, exaidMaskView)
            iconManager.bind(Rider.Decade, dcdImageView, dcdMaskView)

        }
    }

}