@file:JvmName("ArrayExtensions")

package com.windea.breezeframework.core.extensions

import java.util.*
import java.util.stream.*

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
fun IntArray.stream(start: Int = 0, end: Int = this.size): IntStream = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
fun LongArray.stream(start: Int = 0, end: Int = this.size): LongStream = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
fun DoubleArray.stream(start: Int = 0, end: Int = this.size): DoubleStream = Arrays.stream(this, start, end)

/**将数组转化为流对象。可指定索引范围，默认为整个数组。*/
fun <T> Array<out T>.stream(start: Int = 0, end: Int = this.size): Stream<T> = Arrays.stream(this, start, end)


/**将当前字节数组编码为base64格式的字节数组。*/
fun ByteArray.encodeToBase64(): ByteArray = Base64.getEncoder().encode(this)

/**将当前字节数组编码为base64格式的字符串。*/
fun ByteArray.encodeToBase64String(): String = Base64.getEncoder().encodeToString(this)

/**将当前字节数组解码为base64格式的字符串。*/
fun ByteArray.decodeToBase64(): ByteArray = Base64.getDecoder().decode(this)
