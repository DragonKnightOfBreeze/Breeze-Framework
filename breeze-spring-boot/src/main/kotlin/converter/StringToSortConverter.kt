// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*
import org.springframework.data.domain.*

/**
 * 字符串到排序对象的转化器。
 *
 * * 默认对属性进行升序排序。
 * * 不检查属性名的正确性。
 * * 示例："", "+name", "+name,-age", "name,age"。
 * * 默认值：""。
 */
open class StringToSortConverter : Converter<String, Sort> {
	override fun convert(string: String): Sort {
		val splitStrings = string.split(",").map { it.trim() }
		val orders = splitStrings.map {
			when {
				it.startsWith("+") -> Sort.Order.asc(it.substring(1))
				it.startsWith("-") -> Sort.Order.desc(it.substring(1))
				else -> Sort.Order.asc(it)
			}
		}
		return Sort.by(orders)
	}
}
