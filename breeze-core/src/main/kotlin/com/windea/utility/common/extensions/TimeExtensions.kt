package com.windea.utility.common.extensions

import java.time.*

operator fun Duration.unaryMinus(): Duration = this.negated()

operator fun Duration.times(n: Long): Duration = this.multipliedBy(n)

operator fun Duration.div(n: Long): Duration = this.dividedBy(n)

/**@see java.time.Duration.getSeconds*/
operator fun Duration.component1(): Long = this.seconds

/**@see java.time.Duration.getNano*/
operator fun Duration.component2(): Int = this.nano


operator fun Period.unaryMinus(): Period = this.negated()

operator fun Period.times(n: Int): Period = this.multipliedBy(n)

/**@see java.time.Period.getYears*/
operator fun Period.component1(): Int = this.years

/**@see java.time.Period.getMonths*/
operator fun Period.component2(): Int = this.months

/**@see java.time.Period.getDays*/
operator fun Period.component3(): Int = this.days


operator fun Year.plus(years: Int): Year = this.plusYears(years.toLong())

operator fun Year.minus(years: Int): Year = this.minusYears(years.toLong())

operator fun Year.inc(): Year = this.plusYears(1L)

operator fun Year.dec(): Year = this.minusYears(1L)


operator fun Month.plus(months: Int): Month = this.plus(months.toLong())

operator fun Month.minus(months: Int): Month = this.minus(months.toLong())

operator fun Month.inc(): Month = this.plus(1L)

operator fun Month.dec(): Month = this.minus(1L)


operator fun DayOfWeek.plus(days: Int): DayOfWeek = this.plus(days.toLong())

operator fun DayOfWeek.minus(days: Int): DayOfWeek = this.minus(days.toLong())

operator fun DayOfWeek.inc(): DayOfWeek = this.plus(1L)

operator fun DayOfWeek.dec(): DayOfWeek = this.minus(1L)


/**判断是否是明天。*/
val LocalDate.isTomorrow: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是今天。*/
val LocalDate.isToday: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是昨天。*/
val LocalDate.isYesterday: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是未来。*/
val LocalDate.isInFuture: Boolean get() = this > LocalDate.now()

/**判断是否是过去。*/
val LocalDate.isInPast: Boolean get() = this < LocalDate.now()


/**判断是否是明天。*/
val LocalDateTime.isTomorrow: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是今天。*/
val LocalDateTime.isToday: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是昨天。*/
val LocalDateTime.isYesterday: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是未来。*/
val LocalDateTime.isInFuture: Boolean get() = this > LocalDateTime.now()

/**判断是否是过去。*/
val LocalDateTime.isInPast: Boolean get() = this < LocalDateTime.now()


/**得到距今当前时长之前的本地日期时间。*/
inline val Duration.ago: LocalDateTime get() = LocalDateTime.now() - this

/**得到距今当前时长之后的本地日期时间。*/
inline val Duration.after: LocalDateTime get() = LocalDateTime.now() + this

/**得到距今当前时长之前的本地日期。*/
inline val Period.ago: LocalDate get() = LocalDate.now() - this

/**得到距今当前时长之后的本地日期。*/
inline val Period.after: LocalDate get() = LocalDate.now() + this
