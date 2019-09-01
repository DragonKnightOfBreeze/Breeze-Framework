package com.windea.breezeframework.core.extensions

import java.time.*

/**@see java.time.Duration.negated*/
operator fun Duration.unaryMinus(): Duration = this.negated()

/**@see java.time.Duration.multipliedBy*/
operator fun Duration.times(n: Long): Duration = this.multipliedBy(n)

/**@see java.time.Duration.dividedBy*/
operator fun Duration.div(n: Long): Duration = this.dividedBy(n)

/**@see java.time.Duration.getSeconds*/
operator fun Duration.component1(): Long = this.seconds

/**@see java.time.Duration.getNano*/
operator fun Duration.component2(): Int = this.nano


/**@see java.time.Period.negated*/
operator fun Period.unaryMinus(): Period = this.negated()

/**@see java.time.Period.multipliedBy*/
operator fun Period.times(n: Int): Period = this.multipliedBy(n)

/**@see java.time.Period.getYears*/
operator fun Period.component1(): Int = this.years

/**@see java.time.Period.getMonths*/
operator fun Period.component2(): Int = this.months

/**@see java.time.Period.getDays*/
operator fun Period.component3(): Int = this.days


/**@see java.time.Year.plusYears*/
operator fun Year.plus(years: Int): Year = this.plusYears(years.toLong())

/**@see java.time.Year.minusYears*/
operator fun Year.minus(years: Int): Year = this.minusYears(years.toLong())

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
