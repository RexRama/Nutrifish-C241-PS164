package com.org.capstone.nutrifish.ui.main.upload

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.org.capstone.nutrifish.databinding.ActivityUploadRecipeBinding

class UploadRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}