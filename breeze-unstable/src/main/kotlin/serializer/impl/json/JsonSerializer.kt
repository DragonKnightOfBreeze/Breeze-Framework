// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.json

import com.google.gson.*
import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.*
import kotlinx.serialization.json.*
import com.fasterxml.jackson.databind.json.JsonMapper as JacksonJsonMapper

/**
 * Json的序列化器。
 *
 * 其实现默认由`breeze-mapper`提供，但也可以依赖于第三方库，如``kotlinx-serialization`，`jackson`，`gson`，`fastjson`。
 */
interface JsonSerializer : Serializer {
	companion object {
		private const val kotlinJsonClassName = "kotlinx.serialization.json.Json"
		private const val jacksonJsonClassName = "com.fasterxml.jackson.databind.json.JsonMapper"
		private const val gsonClassName = "com.google.gson.Gson"
		private const val fastjsonClassName = "com.alibaba.fastjson.JSON"

		/**得到Json的序列化器的实例。*/
		val instance: JsonSerializer = when {
			presentInClassPath(kotlinJsonClassName) -> KotlinJsonSerializer
			presentInClassPath(jacksonJsonClassName) -> JacksonJsonSerializer
			presentInClassPath(gsonClassName) -> GsonSerializer
			presentInClassPath(fastjsonClassName) -> FastJsonSerializer
			else -> BreezeJsonSerializer
			//else -> throw IllegalStateException("No supported json serializer implementation found in classpath.")
		}

		/**配置BreezeJson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureBreezeJson(block: (JsonMapper.Config.Builder) -> Unit) {
			block(BreezeJsonSerializer.configBuilder)
		}

		/**配置KotlinxSerializationJson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureKotlinJson(block: (Json) -> Unit) {
			block(KotlinJsonSerializer.json)
		}

		/**配置JacksonJson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureJacksonJson(block: (JacksonJsonMapper) -> Unit) {
			block(JacksonJsonSerializer.mapper)
		}

		/**配置Gson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptionalApi
		fun configureGson(block: (GsonBuilder) -> Unit) {
			block(GsonSerializer.gsonBuilder)
		}
	}
}

