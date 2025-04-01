package com.lollipop.ktouch.heisei

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.neon.RippleNeon
import com.lollipop.ktouch.databinding.FragmentCardHeisei21Binding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class DcdHeisei21SubPage : DcdHeiseiSubPage() {

    private var binding: FragmentCardHeisei21Binding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SoundManager.load(context, SoundKey.HeiseiDcdFinally21)
        preloadRider(
            context,
            Rider.Double,
            Rider.Ooo,
            Rider.Fourze,
            Rider.Wizard,
            Rider.Gaim,
            Rider.Drive,
            Rider.Ghost,
            Rider.ExAid,
            Rider.Build,
            Rider.Zio,
            Rider.Decade,
        )
    }

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei21Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            bindRider(Rider.Double, doubleImageView, doubleMaskView)
            bindRider(Rider.Ooo, oooImageView, oooMaskView)
            bindRider(Rider.Fourze, fourzeImageView, fourzeMaskView)
            bindRider(Rider.Wizard, wizardImageView, wizardMaskView)
            bindRider(Rider.Gaim, gaimImageView, gaimMaskView)
            bindRider(Rider.Drive, driveImageView, driveMaskView)
            bindRider(Rider.Ghost, ghostImageView, ghostMaskView)
            bindRider(Rider.ExAid, exaidImageView, exaidMaskView)
            bindRider(Rider.Build, buildImageView, buildMaskView)
            bindRider(Rider.Zio, zioImageView, zioMaskView)
            bindRider(Rider.Decade, dcdImageView, dcdMaskView)

            funcImageView.setOnClickListener { onFClick() }
            cancelImageView.setOnClickListener { onCClick() }
        }
    }


    override fun onResume() {
        super.onResume()
        SoundManager.play(SoundKey.DeviceBoot21)
    }

    override fun heiseiSound(): SoundKey {
        return SoundKey.HeiseiDcdFinally21
    }

    private fun onFClick() {
        when (currentState) {
            State.INIT -> {
                // 这种情况下，就不操作吧
                SoundManager.play(SoundKey.DeviceSpace)
            }

            State.READY -> {
                if (selectedRiderList.isEmpty()) {
                    // 这种情况下，就不操作吧
                    SoundManager.play(SoundKey.DeviceSpace)
                } else {
                    selectedRiderList.lastOrNull()?.let {
                        SoundManager.play(it.skillSound)
                        newNeon(true).play(
                            listOf(
                                RippleNeon.create(
                                    iconManager.playerIndexOf(it),
                                    it.skillSound.timeMillis
                                )
                            )
                        )?.also { anim ->
                            anim.addListener(
                                RiderIconAnimator.AnimationCallbackAdapter(
                                    endCallback = {
                                        iconManager.selectOnly(-1)
                                    }
                                ))
                            anim.start(true)
                        }
                    }
                    selectedRiderList.clear()
                }
            }
        }
    }

    private fun onCClick() {
        SoundManager.play(SoundKey.DeviceSpace)
        if (selectedRiderList.isNotEmpty()) {
            selectedRiderList.clear()
            iconManager.selectOnly(-1)
        } else {
            // TODO 退出的声音需要确认一下
            SoundManager.play(SoundKey.DeviceExit21)
            currentState = State.INIT
        }
    }

}