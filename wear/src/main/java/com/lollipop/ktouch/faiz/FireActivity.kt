package com.lollipop.ktouch.faiz

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.ktouch.databinding.ActivityFireBinding

class FireActivity : AppCompatActivity() {

    companion object {
        const val PARAMS_FIRE_MODE = "fire_mode"
        const val PARAMS_RIDER = "rider"

        fun start(context: Context, fireMode: FireMode, rider: FireRider) {
            context.startActivity(Intent(context, FireActivity::class.java).apply {
                putExtra(PARAMS_FIRE_MODE, fireMode.name)
                putExtra(PARAMS_RIDER, rider.name)
            })
        }

        fun fillBullet() {
            Magazine.fillBullet()
        }

    }

    private val binding by lazy {
        ActivityFireBinding.inflate(layoutInflater)
    }

    private val magazine by lazy {
        Magazine(
            arrayOf(
                binding.bullet01Icon,
                binding.bullet02Icon,
                binding.bullet03Icon,
                binding.bullet04Icon,
                binding.bullet05Icon,
                binding.bullet06Icon,
                binding.bullet07Icon,
                binding.bullet08Icon,
                binding.bullet09Icon,
                binding.bullet10Icon,
                binding.bullet11Icon,
                binding.bullet12Icon
            )
        )
    }

    private var fireMode = FireMode.Single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.currentModeIcon.setOnClickListener {
            fire()
        }
        binding.fillButton.setOnClickListener {
            reloadBullet()
        }
        binding.modeButton.setOnClickListener {
            fireMode = when (fireMode) {
                FireMode.Single -> {
                    FireMode.Burst
                }

                FireMode.Burst -> {
                    FireMode.Single
                }
            }
            updateMode()
        }
        initData()
        updateMode()
    }

    private fun fire() {
        if (magazine.fire(fireMode.bulletCount)) {
            // 发射成功
        } else {
            // 发射失败
        }
    }

    private fun reloadBullet() {
        magazine.reload(true)
    }

    private fun initData() {
        val intentMode = intent.getStringExtra(PARAMS_FIRE_MODE) ?: ""
        fireMode = FireMode.entries.find { it.name == intentMode } ?: FireMode.Single

        val rider = FireRider.entries.find {
            it.name == intent.getStringExtra(PARAMS_RIDER)
        } ?: FireRider.Faiz
        binding.currentModeIcon.setBackgroundResource(
            rider.background
        )
        magazine.init()
    }

    private fun updateMode() {
        binding.currentModeIcon.setImageResource(fireMode.icon)
        binding.modeButton.setImageResource(
            when (fireMode) {
                FireMode.Single -> {
                    FireMode.Burst.icon
                }

                FireMode.Burst -> {
                    FireMode.Single.icon
                }
            }
        )
    }

    enum class FireMode(
        val bulletCount: Int,
        val icon: Int
    ) {
        Single(1, com.lollipop.resource.R.drawable.ic_faiz_103),
        Burst(3, com.lollipop.resource.R.drawable.ic_faiz_106),
    }

    private class Magazine(
        val bulletArray: Array<View>
    ) {

        companion object {
            private var GLOBAL_QUANTITATIVE = -1
            fun fillBullet() {
                GLOBAL_QUANTITATIVE = 12
            }
        }

        val magazine: Int = bulletArray.size
        var quantitative: Int = magazine
        private var isFillBullet = false

        private val bulletFillTask = Runnable {
            bulletFill()
        }

        fun init() {
            quantitative = if (GLOBAL_QUANTITATIVE >= 0) {
                GLOBAL_QUANTITATIVE
            } else {
                magazine
            }
            updateUI()
        }

        private fun animationCancel() {
            isFillBullet = false
        }

        private fun animationReload() {
            animationCancel()
            isFillBullet = true
            postNextBullet()
        }

        private fun postNextBullet() {
            if (!isFillBullet) {
                return
            }
            bulletArray[0].postDelayed(bulletFillTask, 70)
        }

        private fun bulletFill() {
            if (quantitative >= magazine) {
                quantitative = magazine
                updateUI()
                return
            }
            if (quantitative < 0) {
                quantitative = 0
            }
            // 每次更新加一颗子弹
            quantitative += 1
            postNextBullet()
            updateUI()
        }

        fun reload(animation: Boolean) {
            if (animation) {
                animationReload()
            } else {
                animationCancel()
                quantitative = magazine
                updateUI()
            }
        }

        private fun updateUI() {
            val activeIndex = magazine - quantitative
            bulletArray.forEachIndexed { index, view ->
                view.alpha = if (index < activeIndex) {
                    0.3F
                } else {
                    1F
                }
            }
        }

        fun fire(count: Int): Boolean {
            if (quantitative < count) {
                return false
            }
            quantitative -= count
            GLOBAL_QUANTITATIVE = quantitative
            updateUI()
            return true
        }

    }

}