package com.org.capstone.nutrifish.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.org.capstone.nutrifish.R
import com.org.capstone.nutrifish.data.remote.model.UserModel
import com.org.capstone.nutrifish.databinding.ActivityMainBinding
import com.org.capstone.nutrifish.ui.auth.welcome.WelcomeActivity
import com.org.capstone.nutrifish.utils.DialogUtils
import com.org.capstone.nutrifish.utils.SettingPreferences
import com.org.capstone.nutrifish.utils.Utils
import com.org.capstone.nutrifish.utils.ViewModelFactory
import com.org.capstone.nutrifish.utils.dataStore

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userModel: UserModel


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

        setViewModel()
        setupNavigation(bottomNavigation)
        setButton()
        setBackbutton()


    }

    private fun setButton() {
        val logoutButton = binding.btLogout
        logoutButton.setOnClickListener{
            DialogUtils(this).dialogLogout("Logout","Anda yakin ingin Logout?"){
                mainViewModel.logout(this)
                Log.d("MainActivity", "User ID: ${userModel.username}")
                Log.d("MainActivity", "User Name: ${userModel.name}")
                Log.d("MainActivity", "User Email: ${userModel.email}")
                Log.d("MainActivity", "User Token: ${userModel.token}")
            }
        }
    }

    private fun setViewModel() {
        val dataStore = SettingPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelFactory(this, dataStore)
        mainViewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        mainViewModel.getUser()?.observe(this) {user->
            val token = "Bearer ${user.token}"
            mainViewModel.setToken(token)
            userModel = user
            Log.d("MainActivity", "User ID: ${userModel.username}")
            Log.d("MainActivity", "User Name: ${userModel.name}")
            Log.d("MainActivity", "User Email: ${userModel.email}")
            Log.d("MainActivity", "User Token: ${userModel.token}")
        }

        mainViewModel.moveActivity.observe(this) {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
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
                    val bundle = Bundle().apply {
                        putParcelable("user", this@MainActivity.userModel)
                    }
                    navController.navigate(R.id.navigation_profile, bundle)
                    buttonPost.visibility = View.VISIBLE
                    true
                }

                else -> false
            }
        }
    }
}
