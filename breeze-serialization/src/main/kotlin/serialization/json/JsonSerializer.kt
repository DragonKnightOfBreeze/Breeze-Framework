// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import icu.windea.breezeframework.serialization.*

/**
 * Json数据的序列化器。
 *
 * @see BreezeJsonSerializer
 * @see JacksonJsonSerializer
 * @see KotlinxJsonSerializer
 * @see GsonSerializer
 * @see FastJsonSerializer
 */
interface JsonSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormats.Json

	/**
	 * 默认的Json数据的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default : JsonSerializer by defaultJsonSerializer
}
