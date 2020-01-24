@file:JvmName("TemporalExtensions")

package com.windea.breezeframework.time.extensions

import java.time.*
import java.time.temporal.*

//region operator override extensions
/**@see java.time.Year.plusYears*/
operator fun Year.plus(years: Int): Year = this.plusYears(years.toLong())

/**@see java.time.Year.plusYears*/
operator fun Year.plus(years: Long): Year = this.plusYears(years)

/**@see java.time.Year.atMonth*/
operator fun Year.plus(month: Month): YearMonth = this.atMonth(month)

/**@see java.time.Year.atMonthDay*/
operator fun Year.plus(monthDay: MonthDay): LocalDate = atMonthDay(monthDay)

/**@see java.time.Year.minusYears*/
operator fun Year.minus(years: Int): Year = this.minusYears(years.toLong())

/**@see java.time.Year.minusYears*/
operator fun Year.minus(years: Long): Year = this.minusYears(years)

/**@see java.time.Year.plusYears*/
operator fun Year.inc(): Year = this.plusYears(1L)

/**@see java.time.Year.minusYears*/
operator fun Year.dec(): Year = this.minusYears(1L)


/**@see java.time.Month.plus*/
operator fun Month.plus(months: Int): Month = this.plus(months.toLong())

/**@see java.time.Month.minus*/
operator fun Month.minus(months: Int): Month = this.minus(months.toLong())

/**@see java.time.Month.plus*/
operator fun Month.inc(): Month = this.plus(1L)

/**@see java.time.Month.minus*/
operator fun Month.dec(): Month = this.minus(1L)


/**@see java.time.DayOfWeek.plus*/
operator fun DayOfWeek.plus(days: Int): DayOfWeek = this.plus(days.toLong())

/**@see java.time.DayOfWeek.minus*/
operator fun DayOfWeek.minus(days: Int): DayOfWeek = this.minus(days.toLong())

/**@see java.time.DayOfWeek.plus*/
operator fun DayOfWeek.inc(): DayOfWeek = this.plus(1L)

/**@see java.time.DayOfWeek.minus*/
operator fun DayOfWeek.dec(): DayOfWeek = this.minus(1L)
//endregion

//region build extensions
/**得到当前月开始时的时间。*/
inline val <T : Temporal> T.atStartOfMonth: Temporal get() = with(TemporalAdjusters.firstDayOfMonth())

/**得到当前月结束时的时间。*/
inline val <T : Temporal> T.atEndOfMonth: Temporal get() = with(TemporalAdjusters.lastDayOfMonth())

/**得到下个月开始时的时间。*/
inline val <T : Temporal> T.atStartOfNextMonth: Temporal get() = with(TemporalAdjusters.firstDayOfNextMonth())

/**得到当前年开始时的时间。*/
inline val <T : Temporal> T.atStartOfYear: Temporal get() = with(TemporalAdjusters.firstDayOfYear())

/**得到当前年结束时的时间。*/
inline val <T : Temporal> T.atEndOfYear: Temporal get() = with(TemporalAdjusters.lastDayOfYear())

/**得到下个年开始时的时间。*/
inline val <T : Temporal> T.atStartOfNextYear: Temporal get() = with(TemporalAdjusters.firstDayOfNextYear())
//endregion

//region common extensions
/**判断是否是今天。*/
val LocalDate.isToday: Boolean get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是昨天。*/
val LocalDate.isYesterday: Boolean get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是明天。*/
val LocalDate.isTomorrow: Boolean get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是过去。*/
val LocalDate.isInPast: Boolean get() = this < LocalDate.now()

/**判断是否是未来。*/
val LocalDate.isInFuture: Boolean get() = this > LocalDate.now()


/**判断是否是今天。*/
val LocalDateTime.isToday: Boolean get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是昨天。*/
val LocalDateTime.isYesterday: Boolean get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是明天。*/
val LocalDateTime.isTomorrow: Boolean get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是过去。*/
val LocalDateTime.isInPast: Boolean get() = this < LocalDateTime.now()

/**判断是否是未来。*/
val LocalDateTime.isInFuture: Boolean get() = this > LocalDateTime.now()


/**判断是否支持指定的时间单元。*/
infix fun Temporal.supports(temporalUnit: TemporalUnit) = isSupported(temporalUnit)
//endregion
