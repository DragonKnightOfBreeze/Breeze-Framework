// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.serializer

import icu.windea.breezeframework.serialization.*
import icu.windea.breezeframework.serialization.extension.*

/**
 * Xml数据的序列化器。
 *
 * @see JacksonXmlSerializer
 * @see BreezeXmlSerializer
 */
interface XmlSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormat.Xml

	/**
	 * 默认的Xml的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的序列化器。
	 */
	companion object Default: XmlSerializer by defaultXmlSerializer
}
