// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*

/**字符串到单精度浮点数范围的转化器。*/
open class StringToFloatRangeConverter : Converter<String, ClosedFloatingPointRange<Float>> {
	override fun convert(source: String): ClosedFloatingPointRange<Float>? {
		return source.split("..").map { it.trim().trimEnd('f', 'F').toFloat() }.let { it[0]..it[1] }
	}
}
