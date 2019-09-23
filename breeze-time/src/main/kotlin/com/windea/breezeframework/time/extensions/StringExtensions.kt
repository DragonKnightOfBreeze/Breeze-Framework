@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.time.extensions

import java.text.*
import java.time.*
import java.time.format.*
import java.util.*

//REGION String to Time extensions

/**将当前字符串转化为日期。*/
inline fun String.toDate(format: String): Date = SimpleDateFormat(format).parse(this)

/**将当前字符串转化为本地日期。*/
inline fun CharSequence.toLocalDate(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE): LocalDate =
	LocalDate.parse(this, formatter)

/**将当前字符串转化为本地日期时间。*/
inline fun CharSequence.toLocalDateTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)

/**将当前字符串转化为本地时间。*/
inline fun CharSequence.toLocalTime(formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME): LocalDateTime =
	LocalDateTime.parse(this, formatter)
