// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("TemporalExtensions")

package com.windea.breezeframework.time.extension

import java.time.*
import java.time.temporal.*

//region Build Extensions
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

//region Common Extensions
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

