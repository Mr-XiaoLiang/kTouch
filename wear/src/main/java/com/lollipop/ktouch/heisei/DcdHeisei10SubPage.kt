package com.lollipop.ktouch.heisei

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.RiderIconAnimator
import com.lollipop.ktouch.base.neon.RippleNeon
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class DcdHeisei10SubPage : DcdHeiseiSubPage() {

    private var binding: FragmentCardHeisei10Binding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SoundManager.load(context, SoundKey.HeiseiDcdFinally)
        preloadRider(
            context,
            Rider.Kuuga,
            Rider.Agito,
            Rider.Ryuki,
            Rider.Faiz,
            Rider.Blade,
            Rider.Hibiki,
            Rider.Kabuto,
            Rider.Deno,
            Rider.Kiva,
            Rider.Decade,
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val heisei10Binding = FragmentCardHeisei10Binding.inflate(
            inflater, container, false
        )
        this.binding = heisei10Binding
        return heisei10Binding.root
    }

    override fun heiseiSound(): SoundKey {
        return SoundKey.HeiseiDcdFinally
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            bindRider(Rider.Kuuga, kuugaImageView, kuugaMaskView)
            bindRider(Rider.Agito, agitoImageView, agitoMaskView)
            bindRider(Rider.Ryuki, ryukiImageView, ryukiMaskView)
            bindRider(Rider.Faiz, faizImageView, faizMaskView)
            bindRider(Rider.Blade, bladeImageView, bladeMaskView)
            bindRider(Rider.Hibiki, hibikiImageView, hibikiMaskView)
            bindRider(Rider.Kabuto, kabutoImageView, kabutoMaskView)
            bindRider(Rider.Deno, denoImageView, denoMaskView)
            bindRider(Rider.Kiva, kivaImageView, kivaMaskView)
            bindRider(Rider.Decade, dcdImageView, dcdMaskView)

            funcImageView.setOnClickListener {
                onFClick()
            }
            cancelImageView.setOnClickListener {
                onCClick()
            }

            bindRiderTouch(
                arcLayout,
                Rider.Kuuga,
                Rider.Agito,
                Rider.Ryuki,
                Rider.Faiz,
                Rider.Blade,
                Rider.Hibiki,
                Rider.Kabuto,
                Rider.Deno,
                Rider.Kiva,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        SoundManager.play(SoundKey.DeviceBoot)
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