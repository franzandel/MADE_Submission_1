package com.example.madesubmission1.utils

import android.content.Context
import android.view.View
import android.widget.Toast

/**
 * Created by Franz Andel on 2020-02-13.
 * Android Engineer
 */

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

// 0 = SHORT
// 1 = LONG
fun Context.showToast(content: String, duration: Int = 0) {
    Toast.makeText(this, content, duration).show()
}

fun Any?.isNull(): Boolean {
    return this == null
}

fun Any?.isNotNull(): Boolean {
    return this != null
}