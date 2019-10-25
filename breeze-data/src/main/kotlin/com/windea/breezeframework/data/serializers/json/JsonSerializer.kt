package com.windea.breezeframework.data.serializers.json

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface JsonSerializer : Serializer {
	companion object {
		val instance: JsonSerializer = when {
			checkClassForName("com.google.gson.Gson") -> GsonJsonSerializer
			checkClassForName("com.alibaba.fastjson.JSON") -> FastJsonSerializer
			checkClassForName("com.fasterxml.jackson.databind.json.JsonMapper") -> JacksonJsonSerializer
			else -> throw IllegalStateException("Please contains at least one data serializer implementation in classpath.")
		}
	}
}

interface JsonSerializerConfig : SerializerConfig
