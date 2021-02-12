// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TemporalAmountExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.time.extension

import java.time.*
import java.time.temporal.*

//region Build Extensions
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

