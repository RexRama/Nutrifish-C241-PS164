package com.org.capstone.nutrifish.ui.main.scan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.FragmentScanBinding

class ScanFragment : Fragment() {
    private lateinit var binding: FragmentScanBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}