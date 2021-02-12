// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.extension.*
import com.windea.breezeframework.serialization.serializer.*
import java.lang.reflect.*

/**
 * 数据格式。
 *
 * 数据格式基于一定的格式存储数据。
 *
 * @see DataSerializer
 */
@BreezeComponent
interface DataFormat {
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
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 *
	 * 可以进行自定义。
	 */
	val serializer: DataSerializer

	/**
	 * 序列化指定对象。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> serialize(value: T): String {
		return serializer.serialize(value)
	}

	/**
	 * 反序列化指定文本。
	 *
	 * 可以由第三方库委托实现。基于classpath推断具体实现，或者使用框架本身的默认实现。
	 */
	fun <T> deserialize(value: String, type: Class<T>): T {
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

	//region String Data Formats
	/**
	 * Json数据格式。
	 */
	object Json : DataFormat {
		override val fileExtension: String = "json"
		override val fileExtensions: Array<String> = arrayOf("json", "jsb2", "jsb3", "patch")
		override var serializer: JsonSerializer = defaultJsonSerializer
	}

	/**
	 * Yaml数据格式。
	 */
	object Yaml : DataFormat {
		override val fileExtension: String = "yml"
		override val fileExtensions: Array<String> = arrayOf("yml", "yaml")
		override var serializer: YamlSerializer = defaultYamlSerializer
	}

	/**
	 * Xml数据格式。
	 */
	object Xml : DataFormat {
		override val fileExtension: String = "xml"
		override val fileExtensions: Array<String> = arrayOf("xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan",
			"pom", "rng", "tld", "wadl", "wsdd", "wsdl", "xjb", "xsd", "xsl", "xslt", "xul")
		override var serializer: XmlSerializer = defaultXmlSerializer
	}

	/**
	 * Properties数据格式。
	 */
	object Properties : DataFormat {
		override val fileExtension: String = "properties"
		override val fileExtensions: Array<String> = arrayOf("properties")
		override var serializer: PropertiesSerializer = defaultPropertiesSerializer
	}

	/**
	 * Csv数据格式。
	 */
	object Csv : DataFormat {
		override val fileExtension: String = "csv"
		override val fileExtensions: Array<String> = arrayOf("csv")
		override var serializer: CsvSerializer = defaultCsvSerializer
	}
	//endregion

	companion object{
		private val dataFormats = mutableListOf<DataFormat>()

		/**
		 * 得到已注册的数据格式列表。
		 */
		@JvmStatic fun values():List<DataFormat>{
			return dataFormats
		}

		/**
		 * 注册指定的数据格式。
		 */
		@JvmStatic fun register(dataFormat: DataFormat){
			dataFormats.add(dataFormat)
		}

		init {
			registerStringDataFormats()
		}

		private fun registerStringDataFormats() {
			register(Json)
			register(Yaml)
			register(Xml)
			register(Properties)
			register(Csv)
		}
	}
}
