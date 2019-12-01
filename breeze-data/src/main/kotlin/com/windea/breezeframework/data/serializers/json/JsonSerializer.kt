package com.windea.breezeframework.data.serializers.json

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface JsonSerializer : Serializer {
	companion object {
		val instance: JsonSerializer = when {
			checkClassForName("com.fasterxml.jackson.databind.json.JsonMapper") -> JacksonJsonSerializer
			checkClassForName("com.google.gson.Gson") -> GsonJsonSerializer
			checkClassForName("com.alibaba.fastjson.JSON") -> FastJsonSerializer
			else -> throw IllegalStateException("Please contain at least one serializer implementation in classpath.")
		}
	}
}

interface JsonSerializerConfig : SerializerConfig
