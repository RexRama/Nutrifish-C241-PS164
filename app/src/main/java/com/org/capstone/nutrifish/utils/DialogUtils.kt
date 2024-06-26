package com.org.capstone.nutrifish.utils

import android.app.AlertDialog
import android.content.Context

class DialogUtils(private val context: Context) {
    fun dialogSuccess(title: String, message: String, positiveButtonAction: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Proceed") { _, _ ->
                positiveButtonAction.invoke()
            }
            setCancelable(false)
            create()
            show()
        }
    }

    fun dialogError(title: String, message: String?) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Retry") { _, _ ->
                // Retry action if needed
            }
            setCancelable(false)
            create()
            show()
        }
    }

    fun dialogLogout(title: String, message: String, positiveButtonAction: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("Yes") {_,_ ->
                positiveButtonAction.invoke()
            }
            setNegativeButton("No") {_,_ ->
                //do nothing
            }
            create()
            show()
        }
    }
}