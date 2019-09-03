package com.windea.breezeframework.data.serializers.properties

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.*
import java.util.*

interface PropertiesSerializer<S, C> : DataSerializer<S, C>, PropertiesLoader, PropertiesDumper {
	override val dataType: DataType get() = DataType.Properties
	
	companion object {
		val instance: PropertiesSerializer<*, *> by lazy {
			when {
				checkClassForName("com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper") -> JacksonPropertiesSerializer()
				else -> throw IllegalStateException("Please contains at least one data serializer impl in classpath.")
			}
		}
	}
}


interface PropertiesLoader : DataLoader {
	override val dataType: DataType get() = DataType.Properties
	
	fun <T> load(properties: Properties, type: Class<T>): T
	
	fun loadAsList(properties: Properties): List<Any?> = load(properties, List::class.java)
	
	fun loadAsMap(properties: Properties): Map<String, Any?> = load(properties, Map::class.java).toStringKeyMap()
}


interface PropertiesDumper : DataDumper {
	override val dataType: DataType get() = DataType.Properties
	
	fun <T : Any> dump(data: T, properties: Properties)
}
