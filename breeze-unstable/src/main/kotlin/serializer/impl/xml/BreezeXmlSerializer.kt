// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serializer.impl.xml

import icu.windea.breezeframework.mapper.impl.*
import icu.windea.breezeframework.serializer.impl.*
import java.io.*
import java.lang.reflect.*

/**
 * 由BreezeXml实现的Xml的序列化器。
 * @see icu.windea.breezeframework.mapper.impl.XmlMapper
 */
internal object BreezeXmlSerializer : XmlSerializer, BreezeSerializer<XmlMapper> {
	internal val configBuilder = XmlMapper.Config.Builder()
	internal val mapper by lazy { XmlMapper(configBuilder.build()) }
	override val delegate: XmlMapper get() = mapper

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
