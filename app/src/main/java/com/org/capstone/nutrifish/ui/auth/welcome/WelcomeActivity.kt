package com.org.capstone.nutrifish.ui.auth.welcome

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.model.LoginModel
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.databinding.ActivityWelcomeBinding
import com.org.capstone.nutrifish.ui.main.MainActivity
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    private lateinit var welcomeViewModel: WelcomeViewModel
    private lateinit var userModel: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dataStore = SettingPreferences.getInstance(dataStore)
        setViewModel(dataStore)

        setButton()
    }

    private fun setViewModel(dataStore: SettingPreferences) {
        val viewModelFactory = ViewModelFactory(this, dataStore)
        welcomeViewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]

        welcomeViewModel.getUser().observe(this) { user ->
            this.userModel = user
        }

        welcomeViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        welcomeViewModel.moveActivity.observe(this) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showLoading(it: Boolean) {
        binding.pbWelcome.visibility = if (it) View.VISIBLE else View.GONE
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
                val user = LoginModel(loginEmail.text.toString(), loginPassword.text.toString())
                welcomeViewModel.loginUser(user)
            }
        }

    }

}