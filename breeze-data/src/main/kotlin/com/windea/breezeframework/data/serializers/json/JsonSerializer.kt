package com.windea.breezeframework.data.serializers.json

import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*

interface JsonSerializer<S, C> : DataSerializer<S, C>, JsonLoader, JsonDumper {
	override val dataType: DataType get() = DataType.Json
	
	companion object {
		val instance: JsonSerializer<*, *> by lazy {
			when {
				checkClassForName("com.google.gson.Gson") -> GsonJsonSerializer()
				checkClassForName("com.alibaba.fastjson.JSON") -> FastJsonSerializer()
				checkClassForName("com.fasterxml.jackson.databind.json.JsonMapper") -> JacksonJsonSerializer()
				else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
			}
		}
	}
}


interface JsonLoader : DataLoader {
	override val dataType: DataType get() = DataType.Json
}


interface JsonDumper : DataDumper {
	override val dataType: DataType get() = DataType.Json
}
