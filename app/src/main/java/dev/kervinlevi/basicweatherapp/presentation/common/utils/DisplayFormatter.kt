package dev.kervinlevi.basicweatherapp.presentation.common.utils

import android.icu.util.Calendar
import java.text.SimpleDateFormat

/**
 * Created by kervinlevi on 21/11/24
 */

fun String?.formattedDescription(): String {
    if (isNullOrEmpty()) return ""
    return this[0].uppercaseChar() + this.drop(1)
}

fun Long.formattedDateTime(simpleDateFormat: SimpleDateFormat): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this * 1000
    return simpleDateFormat.format(calendar.timeInMillis)
}

fun String.formattedCelsius(): String {
    return this.replace(".00", "")
}

object DateTimeFormat {
    const val FullDate = "MMM dd, yyyy hh:mm aa"
    const val TimeOnly = "hh:mm aa"
}
