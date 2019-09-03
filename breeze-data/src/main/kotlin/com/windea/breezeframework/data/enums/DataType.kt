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
	val serializer: DataSerializer<*, *>
) {
	Json("json", GsonJsonSerializer()),
	Yaml("yml", SnakeYamlSerializer()),
	Xml("xml", JacksonXmlSerializer()),
	Properties("properties", JacksonPropertiesSerializer()),
	Csv("csv", JacksonCsvSerializer())
}
