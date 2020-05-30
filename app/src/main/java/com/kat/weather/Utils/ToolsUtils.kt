package com.kat.weather.Utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility object class
 */
object ToolsUtils {

    /**
     * Method to convert date to specific format
     *
     * @param date
     * @return String of specific format
     *
     */
    fun getDateTime(date: String): String? {
        try {
            val sdf = SimpleDateFormat("HH:mm:ss")
            val netDate = Date(date.toLong())
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

}