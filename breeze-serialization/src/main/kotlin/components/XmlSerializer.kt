// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.serialization.extensions.*

/**
 * Xml的序列化器。
 */
@BreezeComponent
interface XmlSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Xml

	/**
	 * 默认的Xml的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: XmlSerializer by defaultXmlSerializer

	/**
	 * 由Jackson实现的Xml的序列化器。
	 *
	 * @see com.fasterxml.jackson.dataformat.xml.XmlMapper
	 */
	class JacksonXmlSerializer : JacksonSerializer.JacksonXmlSerializer()

	/**
	 * 由Breeze Framework实现的轻量的Xml的序列化器。
	 */
	class BreezeXmlSerializer : BreezeSerializer.BreezeXmlSerializer()
}
