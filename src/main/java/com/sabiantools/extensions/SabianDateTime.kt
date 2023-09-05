package com.sabiantools.extensions

import org.joda.time.*
import java.util.*


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


/**
 * Converts a value like this 00:00:00.00000 to LocalDateTime or null if failed
 */
fun String.hourMinuteSecondsToDateTime(useDate: LocalDate = LocalDate.now()): LocalDateTime? {
    try {
        //Remove hour offset
        val strictHourMinutes = this.split(".").firstOrNull() ?: return null
        val nowDateTimeString = "%sT%s".format(useDate, strictHourMinutes)
        return LocalDateTime.parse(nowDateTimeString)
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}


fun String.toLocalDateTime(): LocalDateTime {
    val formatted = this.replace("\\s+".toRegex(RegexOption.IGNORE_CASE), "T")
    return LocalDateTime.parse(formatted)
}

fun String.toDateTime(): DateTime {
    val formatted = this.replace("\\s+".toRegex(RegexOption.IGNORE_CASE), "T")
    return DateTime.parse(formatted)
}

fun String.toLocalDate(): LocalDate {
    val formatted = this.replace("\\s+".toRegex(RegexOption.IGNORE_CASE), "T")
    return LocalDate.parse(formatted)
}

fun LocalDateTime.isSameDate(with: LocalDateTime): Boolean {
    return this.toLocalDate().equals(with.toLocalDate());
}

fun DateTime.isSameDate(with: DateTime): Boolean {
    return this.toLocalDate().equals(with.toLocalDate());
}

fun Int.toHoursMinutesSeconds(): String {
    val totalSeconds = this
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d:%02d".format(hours, minutes, seconds)
}