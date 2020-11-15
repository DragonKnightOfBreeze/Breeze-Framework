// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.extension.*

/**
 * Json的序列化器。
 */
@BreezeComponent
interface JsonSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Json

	/**
	 * 默认的Json的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: JsonSerializer by defaultJsonSerializer

	/**
	 * 由Jackson实现的Json的序列化器。
	 *
	 * @see com.fasterxml.jackson.databind.json.JsonMapper
	 */
	class JacksonJsonSerializer : JacksonSerializer.JacksonJsonSerializer()

	/**
	 * 由Gson实现的Json的序列化器。
	 *
	 * @see com.google.gson.Gson
	 */
	class GsonSerializer : com.windea.breezeframework.serialization.component.GsonSerializer()

	/**
	 * 由FastJson实现的Json的序列化器。
	 *
	 * @see com.alibaba.fastjson.JSON
	 */
	class FastJsonSerializer : com.windea.breezeframework.serialization.component.FastJsonSerializer()

	/**
	 * 由Kotlinx Serialization实现的Json的序列化器。
	 *
	 * @see kotlinx.serialization.json.Json
	 */
	class KotlinxJsonSerializer : KotlinxSerializer.KotlinxJsonSerializer()

	/**
	 * 由Breeze Framework实现的轻量的Json的序列化器。
	 */
	class BreezeJsonSerializer : BreezeSerializer.BreezeJsonSerializer {
		constructor()

		constructor(configBuilder: ConfigBuilder = ConfigBuilder()) : super(configBuilder)

		constructor(configBuilder: ConfigBuilder.() -> Unit) : super(configBuilder)
	}
}
