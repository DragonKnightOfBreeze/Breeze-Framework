package com.windea.breezeframework.serializer.impl.json

import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.Serializer
import kotlinx.serialization.*
import kotlinx.serialization.json.*

/**
 * Json的序列化器。
 *
 * 注意：其实现依赖于第三方库，如``kotlinx-serialization`，`jackson`，`gson`，`fastjson`。
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
			else -> throw IllegalStateException("No supported json serializer implementation found in classpath.")
		}


		/**配置KotlinxSerializationJson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		@OptIn(UnstableDefault::class)
		fun configureKotlinJson(block: JsonBuilder.() -> Unit) {
			block(KotlinJsonSerializer.jsonBuilder)
		}

		/**配置JacksonJson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		fun configureJacksonJson(block: (JsonMapper) -> Unit) {
			block(JacksonJsonSerializer.mapper)
		}

		/**配置Gson的序列化器。注意需要在使用前配置，并且仅当对应的序列化器适用时才应调用。*/
		fun configureGson(block: (GsonBuilder) -> Unit) {
			block(GsonSerializer.gsonBuilder)
		}
	}
}

