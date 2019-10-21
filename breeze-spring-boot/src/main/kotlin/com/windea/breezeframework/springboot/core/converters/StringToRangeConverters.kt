package com.windea.breezeframework.springboot.core.converters

import org.springframework.core.convert.converter.*

/**字符串到字符范围的转化器。*/
open class StringToCharRangeConverter : Converter<String, CharRange> {
	override fun convert(source: String): CharRange? {
		return source.split("..").map { it.trim().single() }.let { it[0]..it[1] }
	}
}

/**字符串到整数范围的转化器。*/
open class StringToIntRangeConverter : Converter<String, IntRange> {
	override fun convert(source: String): IntRange? {
		return source.split("..").map { it.trim().toInt() }.let { it[0]..it[1] }
	}
}

/**字符串到长整数范围的转化器。*/
open class StringToLongRangeConverter : Converter<String, LongRange> {
	override fun convert(source: String): LongRange? {
		return source.split("..").map { it.trim().trimEnd('l', 'L').toLong() }.let { it[0]..it[1] }
	}
}

//NOT_TESTED
/**字符串到单精度浮点数范围的转化器。*/
open class StringToFloatRangeConverter : Converter<String, ClosedFloatingPointRange<Float>> {
	override fun convert(source: String): ClosedFloatingPointRange<Float>? {
		return source.split("..").map { it.trim().trimEnd('f', 'F').toFloat() }.let { it[0]..it[1] }
	}
}

//NOT_TESTED
/**字符串到双精度浮点数范围的转化器。*/
open class StringToDoubleRangeConverter : Converter<String, ClosedFloatingPointRange<Double>> {
	override fun convert(source: String): ClosedFloatingPointRange<Double>? {
		return source.split("..").map { it.trim().toDouble() }.let { it[0]..it[1] }
	}
}
