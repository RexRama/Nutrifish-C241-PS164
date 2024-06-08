package com.org.capstone.nutrifish.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText
import com.org.capstone.nutrifish.R

class PasswordEditText : TextInputEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                validatePassword(s.toString())
            }
        })
    }

    private fun validatePassword(password: String) {
        val pattern = Regex("^.{8,}\$")
        val isValid = pattern.matches(password)
        if (!isValid) showError() else removeError()
    }

    private fun showError() {

        this.error = context.getString(R.string.password_validate)

    }

    private fun removeError() {
        this.error = null
    }
}