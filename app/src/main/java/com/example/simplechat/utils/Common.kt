package com.example.simplechat.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("dd.MM HH:mm", Locale.getDefault())
    return format.format(date)
}
