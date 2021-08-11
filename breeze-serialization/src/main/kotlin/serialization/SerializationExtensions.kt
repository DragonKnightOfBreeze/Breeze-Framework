// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationExtensions")

package icu.windea.breezeframework.serialization

import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.serialization.csv.*
import icu.windea.breezeframework.serialization.json.*
import icu.windea.breezeframework.serialization.properties.*
import icu.windea.breezeframework.serialization.xml.*
import icu.windea.breezeframework.serialization.yaml.*
import java.lang.reflect.*

//common extensions

/**
 * 根据指定的序列化器，序列化当前对象。
 *
 * @see Serializer
 */
fun <T, V> T.serializeBy(serializer: Serializer<V>): V {
	return serializer.serialize(this)
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
inline fun <reified T, V> V.deserializeBy(serializer: Serializer<V>): T {
	return serializer.deserialize(this, javaTypeOf<T>())
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
fun <T, V> V.deserializeBy(serializer: Serializer<V>, type: Class<T>): T {
	return serializer.deserialize(this, type)
}

/**
 * 根据指定的序列化器，反序列化当前格式。
 *
 * @see Serializer
 */
fun <T, V> V.deserializeBy(serializer: Serializer<V>, type: Type): T {
	return serializer.deserialize(this, type)
}


/**
 * 根据指定的数据格式，序列化当前对象。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataFormat
 */
fun <T> T.serializeDataBy(dataFormat: DataFormat): String {
	return dataFormat.serialize(this)
}

/**
 * 根据指定的数据格式，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataFormat
 */
inline fun <reified T> String.deserializeDataBy(dataFormat: DataFormat): T {
	return dataFormat.deserialize(this, javaTypeOf<T>())
}

/**
 * 根据指定的数据格式，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataFormat
 */
fun <T> String.deserializeDataBy(dataFormat: DataFormat, type: Class<T>): T {
	return dataFormat.deserialize(this, type)
}

/**
 * 根据指定的数据格式，反序列化当前文本。
 *
 * 这个方法使用的序列化器可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
 *
 * @see DataFormat
 */
fun <T> String.deserializeDataBy(dataFormat: DataFormat, type: Type): T {
	return dataFormat.deserialize(this, type)
}

//default serializers

internal val defaultJsonSerializer: JsonSerializer = when {
	presentInClassPath("com.fasterxml.jackson.databind.json.JsonMapper") -> JacksonJsonSerializer()
	presentInClassPath("kotlinx.serialization.json.Json") -> KotlinxJsonSerializer()
	presentInClassPath("com.google.gson.Gson") -> GsonSerializer()
	presentInClassPath("com.alibaba.fastjson.JSON") -> FastJsonSerializer()
	else -> BreezeJsonSerializer()
}

internal val defaultYamlSerializer: YamlSerializer = when {
	presentInClassPath("com.fasterxml.jackson.dataformat.yaml.YAMLMapper") -> JacksonYamlSerializer()
	presentInClassPath("org.yaml.snakeyaml.Yaml") -> SnakeYamlSerializer()
	else -> BreezeYamlSerializer()
}

internal val defaultXmlSerializer: XmlSerializer = when {
	presentInClassPath("com.fasterxml.jackson.dataformat.xml.XmlMapper") -> JacksonXmlSerializer()
	else -> BreezeXmlSerializer()
}

internal val defaultPropertiesSerializer: PropertiesSerializer = when {
	presentInClassPath("com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper") -> JacksonPropertiesSerializer()
	else -> BreezePropertiesSerializer()
}

internal val defaultCsvSerializer: CsvSerializer = BreezeCsvSerializer()
