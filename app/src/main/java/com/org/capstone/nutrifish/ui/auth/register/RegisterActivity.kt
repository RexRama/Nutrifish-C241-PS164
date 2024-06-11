package com.org.capstone.nutrifish.ui.auth.register

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.org.capstone.nutrifish.data.remote.model.RegisterModel
import com.org.capstone.nutrifish.databinding.ActivityRegisterBinding
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupButtonAction()
    }

    private fun setupViewModel() {
        val dataStore = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this, dataStore) // Pass any dependencies required by the ViewModel
        registerViewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        registerViewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }

        registerViewModel.moveActivity.observe(this){
            Utils().toWelcome(this)
            finish()
        }

    }

    private fun setupButtonAction() {
        binding.registerButton.setOnClickListener {
            val registerName = binding.editRegisterName
            val registerUsername = binding.editRegisterUsername
            val registerEmail = binding.editRegisterEmail
            val registerPassword = binding.editRegisterPassword

            if (validateInput(registerName, registerUsername, registerEmail, registerPassword)) {
                val registerModel = RegisterModel(
                    registerName.text.toString(),
                    registerUsername.text.toString(),
                    registerEmail.text.toString(),
                    registerPassword.text.toString()
                )
                registerViewModel.userRegistration(registerModel)
            }
        }
    }

    private fun validateInput(
        registerName: EditText,
        registerUsername: EditText,
        registerEmail: EditText,
        registerPassword: EditText
    ): Boolean {
        if (registerName.text.isEmpty()) {
            registerName.error = "Name field cannot be empty!"
            return false
        }

        if (registerUsername.text.isEmpty()) {
            registerUsername.error = "Username field cannot be empty!"
            return false
        }

        if (registerEmail.text.isEmpty()) {
            registerEmail.error = "Email field cannot be empty!"
            return false
        }

        if (registerPassword.text.isEmpty()) {
            registerPassword.error = "Password field cannot be empty!"
            return false
        }

        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbRegister.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
