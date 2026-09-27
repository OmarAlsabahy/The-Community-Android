package com.tawajood.the_community_user.utils

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatMatchTime(time: String): String {
    return try {
        val localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss"))
        localTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        "formatMatchTime - $e".logE()
        time
    }
}

fun String.toReadableDate(locale: Locale): String {
    val date = LocalDate.parse(this)
    val today = LocalDate.now()

    val isArabic = locale.language == "ar"

    return when {
        date == today && isArabic -> "اليوم"
        date == today && !isArabic -> "Today"

        date == today.plusDays(1) && isArabic -> "غدًا"
        date == today.plusDays(1) && !isArabic -> "Tomorrow"

        else -> {
            val pattern = if (isArabic) "d MMMM yyyy" else "MMMM d, yyyy"
            val formatter = DateTimeFormatter.ofPattern(pattern, locale)
            date.format(formatter)
        }
    }
}
