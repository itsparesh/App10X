package com.example.app10x.utility

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertKtoC(kelvin: Double?, isCelsius: Boolean = false): String {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        return if (isCelsius)
            df.format((kelvin ?: 0.00).minus(273.15)) + " C"
        else
            df.format((kelvin ?: 0.00).minus(273.15))
    }

    fun convertDateFormat(dateString: String): Calendar? {
        return try {
            val calendar = Calendar.getInstance()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
            val date: Date? = simpleDateFormat.parse(dateString)
            calendar.timeInMillis = date?.time?: System.currentTimeMillis()
            calendar
        } catch (e: Exception) {
            e.printStackTrace()
            Calendar.getInstance()
        }
    }
}