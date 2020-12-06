// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("SerializationInternalExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.serialization.extension

import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.serialization.serializer.*
import java.util.concurrent.*

internal inline fun Char.appendTo(buffer: Appendable) = buffer.append(this)

internal inline fun String.appendTo(buffer: Appendable) = buffer.append(this)


private val executor by lazy { Executors.newWorkStealingPool(1024) }

@Suppress("UNCHECKED_CAST")
internal inline fun <T,reified R> Array<T>.mapToArrayAsync( crossinline transform:(T)->R):Array<R>{
	val threadLocal = ThreadLocal.withInitial { arrayOfNulls<R>(size) }
	val result = threadLocal.get()
	for((i, e) in this.withIndex()) {
		executor.submit { result[i] = transform(e) }.get()
	}
	return result as Array<R>
}

internal fun <T> Array<T>.joinToAsync(buffer:Appendable,separator:Char, transform:(T)->String): Appendable {
	val strings = this.mapToArrayAsync(transform)
	var appendSeparator = false
	for(string in strings) {
		if(appendSeparator) buffer.append(separator) else appendSeparator = true
		buffer.append(string)
	}
	return buffer
}

internal fun <T> Array<T>.joinToAsync(buffer:Appendable,separator:String, transform:(T)->String): Appendable {
	val strings = this.mapToArrayAsync(transform)
	var appendSeparator = false
	for(string in strings) {
		if(appendSeparator) buffer.append(separator) else appendSeparator = true
		buffer.append(string)
	}
	return buffer
}


private const val kotlinxJsonClassName = "kotlinx.serialization.json.Json"
private const val jacksonJsonClassName = "com.fasterxml.jackson.databind.json.JsonMapper"
private const val gsonClassName = "com.google.gson.Gson"
private const val fastjsonClassName = "com.alibaba.fastjson.JSON"

internal val defaultJsonSerializer: JsonSerializer = when {
	presentInClassPath(jacksonJsonClassName) -> JacksonJsonSerializer()
	presentInClassPath(kotlinxJsonClassName) -> KotlinxJsonSerializer()
	presentInClassPath(gsonClassName) -> GsonSerializer()
	presentInClassPath(fastjsonClassName) -> FastJsonSerializer()
	else -> BreezeJsonSerializer()
}

private const val jacksonYamlClassName = "com.fasterxml.jackson.dataformat.yaml.YAMLMapper"
private const val snackYamlClassName = "org.yaml.snakeyaml.Yaml"

internal val defaultYamlSerializer: YamlSerializer = when {
	presentInClassPath(jacksonYamlClassName) -> JacksonYamlSerializer()
	presentInClassPath(snackYamlClassName) -> SnakeYamlSerializer()
	else -> BreezeYamlSerializer()
}

private const val jacksonXmlClassName = "com.fasterxml.jackson.dataformat.xml.XmlMapper"

internal val defaultXmlSerializer: XmlSerializer = when {
	presentInClassPath(jacksonXmlClassName) -> JacksonXmlSerializer()
	else -> BreezeXmlSerializer()
}

private const val jacksonPropertiesClassName = "com.fasterxml.jackson.dataformat.javaprop.JavaPropsMapper"

internal val defaultPropertiesSerializer: PropertiesSerializer = when {
	presentInClassPath(jacksonPropertiesClassName) -> JacksonPropertiesSerializer()
	else -> BreezePropertiesSerializer()
}

internal val defaultCsvSerializer: CsvSerializer = BreezeCsvSerializer()

