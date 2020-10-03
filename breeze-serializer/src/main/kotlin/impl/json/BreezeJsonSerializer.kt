// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.json

import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**
 * 由BreezeJson实现的Json序列化器。
 * @see com.windea.breezeframework.mapper.impl.JsonMapper
 */
internal object BreezeJsonSerializer : JsonSerializer, BreezeSerializer<JsonMapper> {
	internal val configBuilder = JsonMapper.Config.Builder()
	internal val mapper by lazy { JsonMapper(configBuilder.build()) }
	override val delegate:JsonMapper get() = mapper

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
