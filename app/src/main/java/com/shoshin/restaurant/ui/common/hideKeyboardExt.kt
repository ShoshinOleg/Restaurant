package com.shoshin.restaurant.ui.common

import android.os.Build
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.hideKeyboard() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        currentFocus?.let { view ->
            val imm: InputMethodManager?
            imm = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}