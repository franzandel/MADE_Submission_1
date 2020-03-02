package com.example.madesubmission1.external.helper

import java.util.*

/**
 * Created by Franz Andel on 02/03/20.
 * Android Engineer
 */

class CalendarHelper {

    fun getExpectedTimeInterval(expectedHour: Int, expectedMinute: Int): Long {
        val calendar = Calendar.getInstance()
        val currentHour = calendar[Calendar.HOUR_OF_DAY]
        val currentMinute = calendar[Calendar.MINUTE]

        if (currentHour > expectedHour ||
            currentHour == expectedHour && currentMinute >= expectedMinute
        ) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendar[Calendar.HOUR_OF_DAY] = expectedHour
        calendar[Calendar.MINUTE] = expectedMinute
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0

        return calendar.timeInMillis
    }
}