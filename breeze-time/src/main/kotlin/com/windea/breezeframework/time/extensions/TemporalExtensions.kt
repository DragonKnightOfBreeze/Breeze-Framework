package com.windea.breezeframework.time.extensions

import java.time.*

/**判断是否是今天。*/
val LocalDate.isToday: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是明天。*/
val LocalDate.isTomorrow: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是昨天。*/
val LocalDate.isYesterday: Boolean
	get() = LocalDate.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是未来。*/
val LocalDate.isInFuture: Boolean get() = this > LocalDate.now()

/**判断是否是过去。*/
val LocalDate.isInPast: Boolean get() = this < LocalDate.now()


/**判断是否是今天。*/
val LocalDateTime.isToday: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear }

/**判断是否是明天。*/
val LocalDateTime.isTomorrow: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear + 1 }

/**判断是否是昨天。*/
val LocalDateTime.isYesterday: Boolean
	get() = LocalDateTime.now().let { this.year == it.year && this.dayOfYear == it.dayOfYear - 1 }

/**判断是否是未来。*/
val LocalDateTime.isInFuture: Boolean get() = this > LocalDateTime.now()

/**判断是否是过去。*/
val LocalDateTime.isInPast: Boolean get() = this < LocalDateTime.now()
