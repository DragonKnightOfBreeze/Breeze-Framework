// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("NumberExtensions")

package com.windea.breezeframework.time.extension

import java.time.*

//region Number to Duration Extensions
/**创建指定小时的时长。*/
inline val Int.hours: Duration get() = Duration.ofHours(this.toLong())

/**创建指定分钟的时长。*/
inline val Int.minutes: Duration get() = Duration.ofMinutes(this.toLong())

/**创建指定秒的时长。*/
inline val Int.seconds: Duration get() = Duration.ofSeconds(this.toLong())

/**创建指定毫秒的时长。*/
inline val Int.milliseconds: Duration get() = Duration.ofMillis(this.toLong())

/**创建指定纳秒的时长。*/
inline val Int.nanoseconds: Duration get() = Duration.ofNanos(this.toLong())


/**创建指定小时的时长。*/
inline val Long.hours: Duration get() = Duration.ofHours(this)

/**创建指定分钟的时长。*/
inline val Long.minutes: Duration get() = Duration.ofMinutes(this)

/**创建指定秒的时长。*/
inline val Long.seconds: Duration get() = Duration.ofSeconds(this)

/**创建指定毫秒的时长。*/
inline val Long.milliseconds: Duration get() = Duration.ofMillis(this)

/**创建指定纳秒的时长。*/
inline val Long.nanoseconds: Duration get() = Duration.ofNanos(this)
//endregion

//region Number to Period Extensions
/**创建指定年数的时期。*/
inline val Int.years: Period get() = Period.ofYears(this)

/**创建指定月数的时期。*/
inline val Int.months: Period get() = Period.ofMonths(this)

/**创建指定天数的时期。*/
inline val Int.days: Period get() = Period.ofDays(this)

/**创建指定周数的时期。*/
inline val Int.weeks: Period get() = Period.ofWeeks(this)


/**创建指定年数的时期。*/
inline val Long.years: Period get() = Period.ofYears(this.toInt())

/**创建指定月数的时期。*/
inline val Long.months: Period get() = Period.ofMonths(this.toInt())

/**创建指定天数的时期。*/
inline val Long.days: Period get() = Period.ofDays(this.toInt())

/**创建指定周数的时期。*/
inline val Long.weeks: Period get() = Period.ofWeeks(this.toInt())
//endregion

//region Number to Year, Month, ... Extensions
/**得到指定的年。*/
inline val Int.year: Year get() = Year.of(this)

/**得到指定的月。*/
inline val Int.month: Month get() = Month.of(this)

/**得到指定的星期。*/
inline val Int.dayOfWeek: DayOfWeek get() = DayOfWeek.of(this)
//endregion
