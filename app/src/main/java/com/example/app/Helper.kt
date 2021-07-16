package com.example.app

import android.content.Context
import android.widget.Toast

object Helper {
    fun showToast(context: Context, toast: String) = Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
}