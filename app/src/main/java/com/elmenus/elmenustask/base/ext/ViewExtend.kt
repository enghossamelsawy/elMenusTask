package com.elmenus.elmenustask.base.ext

import android.view.View
import android.widget.TextView
import com.google.android.material.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnakeBar(message: String) {
    val snakeBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    val textView = snakeBar.view.findViewById(R.id.snackbar_text) as TextView
    textView.maxLines = 3
    snakeBar.show()
}