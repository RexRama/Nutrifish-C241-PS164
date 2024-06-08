package com.org.capstone.nutrifish.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.databinding.ActivityMainBinding
import com.org.capstone.nutrifish.utils.Utils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private var doubleBackToExit = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation: BottomNavigationView = binding.bottomNavbar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigation.setupWithNavController(navController)

        setupNavigation(bottomNavigation)
        setBackbutton()
    }

    private fun setBackbutton() {
        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExit) {
                    finishAffinity()
                } else {
                    doubleBackToExit = true
                    Toast.makeText(this@MainActivity, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExit = false }, 2000)
                }
            }
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun setupNavigation(navigation: BottomNavigationView) {
        val buttonScan = binding.btScan
        val buttonPost = binding.fabPostRecipe

        buttonScan.setOnClickListener {
            navController.navigate(R.id.navigation_scan)
            buttonPost.visibility = View.GONE
        }
        buttonPost.setOnClickListener {
            Utils().toUpload(this)
        }

        navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.to_home -> {
                    // Navigate to the home destination
                    navController.navigate(R.id.navigation_home)
                    buttonPost.visibility = View.VISIBLE
                    true
                }

                R.id.to_profile -> {
                    // Navigate to the profile destination
                    navController.navigate(R.id.navigation_profile)
                    buttonPost.visibility = View.VISIBLE
                    true
                }

                else -> false
            }
        }
    }
}
