package org.sam.applications.kotlinsample.extensions

import android.content.Context
import android.support.v4.content.ContextCompat
import android.widget.TextView

var TextView.textColor: Int
    get() = currentTextColor
    set(color) {
        setTextColor(color)
    }

fun Context.color(color: Int): Int = ContextCompat.getColor(this, color)