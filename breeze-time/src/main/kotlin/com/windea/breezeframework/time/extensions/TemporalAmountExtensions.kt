@file:JvmName("TemporalAmountExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.time.extensions

import java.time.*
import java.time.temporal.*

//region operator override extensions
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
//endregion

//region build extensions
/**得到距今当前时长之前的本地日期时间。*/
inline val Duration.ago: LocalDateTime get() = LocalDateTime.now() - this

/**得到距今当前时长之后的本地日期时间。*/
inline val Duration.later: LocalDateTime get() = LocalDateTime.now() + this

/**得到距今当前时长之前的本地日期。*/
inline val Period.ago: LocalDate get() = LocalDate.now() - this

/**得到距今当前时长之后的本地日期。*/
inline val Period.later: LocalDate get() = LocalDate.now() + this


/**得到距指定本地日期时间当前时长之前的本地日期时间。*/
infix fun TemporalAmount.before(other: LocalDateTime): LocalDateTime = other - this

/**得到距指定本地日期时间当前时长之后的本地日期时间。*/
infix fun TemporalAmount.after(other: LocalDateTime): LocalDateTime = other + this

/**得到距指定本地日期当前时长之前的本地日期。*/
infix fun TemporalAmount.before(other: LocalDate): LocalDate = other - this

/**得到距指定本地日期当前时长之后的本地日期。*/
infix fun TemporalAmount.after(other: LocalDate): LocalDate = other + this

/**得到距指定瞬间当前时长之前的本地日期。*/
infix fun TemporalAmount.before(other: Instant): Instant = other - this

/**得到距指定瞬间当前时长之后的本地日期。*/
infix fun TemporalAmount.after(other: Instant): Instant = other + this

/**得到距指定时区日期时间当前时长之前的本地日期。*/
infix fun TemporalAmount.before(other: ZonedDateTime): ZonedDateTime = other - this

/**得到距指定时区日期时间当前时长之后的本地日期。*/
infix fun TemporalAmount.after(other: ZonedDateTime): ZonedDateTime = other - this
//endregion
