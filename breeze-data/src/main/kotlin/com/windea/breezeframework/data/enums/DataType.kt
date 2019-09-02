package com.windea.breezeframework.data.enums

import com.windea.breezeframework.data.loaders.*

/**数据类型。*/
enum class DataType(
	val extension: String,
	val loader: DataLoader
) {
	Json("json", JsonLoader.instance),
	Yaml("yml", YamlLoader.instance),
	Xml("xml", XmlLoader.instance),
	Properties("properties", PropertiesLoader.instance),
	Csv("csv", TODO())
}
