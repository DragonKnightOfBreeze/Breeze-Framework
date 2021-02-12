// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl.yaml

import com.windea.breezeframework.mapper.impl.*
import com.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**
 * 由BreezeYaml实现的Yaml的序列化器。
 * @see com.windea.breezeframework.mapper.impl.YamlMapper
 */
internal object BreezeYamlSerializer : YamlSerializer, BreezeSerializer<YamlMapper> {
	internal val configBuilder = YamlMapper.Config.Builder()
	internal val mapper by lazy { YamlMapper(configBuilder.build()) }
	override val delegate: YamlMapper get() = mapper

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

	override fun readAll(string: String): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}

	override fun readAll(file: File): List<Any?> {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}

	override fun <T : Any> write(data: T): String {
		return mapper.map(data)
	}

	override fun <T : Any> write(data: T, file: File) {
		return mapper.map(data).let { file.writeText(it) }
	}

	override fun <T : Any> writeAll(data: Iterable<T>): String {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}

	override fun <T : Any> writeAll(data: Iterable<T>, file: File) {
		throw UnsupportedOperationException("Could not find suitable methods to delegate in YamlMapper.")
	}
}
