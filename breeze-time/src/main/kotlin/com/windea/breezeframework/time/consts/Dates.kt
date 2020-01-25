package com.windea.breezeframework.time.consts

import com.windea.breezeframework.time.extensions.*
import java.util.*

object Dates {
	/**今天。*/
	@JvmStatic
	val today: Date
		get() = Date()
	/**明天。*/
	@JvmStatic
	val tomorrow: Date
		get() = getDate(1)
	/**昨天。*/
	@JvmStatic
	val yesterday: Date
		get() = getDate(-1)

	@JvmStatic
	private fun getDate(amount: Int): Date {
		calendar.time = Date()
		calendar.add(Calendar.DATE, amount)
		return calendar.time
	}
}
