package ru.resodostudios.flick.core.common

import android.content.Context
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    val rawDate = inputFormat.parse(date)

    return outputFormat.format(rawDate!!)
}

fun convertPixelsToDp(context: Context, pixels: Float): Dp {
    val screenPixelDensity = context.resources.displayMetrics.density
    return (pixels / screenPixelDensity).dp
}