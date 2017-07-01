package org.sam.applications.kotlinsample.extensions

import java.text.DateFormat
import java.util.*

fun Long.toStringDate(expectedFormat: Int = DateFormat.MEDIUM): String {
    val dateFormat = DateFormat.getDateInstance(expectedFormat, Locale.getDefault())
    return dateFormat.format(this)
}