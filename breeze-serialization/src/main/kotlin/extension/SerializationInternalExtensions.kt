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

