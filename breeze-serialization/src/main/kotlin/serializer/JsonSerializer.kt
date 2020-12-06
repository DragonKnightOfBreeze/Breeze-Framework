// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.serialization.*
import com.windea.breezeframework.serialization.extension.*

/**
 * Json的序列化器。
 */
interface JsonSerializer : DataSerializer {
	val dataFormat: DataFormat get() = DataFormat.Json

	/**
	 * 默认的Json的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: JsonSerializer by defaultJsonSerializer
}
