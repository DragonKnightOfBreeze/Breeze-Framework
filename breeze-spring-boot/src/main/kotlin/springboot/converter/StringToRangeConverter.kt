// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*

/**字符串到双精度浮点数范围的转化器。*/
open class StringToRangeConverter : Converter<String, ClosedFloatingPointRange<Double>> {
	override fun convert(source: String): ClosedFloatingPointRange<Double>? {
		return source.split("..").map { it.trim().toDouble() }.let { it[0]..it[1] }
	}
}
