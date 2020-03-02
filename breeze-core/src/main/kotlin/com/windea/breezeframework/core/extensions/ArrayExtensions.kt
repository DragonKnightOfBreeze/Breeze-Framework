@file:JvmName("ArrayExtensions")

package com.windea.breezeframework.core.extensions

import java.nio.*
import java.util.*
import java.util.stream.*

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun <T> Array<out T>.onEach(action: (T) -> Unit): Array<out T> {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun ByteArray.onEach(action: (Byte) -> Unit): ByteArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun ShortArray.onEach(action: (Short) -> Unit): ShortArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun IntArray.onEach(action: (Int) -> Unit): IntArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun LongArray.onEach(action: (Long) -> Unit): LongArray {
	this.forEach(action)
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun FloatArray.onEach(action: (Float) -> Unit): FloatArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun DoubleArray.onEach(action: (Double) -> Unit): DoubleArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun BooleanArray.onEach(action: (Boolean) -> Unit): BooleanArray {
	return apply { for(element in this) action(element) }
}

/**遍历当前数组中的每个元素，执行指定的操作，然后返回当前数组自身。*/
inline fun CharArray.onEach(action: (Char) -> Unit): CharArray {
	return apply { for(element in this) action(element) }
}


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

fun main() {
	IntBuffer.allocate(1024).array()
	intArrayOf(1).smartToString()
	ByteBuffer.allocate(1024).array()
}
