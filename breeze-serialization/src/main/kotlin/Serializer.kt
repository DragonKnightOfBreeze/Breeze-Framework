// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.alibaba.fastjson.*
import com.fasterxml.jackson.databind.json.*
import com.fasterxml.jackson.dataformat.yaml.*
import com.google.gson.*
import com.windea.breezeframework.core.annotations.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import org.yaml.snakeyaml.*
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.representer.*
import java.lang.reflect.*

/**
 * 序列化器。
 *
 * 序列化器用于对数据进行序列化和反序列化。
 * 其具体实现可能需要依赖第三方库，如`gson`，`fastjson`，`jackson`和`kotlinx-serialization`。
 *
 * @see DataType
 */
@BreezeComponent
interface Serializer {
	/**
	 * 对应的数据类型。
	 */
	val dataType: DataType

	/**
	 * 序列化指定对象。
	 */
	fun <T:Any> serialize(value: T): String

	/**
	 * 反序列化指定的文本。
	 */
	fun <T:Any> deserialize(value: String, type: Class<T>): T

	/**
	 * 反序列化指定的文本。
	 */
	fun <T:Any> deserialize(value: String, type: Type): T
}
