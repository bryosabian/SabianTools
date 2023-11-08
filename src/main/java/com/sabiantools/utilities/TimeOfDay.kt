package com.sabiantools.utilities

import org.joda.time.LocalDateTime
import java.util.Date

class TimeOfDay(private var date: Date = SabianUtilities.getCurrentDateTime().toDate()) {


    enum class TimePeriod {
        MORNING, NOON, AFTERNOON, EVENING, NIGHT
    }

    val timePeriod: TimePeriod
        get() {
            val dateTime = LocalDateTime.fromDateFields(date)
            val hour = dateTime.hourOfDay().get()
            if (hour in 1..11)
                return TimePeriod.MORNING
            if (hour in 12.until(13))
                return TimePeriod.NOON
            if (hour in 13..17)
                return TimePeriod.AFTERNOON
            if (hour in 18..21)
                return TimePeriod.EVENING
            return TimePeriod.NIGHT
        }
}