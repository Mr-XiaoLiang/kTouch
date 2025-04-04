package com.lollipop.ktouch.faiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.ktouch.databinding.ActivityFaizMenuBinding

class FaizMenuActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFaizMenuBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.recyclerView.isEdgeItemsCenteringEnabled = true
    }

}