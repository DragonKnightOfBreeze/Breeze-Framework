// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.serialization.csv.*
import icu.windea.breezeframework.serialization.json.*
import icu.windea.breezeframework.serialization.properties.*
import icu.windea.breezeframework.serialization.xml.*
import icu.windea.breezeframework.serialization.yaml.*

object DataFormats : ComponentRegistry<DataFormat>() {
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

	override fun registerDefault() {
		register(Json)
		register(Yaml)
		register(Xml)
		register(Properties)
		register(Csv)
	}
}
