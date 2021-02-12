// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*

/**字符串到字符范围的转化器。*/
open class StringToCharRangeConverter : Converter<String, CharRange> {
	override fun convert(source: String): CharRange? {
		return source.split("..").map { it.trim().single() }.let { it[0]..it[1] }
	}
}
