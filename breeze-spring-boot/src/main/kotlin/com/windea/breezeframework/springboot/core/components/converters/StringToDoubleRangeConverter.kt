package com.windea.breezeframework.springboot.core.components.converters

import org.springframework.core.convert.converter.*

/**字符串到双精度浮点数范围的转化器。*/
open class StringToDoubleRangeConverter : Converter<String, ClosedFloatingPointRange<Double>> {
	override fun convert(source: String): ClosedFloatingPointRange<Double>? {
		return source.split("..").map { it.trim().toDouble() }.let { it[0]..it[1] }
	}
}
