// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import java.lang.reflect.*

/**
 * 数据的序列化器。
 *
 * 数据的序列化器基于特定的数据类型，对数据进行序列化和反序列化。
 * 其具体实现可能需要依赖第三方库，如`gson`，`fastjson`，`jackson`和`kotlinx-serialization`。
 *
 * @see DataFormat
 */
interface DataSerializer: Serializer<String> {
	/**
	 * 对应的数据类型。
	 */
	val dataFormat: DataFormat

	/**
	 * 序列化指定对象。
	 */
	override fun <T> serialize(target: T): String

	/**
	 * 反序列化指定的文本。
	 */
	override fun <T> deserialize(value: String, type: Class<T>): T

	/**
	 * 反序列化指定的文本。
	 */
	override fun <T> deserialize(value: String, type: Type): T
}

