// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.serializer

import icu.windea.breezeframework.serialization.config.*
import icu.windea.breezeframework.serialization.io.*
import java.lang.reflect.*

/**
 * 由Breeze Framework实现的Xml数据的序列化器。
 */
class BreezeXmlSerializer(
	val config: XmlConfig = XmlConfig()
) : XmlSerializer, BreezeSerializer {
	private val reader = XmlReader(config)
	private val writer = XmlWriter(config)

	override fun <T> serialize(target: T): String {
		return writer.write(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return reader.read(value,type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return reader.read(value,type)
	}
}
