@file:JvmName("ArrayExtensions")

package com.windea.breezeframework.core.extensions

import java.util.*
import java.util.stream.*

//region operator extensions
/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat
 * */
operator fun <T> Array<out T>.times(n: Int): List<T> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun ByteArray.times(n: Int): List<Byte> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun ShortArray.times(n: Int): List<Short> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun IntArray.times(n: Int): List<Int> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun LongArray.times(n: Int): List<Long> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun FloatArray.times(n: Int): List<Float> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun DoubleArray.times(n: Int): List<Double> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun BooleanArray.times(n: Int): List<Boolean> = this.toList().repeat(n)

/**
 * 重复当前数组中的元素到指定次数，并转化为列表。
 *
 * @see com.windea.breezeframework.core.extensions.repeat*/
operator fun CharArray.times(n: Int): List<Char> = this.toList().repeat(n)


/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun <T> Array<out T>.div(n: Int): List<List<T>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun ByteArray.div(n: Int): List<List<Byte>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun ShortArray.div(n: Int): List<List<Short>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun IntArray.div(n: Int): List<List<Int>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun LongArray.div(n: Int): List<List<Long>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun FloatArray.div(n: Int): List<List<Float>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun DoubleArray.div(n: Int): List<List<Double>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun BooleanArray.div(n: Int): List<List<Boolean>> = this.toList().chunked(n)

/**
 * 切分当前数组中的元素到指定个数，并转化为列表。
 *
 * @see kotlin.collections.chunked*/
operator fun CharArray.div(n: Int): List<List<Char>> = this.toList().chunked(n)


/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun <T> Array<out T>.get(indices: IntRange): Array<out T> = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun ByteArray.get(indices: IntRange): ByteArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun ShortArray.get(indices: IntRange): ShortArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun IntArray.get(indices: IntRange): IntArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun LongArray.get(indices: IntRange): LongArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun FloatArray.get(indices: IntRange): FloatArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun DoubleArray.get(indices: IntRange): DoubleArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun BooleanArray.get(indices: IntRange): BooleanArray = this.sliceArray(indices)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun CharArray.get(indices: IntRange): CharArray = this.sliceArray(indices)


/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun <T> Array<out T>.get(startIndex: Int, endIndex: Int): Array<out T> = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun ByteArray.get(startIndex: Int, endIndex: Int): ByteArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun ShortArray.get(startIndex: Int, endIndex: Int): ShortArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun IntArray.get(startIndex: Int, endIndex: Int): IntArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun LongArray.get(startIndex: Int, endIndex: Int): LongArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun FloatArray.get(startIndex: Int, endIndex: Int): FloatArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun DoubleArray.get(startIndex: Int, endIndex: Int): DoubleArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun BooleanArray.get(startIndex: Int, endIndex: Int): BooleanArray = this.sliceArray(startIndex until endIndex)

/**
 * 得到索引指定范围内的子数组。
 *
 * @see kotlin.collections.sliceArray*/
operator fun CharArray.get(startIndex: Int, endIndex: Int): CharArray = this.sliceArray(startIndex until endIndex)
//endregion

//region common extensions
/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun <T> Array<out T>.onEach(action: (T) -> Unit): Array<out T> = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun ByteArray.onEach(action: (Byte) -> Unit): ByteArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun ShortArray.onEach(action: (Short) -> Unit): ShortArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun IntArray.onEach(action: (Int) -> Unit): IntArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun LongArray.onEach(action: (Long) -> Unit): LongArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun FloatArray.onEach(action: (Float) -> Unit): FloatArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun DoubleArray.onEach(action: (Double) -> Unit): DoubleArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun BooleanArray.onEach(action: (Boolean) -> Unit): BooleanArray = apply { for(element in this) action(element) }

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun CharArray.onEach(action: (Char) -> Unit): CharArray = apply { for(element in this) action(element) }


/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
@JvmSynthetic
fun <T> Array<out T>.stream(start: Int = 0, end: Int = this.size): Stream<T> = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
@JvmSynthetic
fun IntArray.stream(start: Int = 0, end: Int = this.size): IntStream = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
@JvmSynthetic
fun LongArray.stream(start: Int = 0, end: Int = this.size): LongStream = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
@JvmSynthetic
fun DoubleArray.stream(start: Int = 0, end: Int = this.size): DoubleStream = Arrays.stream(this, start, end)


/**将当前字节数组编码为base64格式的字节数组。*/
fun ByteArray.encodeToBase64(): ByteArray = Base64.getEncoder().encode(this)

/**将当前字节数组解码为base64格式的字节数组。*/
fun ByteArray.decodeToBase64(): ByteArray = Base64.getDecoder().decode(this)

/**将当前字节数组编码为base64格式的字符串。*/
fun ByteArray.encodeToBase64String(): String = Base64.getEncoder().encodeToString(this)
//endregion
