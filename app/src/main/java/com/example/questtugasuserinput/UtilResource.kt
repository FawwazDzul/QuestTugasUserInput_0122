package com.example.questtugasuserinput

import androidx.annotation.DimenRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.TextUnit
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun spResource(@DimenRes id: Int): TextUnit =
    with(LocalDensity.current) { dimensionResource(id).toSp() }

// DatePicker mengembalikan milidetik dalam UTC, jadi diformat dengan UTC
// agar tanggal tidak bergeser sehari.
fun formatTanggal(milidetik: Long, pola: String): String {
    val formatter = SimpleDateFormat(pola, Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(milidetik)
}

fun emailValid(email: String): Boolean =
    android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()