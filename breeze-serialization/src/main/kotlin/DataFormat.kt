// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.serialization.csv.*
import icu.windea.breezeframework.serialization.json.*
import icu.windea.breezeframework.serialization.properties.*
import icu.windea.breezeframework.serialization.xml.*
import icu.windea.breezeframework.serialization.yaml.*
import java.lang.reflect.*

/**
 * 数据格式。
 *
 * 数据格式基于一定的格式存储数据。
 *
 * @see DataSerializer
 */
interface DataFormat : Component {
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

	companion object Registry : AbstractComponentRegistry<DataFormat>() {
		override fun registerDefault() {
			register(Json)
			register(Yaml)
			register(Xml)
			register(Properties)
			register(Csv)
		}
	}

	//region Data Formats
	/**
	 * Json数据格式。
	 */
	object Json : DataFormat {
		override val fileExtension: String = "data/json"
		override val fileExtensions: Array<String> = arrayOf("data/json", "jsb2", "jsb3", "patch")
		override var serializer: JsonSerializer = defaultJsonSerializer
	}

	/**
	 * Yaml数据格式。
	 */
	object Yaml : DataFormat {
		override val fileExtension: String = "yml"
		override val fileExtensions: Array<String> = arrayOf("yml", "data/yaml")
		override var serializer: YamlSerializer = defaultYamlSerializer
	}

	/**
	 * Xml数据格式。
	 */
	object Xml : DataFormat {
		override val fileExtension: String = "data/xml"
		override val fileExtensions: Array<String> = arrayOf(
			"data/xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan",
			"pom", "rng", "tld", "wadl", "wsdd", "wsdl", "xjb", "xsd", "xsl", "xslt", "xul"
		)
		override var serializer: XmlSerializer = defaultXmlSerializer
	}

	/**
	 * Properties数据格式。
	 */
	object Properties : DataFormat {
		override val fileExtension: String = "data/properties"
		override val fileExtensions: Array<String> = arrayOf("data/properties")
		override var serializer: PropertiesSerializer = defaultPropertiesSerializer
	}

	/**
	 * Csv数据格式。
	 */
	object Csv : DataFormat {
		override val fileExtension: String = "data/csv"
		override val fileExtensions: Array<String> = arrayOf("data/csv")
		override var serializer: CsvSerializer = defaultCsvSerializer
	}
	//endregion
}
