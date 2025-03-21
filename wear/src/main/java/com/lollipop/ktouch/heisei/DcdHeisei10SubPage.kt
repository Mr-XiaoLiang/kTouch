package com.lollipop.ktouch.heisei

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.base.neon.RippleNeon
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class DcdHeisei10SubPage : HeiseiSubPage() {

    private var binding: FragmentCardHeisei10Binding? = null

    private val selectedRiderList = ArrayList<Rider>()

    private var currentState = State.INIT

    override fun onAttach(context: Context) {
        super.onAttach(context)
        preloadRider(
            context,
            Rider.Kuuga,
            Rider.Kiva,
            Rider.Kabuto,
            Rider.Hibiki,
            Rider.Ryuki,
            Rider.Faiz,
            Rider.Deno,
            Rider.Decade,
            Rider.Agito,
            Rider.Blade,
        )
    }

    override fun createContentView(parent: ViewGroup): View {
        val newBinding = FragmentCardHeisei10Binding.inflate(layoutInflater, parent, false)
        this.binding = newBinding
        return newBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            bindRider(Rider.Kuuga, kuugaImageView, kuugaMaskView)
            bindRider(Rider.Kiva, kivaImageView, kivaMaskView)
            bindRider(Rider.Kabuto, kabutoImageView, kabutoMaskView)
            bindRider(Rider.Hibiki, hibikiImageView, hibikiMaskView)
            bindRider(Rider.Ryuki, ryukiImageView, ryukiMaskView)
            bindRider(Rider.Faiz, faizImageView, faizMaskView)
            bindRider(Rider.Deno, denoImageView, denoMaskView)
            bindRider(Rider.Decade, dcdImageView, dcdMaskView)
            bindRider(Rider.Agito, agitoImageView, agitoMaskView)
            bindRider(Rider.Blade, bladeImageView, bladeMaskView)
            funcImageView.setOnClickListener {
                onFClick()
            }
            cancelImageView.setOnClickListener {
                onCClick()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        SoundManager.play(SoundKey.DeviceBoot)
    }

    override fun onRiderClick(rider: Rider) {
        if (rider == Rider.Decade) {
            onDcdClick()
            return
        }
        when (currentState) {
            State.INIT -> {
                if (!selectedRiderList.contains(rider)) {
                    selectedRiderList.add(rider)
                    iconManager.select(rider, true)
                    SoundManager.play(rider.nameSound)
                    iconManager.playAnimation(rider)
                }
            }

            State.READY -> {
                if (!selectedRiderList.contains(rider)) {
                    selectedRiderList.add(rider)
                    iconManager.select(rider, true)
                    SoundManager.play(rider.nameSound)
                    iconManager.playAnimation(rider)
                } else {
                    SoundManager.play(SoundKey.DeviceSpace)
                }
            }
        }
    }

    private fun onDcdClick() {
        when (currentState) {
            State.INIT -> {
                if (selectedRiderList.size == iconManager.riderIconCount - 1) {
                    currentState = State.READY
                    val rider = Rider.Decade
                    selectedRiderList.add(rider)
                    iconManager.select(rider, true)
                    iconManager.playAnimation(rider) {
                        val sound = SoundKey.HeiseiDcdFinally
                        SoundManager.play(sound)
                        newNeon().play(sound.time)?.start(true)
                    }
                    selectedRiderList.clear()
                } else {
                    // 这种情况下，就不操作吧
                    SoundManager.play(SoundKey.DeviceSpace)
                }
            }

            State.READY -> {
                SoundManager.play(SoundKey.NameDecade)
            }
        }
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
                                    it.skillSound.time
                                )
                            )
                        )?.start(true)
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
        } else {
            // TODO 退出的声音需要确认一下
            SoundManager.play(SoundKey.DeviceExit21)
            iconManager.clearPlayerList()
        }
    }

    private enum class State {
        INIT,
        READY,
    }

}