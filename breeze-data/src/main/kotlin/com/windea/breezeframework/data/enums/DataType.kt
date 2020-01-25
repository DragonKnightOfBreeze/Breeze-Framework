package com.windea.breezeframework.data.enums

import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.data.serializers.json.*
import com.windea.breezeframework.data.serializers.properties.*
import com.windea.breezeframework.data.serializers.xml.*
import com.windea.breezeframework.data.serializers.yaml.*

/**数据类型。*/
enum class DataType(
	val extension: String,
	val serializer: Serializer
) {
	/**Json。*/
	Json("json", JsonSerializer.instance),
	/**Properties。*/
	Properties("properties", PropertiesSerializer.instance),
	/**Xml。*/
	Xml("xml", XmlSerializer.instance),
	/**Yaml.*/
	Yaml("yml", YamlSerializer.instance)
}
