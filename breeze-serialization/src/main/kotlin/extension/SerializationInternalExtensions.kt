// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationInternalExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serialization.extension

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serialization.component.*
import java.util.concurrent.*

internal inline fun Char.writeTo(writer: DataWriter) = writer.write(this)

internal inline fun String.writeTo(writer: DataWriter) = writer.write(this)


internal val defaultMapLikeSerializer: MapLikeSerializer = MapLikeSerializer.BreezeMapLikeSerializer()

private const val kotlinxJsonClassName = "kotlinx.serialization.json.Json"
private const val jacksonJsonClassName = "com.fasterxml.jackson.databind.json.JsonMapper"
private const val gsonClassName = "com.google.gson.Gson"
private const val fastjsonClassName = "com.alibaba.fastjson.JSON"

internal val defaultJsonSerializer: JsonSerializer = when {
	presentInClassPath(jacksonJsonClassName) -> JsonSerializer.JacksonJsonSerializer()
	presentInClassPath(kotlinxJsonClassName) -> JsonSerializer.KotlinxJsonSerializer()
	presentInClassPath(gsonClassName) -> JsonSerializer.GsonSerializer()
	presentInClassPath(fastjsonClassName) -> JsonSerializer.FastJsonSerializer()
	else -> JsonSerializer.BreezeJsonSerializer()
}

private const val jacksonYamlClassName = "com.fasterxml.jackson.dataformat.yaml.YAMLMapper"
private const val snackYamlClassName = "org.yaml.snakeyaml.Yaml"

internal val defaultYamlSerializer: YamlSerializer = when {
	presentInClassPath(jacksonYamlClassName) -> YamlSerializer.JacksonYamlSerializer()
	presentInClassPath(snackYamlClassName) -> YamlSerializer.SnakeYamlSerializer()
	else -> YamlSerializer.BreezeYamlSerializer()
}

private const val jacksonXmlClassName = "com.fasterxml.jackson.dataformat.xml.XmlMapper"

internal val defaultXmlSerializer: XmlSerializer = when {
	presentInClassPath(jacksonXmlClassName) -> XmlSerializer.JacksonXmlSerializer()
	else -> XmlSerializer.BreezeXmlSerializer()
}

private const val jacksonPropertiesClassName = "com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper"

internal val defaultPropertiesSerializer: PropertiesSerializer = when {
	presentInClassPath(jacksonPropertiesClassName) -> PropertiesSerializer.JacksonPropertiesSerializer()
	else -> PropertiesSerializer.BreezePropertiesSerializer()
}

internal val defaultCsvSerializer: CsvSerializer = CsvSerializer.BreezeCsvSerializer()

internal val defaultTsvSerializer: TsvSerializer = TsvSerializer.BreezeTsvSerializer()
