// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.springboot.converter

import org.springframework.core.convert.converter.*

/**字符串到长整数范围的转化器。*/
open class StringToLongRangeConverter : Converter<String, LongRange> {
	override fun convert(source: String): LongRange? {
		return source.split("..").map { it.trim().trimEnd('l', 'L').toLong() }.let { it[0]..it[1] }
	}
}
