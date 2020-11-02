// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import java.lang.reflect.*

/**
 * 数据类型。
 *
 * 数据类型基于一定的格式存储数据。
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
	 * 根据classpath中存在的可以委托实现的第三方库推断而来，也可自行配置。
	 */
	var serializer: Serializer

	/**
	 * 序列化指定对象。
	 */
	fun <T : Any> serialize(value: T): String {
		return serializer.serialize(value)
	}

	/**
	 * 反序列化指定文本。
	 */
	fun <T : Any> deserialize(value: String, type: Class<T>): T {
		return serializer.deserialize(value, type)
	}

	/**
	 * 反序列化指定文本。
	 */
	fun <T> deserialize(value: String, type: Type): T {
		return serializer.deserialize(value, type)
	}

	//region Default Data Types
	/**
	 * Json数据类型。
	 */
	object Json : DataType {
		private const val kotlinJsonClassName = "kotlinx.serialization.json.Json"
		private const val jacksonJsonClassName = "com.fasterxml.jackson.databind.json.JsonMapper"
		private const val gsonClassName = "com.google.gson.Gson"
		private const val fastjsonClassName = "com.alibaba.fastjson.JSON"

		override val fileExtension: String = "json"
		override val fileExtensions: Array<String> = arrayOf("json", "jsb2", "jsb3", "patch")
		override var serializer: Serializer = when {
			presentInClassPath(kotlinJsonClassName) -> JsonSerializer.KotlinxJsonSerializer
			presentInClassPath(jacksonJsonClassName) -> JsonSerializer.JacksonJsonSerializer
			presentInClassPath(gsonClassName) -> JsonSerializer.GsonSerializer
			presentInClassPath(fastjsonClassName) -> JsonSerializer.FastJsonSerializer
			else -> JsonSerializer.BreezeJsonSerializer
		}
	}

	/**
	 * Yaml数据类型。
	 */
	object Yaml : DataType {
		private const val jacksonYamlClassName = "com.fasterxml.jackson.dataformat.yaml.YAMLMapper"
		private const val snackYamlClassName = "org.yaml.snakeyaml.Yaml"

		override val fileExtension: String = "yml"
		override val fileExtensions: Array<String> = arrayOf("yml", "yaml")
		override var serializer: Serializer=when {
			presentInClassPath(jacksonYamlClassName) -> YamlSerializer.JacksonYamlSerializer
			presentInClassPath(snackYamlClassName) -> YamlSerializer.SnakeYamlSerializer
			else -> YamlSerializer.BreezeYamlSerializer
		}
	}

	/**
	 * Xml数据类型。
	 */
	object Xml : DataType {
		private const val jacksonXmlClassName = "com.fasterxml.jackson.dataformat.xml.XmlMapper"

		override val fileExtension: String = "xml"
		override val fileExtensions: Array<String> = arrayOf("xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan",
			"pom", "rng", "tld", "wadl", "wsdd", "wsdl", "xjb", "xsd", "xsl", "xslt", "xul")
		override var serializer: Serializer = when {
			presentInClassPath(jacksonXmlClassName) -> XmlSerializer.JacksonXmlSerializer
			else -> XmlSerializer.BreezeXmlSerializer
		}
	}

	/**
	 * Properties数据类型。基于键值对。
	 */
	object Properties : DataType {
		private const val jacksonPropertiesClassName = "com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper"

		override val fileExtension: String = "properties"
		override val fileExtensions: Array<String> = arrayOf("properties")
		override var serializer: Serializer = when {
			presentInClassPath(jacksonPropertiesClassName) -> PropertiesSerializer.JacksonPropertiesSerializer
			else -> PropertiesSerializer.BreezePropertiesSerializer
		}
	}

	/**
	 * Csv数据类型。基于以逗号分隔的值。
	 */
	object Csv : DataType {
		override val fileExtension: String = "csv"
		override val fileExtensions: Array<String> = arrayOf("csv")
		override var serializer: Serializer = CsvSerializer.BreezeCsvSerializer
	}

	/**
	 * Tsv数据类型，基于以缩进分割的值。
	 */
	object Tsv : DataType {
		override val fileExtension: String = "tsv"
		override val fileExtensions: Array<String> = arrayOf("tsv")
		override var serializer: Serializer = TsvSerializer.BreezeTsvSerializer
	}
	//endregion
}
