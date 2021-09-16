package com.example.supplement_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.supplement_tracker.databinding.ActivityListAddBinding

class list_add : AppCompatActivity() {

    val binding by lazy {ActivityListAddBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}