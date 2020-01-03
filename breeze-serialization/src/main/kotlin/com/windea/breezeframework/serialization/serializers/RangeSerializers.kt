@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.serialization.serializers

import kotlinx.serialization.*
import kotlinx.serialization.internal.*

//remove annotation for it is not serializable automatically
class RangeSerializer<T : Comparable<T>>(
	val vSerializer: KSerializer<T>
) : KSerializer<ClosedRange<T>> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.ClosedRange")

	override fun serialize(encoder: Encoder, obj: ClosedRange<T>) {
		encoder.encodeString(obj.toString())
	}

	override fun deserialize(decoder: Decoder): ClosedRange<T> {
		val (start, endInclusive) = decoder.decodeString().split("..", limit = 2).map { it.trim() }
		return (start to endInclusive).toRange()
	}

	fun Pair<String, String>.toRange(): ClosedRange<T> {
		try {
			((first.single()..second.single()) as? ClosedRange<T>)?.let { return it }
			((first.toInt()..second.toInt()) as? ClosedRange<T>)?.let { return it }
			((first.toLong()..second.toLong()) as? ClosedRange<T>)?.let { return it }
			((first.toFloat()..second.toFloat()) as? ClosedRange<T>)?.let { return it }
			((first.toDouble()..second.toDouble()) as ClosedRange<T>).let { return it }
		} catch(e: NumberFormatException) {
			throw IllegalArgumentException("value of ClosedFloatingPointRange<T> is invalid.")
		} catch(e: Exception) {
			throw UnsupportedOperationException("T of ClosedFloatingPointRange<T> is neither Float or Double")
		}
	}
}

//remove annotation for it is not serializable automatically
class FloatingPointRangeSerializer<T : Comparable<T>>(
	val vSerializer: KSerializer<T>
) : KSerializer<ClosedFloatingPointRange<T>> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.ClosedFloatingPointRange")

	override fun serialize(encoder: Encoder, obj: ClosedFloatingPointRange<T>) {
		encoder.encodeString(obj.toString())
	}

	override fun deserialize(decoder: Decoder): ClosedFloatingPointRange<T> {
		val (start, endInclusive) = decoder.decodeString().split("..", limit = 2).map { it.trim() }
		return (start to endInclusive).toRange()
	}

	fun Pair<String, String>.toRange(): ClosedFloatingPointRange<T> {
		try {
			((first.toFloat()..second.toFloat()) as? ClosedFloatingPointRange<T>)?.let { return it }
			((first.toDouble()..second.toDouble()) as ClosedFloatingPointRange<T>).let { return it }
		} catch(e: NumberFormatException) {
			throw IllegalArgumentException("value of ClosedFloatingPointRange<T> is invalid.")
		} catch(e: Exception) {
			throw UnsupportedOperationException("T of ClosedFloatingPointRange<T> is neither Float or Double")
		}
	}
}

//remove annotation for it is not serializable automatically
object CharRangeSerializer : KSerializer<CharRange> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.CharRange")

	override fun serialize(encoder: Encoder, obj: CharRange) {
		encoder.encodeString(obj.toString())
	}

	override fun deserialize(decoder: Decoder): CharRange {
		val (first, last) = decoder.decodeString().split("..", limit = 2).map { it.trim() }
		return first.single()..last.single()
	}
}

//remove annotation for it is not serializable automatically
object IntRangeSerializer : KSerializer<IntRange> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.IntRange")

	override fun serialize(encoder: Encoder, obj: IntRange) {
		encoder.encodeString(obj.toString())
	}

	override fun deserialize(decoder: Decoder): IntRange {
		val (first, last) = decoder.decodeString().split("..", limit = 2).map { it.trim() }
		return first.toInt()..last.toInt()
	}
}

//remove annotation for it is not serializable automatically
object LongRangeSerializer : KSerializer<LongRange> {
	override val descriptor: SerialDescriptor = StringDescriptor.withName("kotlin.ranges.LongRange")

	override fun serialize(encoder: Encoder, obj: LongRange) {
		encoder.encodeString(obj.toString())
	}

	override fun deserialize(decoder: Decoder): LongRange {
		val (first, last) = decoder.decodeString().split("..", limit = 2).map { it.trim() }
		return first.toLong()..last.toLong()
	}
}
