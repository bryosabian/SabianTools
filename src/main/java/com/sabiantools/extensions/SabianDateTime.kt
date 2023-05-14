package com.sabiantools.extensions

import org.joda.time.*
import java.util.*
import java.util.concurrent.TimeUnit


fun Calendar.strippedFromSeconds(hour: Int? = null, minute: Int? = null): Calendar {
    set(Calendar.HOUR_OF_DAY, hour ?: get(Calendar.HOUR_OF_DAY))
    set(Calendar.MINUTE, minute ?: get(Calendar.MINUTE))
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

fun DateTime.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()
    return calendar
}

fun DateTime.strippedFromSeconds(): DateTime {
    var calendar = this.toCalendar()
    calendar = calendar.strippedFromSeconds(this.hourOfDay, this.minuteOfHour)
    return DateTime(calendar.time)
}


fun LocalDateTime.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this.toDate()
    return calendar
}

fun LocalDateTime.strippedFromSeconds(): DateTime {
    val calendar = toCalendar().strippedFromSeconds(this.hourOfDay, this.minuteOfHour)
    return DateTime(calendar.time)
}

fun Calendar.toDateTime(): DateTime {
    val tz = this.timeZone
    val zoneHere = DateTimeZone.forID(tz.id)
    return DateTime(this.time).withZone(zoneHere)
}


fun ReadableInstant.hoursBetween(to: ReadableInstant): Hours {
    return Hours.hoursBetween(this, to)
}


fun LocalDateTime.isNight(): Boolean {
    return this.hourOfDay in 18..23
}

fun DateTime.isNight(): Boolean {
    return this.hourOfDay in 18..23
}

fun LocalDateTime.isMorning(): Boolean {
    return this.hourOfDay in 0..11
}

fun DateTime.isMorning(): Boolean {
    return this.hourOfDay in 0..11
}

fun DateTime.isMidnightHour(): Boolean {
    return this.hourOfDay == 0
}

fun Long.toHoursMinutesSeconds(): String {
    var period = Period(this * 1000)
    period = period.normalizedStandard()
    return "%02d:%02d:%02d".format(period.hours, period.minutes, period.seconds)
}

