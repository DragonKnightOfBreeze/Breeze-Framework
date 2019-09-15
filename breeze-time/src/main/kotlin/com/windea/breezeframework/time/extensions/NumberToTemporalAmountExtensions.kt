package com.windea.breezeframework.time.extensions

import java.time.*

/**创建指定周数的时期。*/
inline val Int.weeks: Period get() = Period.ofWeeks(this)

/**创建指定月数的时期。*/
inline val Int.months: Period get() = Period.ofMonths(this)

/**创建指定年数的时期。*/
inline val Int.years: Period get() = Period.ofYears(this)

/**创建指定天数的时期。*/
inline val Int.days: Period get() = Period.ofDays(this)
