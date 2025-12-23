package com.mobiluygulamagelistirme.myapplication.common.component

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// Bu fonksiyon artık proje genelinde erişilebilir (public) olacak
fun formatDate(date: Date, formatPattern: String): String {
    val formatter = SimpleDateFormat(formatPattern, Locale("tr", "TR"))
    return formatter.format(date)
}