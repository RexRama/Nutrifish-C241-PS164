package com.org.capstone.nutrifish.utils

import android.content.Context
import android.content.Intent
import com.org.capstone.nutrifish.ui.auth.register.RegisterActivity

class Utils {
    fun toRegister(context: Context) {
        val toRegister = Intent(context, RegisterActivity::class.java)
        context.startActivity(toRegister)
    }
}