package com.org.capstone.nutrifish.ui.auth.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivityWelcomeBinding
import com.org.capstone.nutrifish.utils.Utils

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setButton()
    }

    private fun setButton() {
        val toRegister = binding.welcomeButtonRegister
        val toLogin = binding.welcomeButtonLogin

        toRegister.setOnClickListener {
            Utils().toRegister(this)
        }

        toLogin.setOnClickListener {
            Utils().toLogin(this)
        }
    }
}