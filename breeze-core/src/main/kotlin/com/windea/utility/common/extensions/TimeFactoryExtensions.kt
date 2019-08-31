package com.windea.utility.common.extensions

import java.time.*

/**创建指定纳秒的时长。*/
inline val Int.nanoseconds: Duration get() = Duration.ofNanos(this.toLong())

/**创建指定毫秒的时长。*/
inline val Int.milliseconds: Duration get() = Duration.ofMillis(this.toLong())

/**创建指定秒的时长。*/
inline val Int.seconds: Duration get() = Duration.ofSeconds(this.toLong())

/**创建指定分钟的时长。*/
inline val Int.minutes: Duration get() = Duration.ofMinutes(this.toLong())

/**创建指定小时的时长。*/
inline val Int.hours: Duration get() = Duration.ofHours(this.toLong())


/**创建指定周数的时期。*/
inline val Int.weeks: Period get() = Period.ofWeeks(this)

/**创建指定月数的时期。*/
inline val Int.months: Period get() = Period.ofMonths(this)

/**创建指定年数的时期。*/
inline val Int.years: Period get() = Period.ofYears(this)

/**创建指定天数的时期。*/
inline val Int.days: Period get() = Period.ofDays(this)


/**创建指定纳秒的时长。*/
inline val Long.nanoseconds: Duration get() = Duration.ofNanos(this)

/**创建指定毫秒的时长。*/
inline val Long.milliseconds: Duration get() = Duration.ofMillis(this)

/**创建指定秒的时长。*/
inline val Long.seconds: Duration get() = Duration.ofSeconds(this)

/**创建指定分钟的时长。*/
inline val Long.minutes: Duration get() = Duration.ofMinutes(this)

/**创建指定小时的时长。*/
inline val Long.hours: Duration get() = Duration.ofHours(this)
