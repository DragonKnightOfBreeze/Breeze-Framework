package com.windea.breezeframework.serializer.impl.json

import com.fasterxml.jackson.databind.json.*
import com.google.gson.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.*

/**
 * Json的序列化器。
 *
 * 注意：其实现依赖于第三方库，如`jackson`，`gson`，`fastjson`。
 */
interface JsonSerializer : Serializer {
	companion object {
		private const val jacksonJsonClassName = "com.fasterxml.jackson.databind.json.JsonMapper"
		private const val gsonClassName = "com.google.gson.Gson"
		private const val fastjsonClassName = "com.alibaba.fastjson.JSON"

		/**得到Json的序列化器的实例。*/
		val instance: JsonSerializer = when {
			presentInClassPath(jacksonJsonClassName) -> JacksonJsonSerializer
			presentInClassPath(gsonClassName) -> GsonSerializer
			presentInClassPath(fastjsonClassName) -> FastJsonSerializer
			else -> throw IllegalStateException("No supported json serializer implementation found in classpath.")
		}


		/**配置JacksonJson的序列化器。*/
		fun configureJacksonJson(block: (JsonMapper) -> Unit) {
			block(JacksonJsonSerializer.mapper)
		}

		/**配置Gson的序列化器。*/
		fun configureGson(block: (GsonBuilder) -> Unit) {
			block(GsonSerializer.gsonBuilder)
		}
	}
}

