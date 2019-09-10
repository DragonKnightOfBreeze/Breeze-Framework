package com.windea.breezeframework.data.enums

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.data.serializers.csv.*
import com.windea.breezeframework.data.serializers.json.*
import com.windea.breezeframework.data.serializers.properties.*
import com.windea.breezeframework.data.serializers.xml.*
import com.windea.breezeframework.data.serializers.yaml.*

/**数据类型。*/
enum class DataType(
	val extension: String,
	val serializer: Serializer
) {
	Json("json", JsonSerializer.instance),
	Yaml("yml", YamlSerializer.instance),
	Xml("xml", XmlSerializer.instance),
	Properties("properties", PropertiesSerializer.instance),
	Csv("csv", CsvSerializer.instance)
}
