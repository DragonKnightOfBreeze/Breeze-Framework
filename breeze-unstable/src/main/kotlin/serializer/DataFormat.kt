// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serializer

import icu.windea.breezeframework.serializer.impl.json.*
import icu.windea.breezeframework.serializer.impl.properties.*
import icu.windea.breezeframework.serializer.impl.xml.*
import icu.windea.breezeframework.serializer.impl.yaml.*

/**数据格式。*/
enum class DataFormat(
	/**名字。*/
	val value: String,
	/**兼容的扩展名一览。*/
	val extensions: Array<String>, //参考自IDEA的Editor/File Types设置
	/**序列化器。*/
	val serializer: Serializer,
) {
	/**Json。*/
	Json(
		"data/json",
		arrayOf("data/json", "jsb2", "jsb3"),
		JsonSerializer.instance
	),

	/**Properties。*/
	Properties(
		"data/properties",
		arrayOf("data/properties", "scriptenginefactory"),
		PropertiesSerializer.instance
	),

	/**Xml。*/
	Xml(
		"data/xml",
		arrayOf("data/xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan", "pom", "rng", "tld", "wadl", "wsdd", "wsdl",
			"xjb", "xsd", "xsl", "xslt", "xul"),
		XmlSerializer.instance
	),

	/**Yaml。*/
	Yaml(
		"data/yaml",
		arrayOf("yml", "data/yaml"),
		YamlSerializer.instance
	)
}
