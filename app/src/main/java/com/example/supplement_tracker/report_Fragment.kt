package com.example.supplement_tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.supplement_tracker.databinding.FragmentReportBinding

class report_Fragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

}