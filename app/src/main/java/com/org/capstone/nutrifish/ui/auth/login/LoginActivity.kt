package com.org.capstone.nutrifish.ui.auth.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivityLoginBinding
import com.org.capstone.nutrifish.utils.Utils

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setButtonAction()
    }

    private fun setButtonAction() {
        val toRegister = binding.btLoginToRegister

        toRegister.setOnClickListener {
            Utils().toRegister(this)
            finish()
        }
    }
}