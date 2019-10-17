package com.windea.breezeframework.springboot.core.converters

import org.springframework.core.convert.converter.*

/**字符串到范围的转化器。*/
sealed class StringToRangeConverter<T : ClosedRange<*>> : Converter<String, T>

/**字符串到整数范围的转化器。*/
open class StringToIntRangeConverter : StringToRangeConverter<IntRange>() {
	override fun convert(source: String): IntRange? {
		return source.split("..").map { it.trim().toInt() }.let { it[0]..it[1] }
	}
}

/**字符串到长整数范围的转化器。*/
open class StringToLongRangeConverter : StringToRangeConverter<LongRange>() {
	override fun convert(source: String): LongRange? {
		return source.split("..").map { it.trim().trimEnd('l', 'L').toLong() }.let { it[0]..it[1] }
	}
}

/**字符串到单精度浮点数范围的转化器。*/
open class StringToFloatRangeConverter : StringToRangeConverter<ClosedFloatingPointRange<Float>>() {
	override fun convert(source: String): ClosedFloatingPointRange<Float>? {
		return source.split("..").map { it.trim().trimEnd('f', 'F').toFloat() }.let { it[0]..it[1] }
	}
}

/**字符串到双精度浮点数范围的转化器。*/
open class StringToDoubleRangeConverter : StringToRangeConverter<ClosedFloatingPointRange<Double>>() {
	override fun convert(source: String): ClosedFloatingPointRange<Double>? {
		return source.split("..").map { it.trim().toDouble() }.let { it[0]..it[1] }
	}
}
