// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer

import com.windea.breezeframework.serializer.impl.json.*
import com.windea.breezeframework.serializer.impl.properties.*
import com.windea.breezeframework.serializer.impl.xml.*
import com.windea.breezeframework.serializer.impl.yaml.*

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
		"json",
		arrayOf("json", "jsb2", "jsb3"),
		JsonSerializer.instance
	),

	/**Properties。*/
	Properties(
		"properties",
		arrayOf("properties", "scriptenginefactory"),
		PropertiesSerializer.instance
	),

	/**Xml。*/
	Xml(
		"xml",
		arrayOf("xml", "ant", "fxml", "jhm", "jnlp", "jrxml", "plan", "pom", "rng", "tld", "wadl", "wsdd", "wsdl",
			"xjb", "xsd", "xsl", "xslt", "xul"),
		XmlSerializer.instance
	),

	/**Yaml。*/
	Yaml(
		"yaml",
		arrayOf("yml", "yaml"),
		YamlSerializer.instance
	)
}
