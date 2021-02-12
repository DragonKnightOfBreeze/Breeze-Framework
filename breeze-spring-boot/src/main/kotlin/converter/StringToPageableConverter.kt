// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*
import org.springframework.data.domain.*

/**
 * 字符串到分页对象的转化器。
 *
 * * 页面从1开始。
 * * 示例："", "1", "1,10", "1,10,+name"。
 * * 默认值："1,10"。
 */
open class StringToPageableConverter(
	private val stringToSortConverter: StringToSortConverter,
) : Converter<String, Pageable> {
	override fun convert(string: String): Pageable {
		val splitStrings = string.split(",", limit = 3).map { it.trim() }
		val page = splitStrings.getOrElse(0){ "1"}.toInt()
		val size = splitStrings.getOrElse(1){"10"}.toInt()
		val sort = stringToSortConverter.convert(splitStrings.getOrElse(2){ ""})
		return PageRequest.of(page - 1, size, sort)
	}
}
