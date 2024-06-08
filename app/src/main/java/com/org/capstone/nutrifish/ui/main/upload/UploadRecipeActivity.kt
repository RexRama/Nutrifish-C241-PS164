package com.org.capstone.nutrifish.ui.main.upload

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivityUploadRecipeBinding
import com.org.capstone.nutrifish.utils.Utils

class UploadRecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadRecipeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}