package com.windea.breezeframework.serializer.impl.json

import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**由BreezeJson实现的json的序列化器。*/
internal object BreezeJsonSerializer : JsonSerializer, BreezeSerializer {
	internal val configBuilder = JsonMapper.Config.Builder()
	internal val mapper by lazy { JsonMapper(configBuilder.build()) }

	override fun <T : Any> read(string: String, type: Class<T>): T {
		return mapper.unmap(string, type)
	}

	override fun <T : Any> read(string: String, type: Type): T {
		return mapper.unmap(string, type)
	}

	override fun <T : Any> read(file: File, type: Class<T>): T {
		return mapper.unmap(file.readText(), type)
	}

	override fun <T : Any> read(file: File, type: Type): T {
		return mapper.unmap(file.readText(), type)
	}

	override fun <T : Any> write(data: T): String {
		return mapper.map(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.map(data).let { file.writeText(it) }
	}
}
