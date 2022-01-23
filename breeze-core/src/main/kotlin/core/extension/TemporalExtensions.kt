// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TemporalExtensions")

package icu.windea.breezeframework.core.extension

import java.time.*

//region operator extensions
/**
 * @see java.time.Year.plusYears
 */
operator fun Year.plus(years: Int): Year = this.plusYears(years.toLong())

/**
 * @see java.time.Year.plusYears
 */
operator fun Year.plus(years: Long): Year = this.plusYears(years)

/**
 * @see java.time.Year.atMonth
 */
operator fun Year.plus(month: Month): YearMonth = this.atMonth(month)

/**
 * @see java.time.Year.atMonthDay
 */
operator fun Year.plus(monthDay: MonthDay): LocalDate = atMonthDay(monthDay)

/**
 * @see java.time.Year.minusYears
 */
operator fun Year.minus(years: Int): Year = this.minusYears(years.toLong())

/**
 * @see java.time.Year.minusYears
 */
operator fun Year.minus(years: Long): Year = this.minusYears(years)

/**
 * @see java.time.Year.plusYears
 */
operator fun Year.inc(): Year = this.plusYears(1L)

/**
 * @see java.time.Year.minusYears
 */
operator fun Year.dec(): Year = this.minusYears(1L)


/**
 * @see java.time.Month.plus
 */
operator fun Month.plus(months: Int): Month = this.plus(months.toLong())

/**
 * @see java.time.Month.minus
 */
operator fun Month.minus(months: Int): Month = this.minus(months.toLong())

/**
 * @see java.time.Month.plus
 */
operator fun Month.inc(): Month = this.plus(1L)

/**
 * @see java.time.Month.minus
 */
operator fun Month.dec(): Month = this.minus(1L)


/**
 * @see java.time.DayOfWeek.plus
 */
operator fun DayOfWeek.plus(days: Int): DayOfWeek = this.plus(days.toLong())

/**
 * @see java.time.DayOfWeek.minus
 * */
operator fun DayOfWeek.minus(days: Int): DayOfWeek = this.minus(days.toLong())

/**
 * @see java.time.DayOfWeek.plus
 */
operator fun DayOfWeek.inc(): DayOfWeek = this.plus(1L)

/**
 * @see java.time.DayOfWeek.minus
 */
operator fun DayOfWeek.dec(): DayOfWeek = this.minus(1L)
//endregion
