package com.lollipop.ktouch.heisei

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.lollipop.ktouch.databinding.FragmentCardHeisei10Binding
import com.lollipop.resource.sound.Rider
import com.lollipop.resource.sound.SoundKey
import com.lollipop.resource.sound.SoundManager

class DcdHeisei10SubPage : HeiseiSubPage() {

    private var binding: FragmentCardHeisei10Binding? = null

    private val fRiderList = ArrayList<Rider>()

    private var currentState = State.INIT

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SoundManager.preload(
            context,
            arrayOf(
                SoundKey.DeviceSpace,
                SoundKey.DeviceBoot,
                Rider.Kuuga.nameSound,
                Rider.Kiva.nameSound,
                Rider.Kabuto.nameSound,
                Rider.Hibiki.nameSound,
                Rider.Ryuki.nameSound,
                Rider.Faiz.nameSound,
                Rider.Deno.nameSound,
                Rider.Decade.nameSound,
                Rider.Agito.nameSound,
                Rider.Blade.nameSound,
                // TODO 这个声音不该在这里
                SoundKey.DeviceBoot21,
            )
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
                if (iconManager.putPlayerList(rider)) {
                    iconManager.select(rider, true)
                    SoundManager.play(rider.nameSound)
                    iconManager.playAnimation(rider)
                }
            }

            State.READY -> {
                if (!fRiderList.contains(rider)) {
                    fRiderList.add(rider)
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
                if (iconManager.playerIconCount == iconManager.riderIconCount - 1) {
                    currentState = State.READY
                    val rider = Rider.Decade
                    iconManager.putPlayerList(rider)
                    iconManager.select(rider, true)
                    iconManager.playAnimation(rider) {
                        // TODO 临时用一个声音播放
                        val sound = SoundKey.DeviceBoot21
                        SoundManager.play(sound)
                        neonManager?.cancel()
                        neonManager = iconManager.neon()
                        neonManager?.play(sound.time)?.start(true)
                    }
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
                if (fRiderList.isEmpty()) {
                    // 这种情况下，就不操作吧
                    SoundManager.play(SoundKey.DeviceSpace)
                } else {
                    for (rider in fRiderList) {
                        iconManager.select(rider, false)
                        // TODO 播放大招的声音，这里用临时的顶一下
                        SoundManager.play(rider.nameSound)
                    }
                    fRiderList.clear()
                }
            }
        }
    }

    private fun onCClick() {
        SoundManager.play(SoundKey.DeviceSpace)
        if (fRiderList.isNotEmpty()) {
            fRiderList.clear()
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