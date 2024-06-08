package com.org.capstone.nutrifish.ui.auth.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivityWelcomeBinding
import com.org.capstone.nutrifish.utils.DialogUtils
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
        val registerButton = binding.btLoginToRegister
        registerButton.setOnClickListener {
            Utils().toRegister(this)
        }

        val loginButton = binding.loginButtonLogin
        loginButton.setOnClickListener {
            val loginEmail = binding.editLoginEmail
            val loginPassword = binding.editLoginPassword

            when {
                loginEmail.text.toString().isEmpty() -> {
                    loginEmail.error = getString(R.string.empty_email_field)
                }

                loginPassword.text.toString().isEmpty() -> {
                    loginPassword.error = getString(R.string.empty_password_field)
                }

                else -> {
                    loginEmail.error = null
                    loginPassword.error = null
                }
            }

            if (loginEmail.error == null && loginPassword.error == null) {
                if (loginEmail.text.toString() == "admin@admin.com" && loginPassword.text.toString() == "admin123") {
                   DialogUtils(this).dialogSuccess("Login Successful", "Welcome Admin") {
                       Utils().toHome(this)
                       finish()
                   }
                }
            }
        }
    }

}