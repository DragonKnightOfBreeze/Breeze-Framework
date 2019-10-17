package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.internal.*

sealed class RangeSerializer<T : Comparable<T>, R : ClosedRange<T>>(
	val vSerializer: KSerializer<T>
) : KSerializer<R> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.ClosedRange<T>")
	
	override fun serialize(encoder: Encoder, obj: R) {
		val start = obj.start.toString()
		val endInclusive = obj.endInclusive.toString()
		encoder.encodeString("$start..$endInclusive")
	}
	
	override fun deserialize(decoder: Decoder): R {
		val string = decoder.decodeString()
		val (start, endInclusive) = string.split("..", limit = 2).map { it.trim() }
		return (start to endInclusive).toRange()
	}
	
	abstract fun Pair<String, String>.toRange(): R
}

object IntRangeSerializer : RangeSerializer<Int, IntRange>(IntSerializer) {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.IntRange")
	
	override fun Pair<String, String>.toRange(): IntRange = first.toInt()..second.toInt()
}

object LongRangeSerializer : RangeSerializer<Long, LongRange>(LongSerializer) {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.LongRange")
	
	override fun Pair<String, String>.toRange(): LongRange = first.toLong()..second.toLong()
}

object FloatRangeSerializer : RangeSerializer<Float, ClosedFloatingPointRange<Float>>(FloatSerializer) {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.ClosedFloatingPointRange<Float>")
	
	override fun Pair<String, String>.toRange(): ClosedFloatingPointRange<Float> = first.toFloat()..second.toFloat()
}

object DoubleRangeSerializer : RangeSerializer<Double, ClosedFloatingPointRange<Double>>(DoubleSerializer) {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.ClosedFloatingPointRange<Double>")
	
	override fun Pair<String, String>.toRange(): ClosedFloatingPointRange<Double> = first.toDouble()..second.toDouble()
}
