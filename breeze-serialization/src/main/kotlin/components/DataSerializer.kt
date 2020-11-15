// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*

/**
 * 数据的序列化器。
 *
 * 数据的序列化器基于特定的数据类型，对数据进行序列化和反序列化。
 * 其具体实现可能需要依赖第三方库，如`gson`，`fastjson`，`jackson`和`kotlinx-serialization`。
 *
 * @see DataType
 */
@BreezeComponent
interface DataSerializer:Serializer<String> {
	/**
	 * 对应的数据类型。
	 */
	val dataType: DataType

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

