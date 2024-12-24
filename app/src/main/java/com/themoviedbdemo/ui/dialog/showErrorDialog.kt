package com.themoviedbdemo.ui.dialog

import android.content.Context
import androidx.appcompat.app.AlertDialog

fun showErrorDialog(context: Context, title: String, message: String, onRetry: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("Retry") { dialog, _ ->
            dialog.dismiss()
            onRetry()
        }
        .setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
