/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

package com.windea.breezeframework.serializer.impl.properties

import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*
import java.util.*

/**
 * 由BreezeProperties实现的Properties序列化器。
 * @see com.windea.breezeframework.mapper.impl.PropertiesMapper
 */
internal object BreezePropertiesSerializer : PropertiesSerializer, BreezeSerializer<PropertiesMapper> {
	internal val configBuilder = PropertiesMapper.Config.Builder()
	internal val mapper by lazy { PropertiesMapper(configBuilder.build()) }
	override val delegate:PropertiesMapper get() = mapper

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

	override fun <T : Any> read(properties: Properties, type: Class<T>): T {
		return mapper.unmap(StringWriter().apply { properties.store(this, "") }.toString(), type)
	}

	override fun <T : Any> read(properties: Properties, type: Type): T {
		return mapper.unmap(StringWriter().apply { properties.store(this, "") }.toString(), type)
	}

	override fun <T : Any> write(data: T): String {
		return mapper.map(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.map(data).let { file.writeText(it) }
	}

	override fun <T : Any> write(data: T, properties: Properties) {
		return mapper.map(data).let { properties.load(it.reader()) }
	}
}
