// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("DateExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.model.*
import java.text.*
import java.util.*

//region Operator Extensions
/** @see java.util.Calendar.add*/
operator fun Date.plus(calendarField: CalendarField): Date {
	calendar.time = this
	calendar.add(calendarField.field, calendarField.amount)
	return calendar.time
}

/** @see java.util.Calendar.add*/
operator fun Date.minus(calendarField: CalendarField): Date {
	calendar.time = this
	calendar.add(calendarField.field, -calendarField.amount)
	return calendar.time
}
//endregion

//region Common Extensions
val Date.beginningOfYear: Date get() = this.modify(month = 1, day = 1, hour = 0, minute = 0, second = 0)

val Date.endOfYear: Date get() = this.modify(month = 12, day = 31, hour = 23, minute = 59, second = 59)

val Date.beginningOfMonth: Date get() = this.modify(day = 1, hour = 0, minute = 0, second = 0)

val Date.endOfMonth: Date get() = this.assign().modify(day = calendar.getActualMaximum(Calendar.DATE), hour = 23, minute = 59, second = 59)

val Date.beginningOfDay: Date get() = this.modify(hour = 0, minute = 0, second = 0)

val Date.endOfDay: Date get() = this.modify(hour = 23, minute = 59, second = 59)

val Date.beginningOfHour: Date get() = this.modify(minute = 0, second = 0)

val Date.endOfHour: Date get() = this.modify(minute = 59, second = 59)

val Date.beginningOfMinute: Date get() = this.modify(second = 0)

val Date.endOfMinute: Date get() = this.modify(second = 59)


val Date.isSunday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY }

val Date.isMonday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY }

val Date.isTuesday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY }

val Date.isWednesday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY }

val Date.isThursday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY }

val Date.isFriday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY }

val Date.isSaturday: Boolean get() = this.assign().let { calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY }

private fun Date.assign(): Date = this.also { calendar.time = this }


/**
 * 更改日期。
 */
fun Date.modify(
	year: Int = -1, month: Int = -1, day: Int = -1, hour: Int = -1, minute: Int = -1, second: Int = -1,
	weekday: Int = -1,
): Date {
	calendar.time = this
	calendar.set(Calendar.YEAR, year)
	if(month > 0) calendar.set(Calendar.MONTH, month - 1)
	if(day > 0) calendar.set(Calendar.DATE, day)
	if(hour > -1) calendar.set(Calendar.HOUR_OF_DAY, hour)
	if(minute > -1) calendar.set(Calendar.MINUTE, minute)
	if(second > -1) calendar.set(Calendar.SECOND, second)
	if(weekday > -1) calendar.set(Calendar.WEEK_OF_MONTH, weekday)
	return calendar.time
}
//endregion

//region Convert Extensions
/**
 * 将当前日期转化为字符串。
 */
fun Date.toString(format: String, locale: Locale = defaultLocale, timeZone: TimeZone = defaultTimeZone): String {
	val dateFormat = threadLocalDateFormatMapCache.getOrPut(format) {
		ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
	}.get()
	return dateFormat.format(this)
}

/**
 * 将当前日期转化为字符串。
 */
fun Date.toString(dateFormat: DateFormat): String {
	return dateFormat.format(this)
}
//endregion
