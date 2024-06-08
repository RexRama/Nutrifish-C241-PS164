package com.org.capstone.nutrifish.ui.main.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivitySplashBinding
import com.org.capstone.nutrifish.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setWelcome()
    }

    private fun setWelcome() {
        val toWelcome = Intent(this, MainActivity::class.java)
        toWelcome.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(toWelcome)
            finish()
        }, DELAY_MILLIS)
    }

    companion object {
        private const val DELAY_MILLIS: Long = 3000
    }
}