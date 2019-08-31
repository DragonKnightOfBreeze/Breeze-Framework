package com.windea.utility.springboot.data.components.converters

import com.windea.utility.common.extensions.*
import org.springframework.core.convert.converter.*
import org.springframework.data.domain.*

/**
 * 字符串到分页对象的转换器。
 *
 * * 页面从1开始。
 * * 示例："", "1", "1,10", "1,10,+name"。
 * * 默认值："1,10"。
 */
open class StringToPageableConverter(
	private val stringToSortConverter: StringToSortConverter
) : Converter<String, Pageable> {
	override fun convert(string: String): Pageable {
		val splitStrings = string.split(",", limit = 3).map { it.trim() }
		val page = splitStrings.getOrDefault(0, "1").toInt()
		val size = splitStrings.getOrDefault(1, "10").toInt()
		val sort = stringToSortConverter.convert(splitStrings.getOrDefault(2, ""))
		return PageRequest.of(page - 1, size, sort)
	}
}
