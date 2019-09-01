package com.windea.breezeframework.time.domain

/**日历的字段对象。如：年、月、日。*/
data class CalendarField(
	/**字段。*/
	val field: Int,
	/**数量。*/
	val amount: Int
)
