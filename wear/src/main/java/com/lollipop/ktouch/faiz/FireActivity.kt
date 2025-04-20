package com.lollipop.ktouch.faiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lollipop.ktouch.databinding.ActivityFireBinding

class FireActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFireBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

}