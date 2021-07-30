// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import icu.windea.breezeframework.core.component.*
import java.lang.reflect.*

/**
 * 数据格式。
 *
 * 数据格式基于一定的格式存储数据。
 *
 * @see DataSerializer
 */
interface DataFormat : Component {
	/**
	 * 文件扩展名。
	 */
	val fileExtension: String

	/**
	 * 可能的文件扩展名一览。
	 */
	val fileExtensions: Array<String>

	/**
	 * 对应的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	val serializer: DataSerializer

	/**
	 * 序列化指定对象。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> serialize(value: T): String {
		return serializer.serialize(value)
	}

	/**
	 * 反序列化指定文本。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> deserialize(value: String, type: Class<T>): T {
		return serializer.deserialize(value, type)
	}

	/**
	 * 反序列化指定文本。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> deserialize(value: String, type: Type): T {
		return serializer.deserialize(value, type)
	}
}
