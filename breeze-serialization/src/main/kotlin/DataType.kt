// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization
import com.windea.breezeframework.core.annotations.*
import java.lang.reflect.*

/**
 * 数据类型。
 *
 * 数据类型基于一定的格式存储数据。
 *
 * @see Serializer
 */
@BreezeComponent
interface DataType {
	/**
	 * 文件扩展名。
	 */
	val fileExtension: String

	/**
	 * 可能的文件扩展名一览。
	 */
	val fileExtensions: Array<String>

	/**
	 * 对应的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	val serializer: Serializer

	/**
	 * 序列化指定对象。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T : Any> serialize(value: T): String {
		return serializer.serialize(value)
	}

	/**
	 * 反序列化指定文本。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T : Any> deserialize(value: String, type: Class<T>): T {
		return serializer.deserialize(value, type)
	}

	/**
	 * 反序列化指定文本。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> deserialize(value: String, type: Type): T {
		return serializer.deserialize(value, type)
	}

	//region Data Types
	/**
	 * Json数据类型。
	 */
	object Json : DataType {
		override val fileExtension: String = "json"
		override val fileExtensions: Array<String> = arrayOf("json", "jsb2", "jsb3", "patch")
		override val serializer: JsonSerializer = defaultJsonSerializer
	}

	/**
	 * Yaml数据类型。
	 */
	object Yaml : DataType {
		override val fileExtension: String = "yml"
		override val fileExtensions: Array<String> = arrayOf("yml", "yaml")
		override val serializer: YamlSerializer = defaultYamlSerializer
	}

	/**
	 * Xml数据类型。
	 */
	object Xml : DataType {
		override val fileExtension: String = "xml"
		override val fileExtensions: Array<String> = arrayOf("xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan",
			"pom", "rng", "tld", "wadl", "wsdd", "wsdl", "xjb", "xsd", "xsl", "xslt", "xul")
		override val serializer: XmlSerializer = defaultXmlSerializer
	}

	/**
	 * Properties数据类型。基于键值对。
	 */
	object Properties : DataType {
		override val fileExtension: String = "properties"
		override val fileExtensions: Array<String> = arrayOf("properties")
		override val serializer: PropertiesSerializer = defaultPropertiesSerializer
	}

	/**
	 * Csv数据类型。基于以逗号分隔的值。
	 */
	object Csv : DataType {
		override val fileExtension: String = "csv"
		override val fileExtensions: Array<String> = arrayOf("csv")
		override val serializer: CsvSerializer = defaultCsvSerializer
	}

	/**
	 * Tsv数据类型，基于以缩进分割的值。
	 */
	object Tsv : DataType {
		override val fileExtension: String = "tsv"
		override val fileExtensions: Array<String> = arrayOf("tsv")
		override val serializer: TsvSerializer = defaultTsvSerializer
	}
	//endregion
}
