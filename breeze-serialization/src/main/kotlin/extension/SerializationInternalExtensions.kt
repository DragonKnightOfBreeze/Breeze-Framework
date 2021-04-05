// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationInternalExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serialization.extension

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serialization.serializer.*
import java.util.concurrent.*

//internal extensions

internal inline fun Char.appendTo(buffer: Appendable) = buffer.append(this)

internal inline fun String.appendTo(buffer: Appendable) = buffer.append(this)

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

