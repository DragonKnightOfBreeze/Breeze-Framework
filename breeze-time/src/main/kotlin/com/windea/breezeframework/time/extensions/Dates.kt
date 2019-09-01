package com.windea.breezeframework.time.extensions

import java.util.*

object Dates {
	/**今天。*/
	val today = Date()
	
	/**明天。*/
	val tomorrow = getDate(1)
	
	/**昨天。*/
	val yesterday = getDate(-1)
	
	
	private fun getDate(amount: Int): Date {
		calendar.time = Date()
		calendar.add(Calendar.DATE, amount)
		return calendar.time
	}
}
