package com.windea.utility.common.enums

import com.windea.utility.common.loaders.*

/**数据类型。*/
enum class DataType(
	/**扩展名。*/
	val extension: String,
	/**对应的数据读取器。*/
	val loader: DataLoader
) {
	Json("json", JsonLoader.instance),
	Yaml("yml", YamlLoader.instance),
	Xml("xml", XmlLoader.instance),
	Properties("properties", PropertiesLoader.instance)
}
