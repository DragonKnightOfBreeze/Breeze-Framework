// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("InternalExtensions")

package icu.windea.breezeframework.core.extension

import java.lang.reflect.*
import java.text.*
import java.util.*
import java.util.concurrent.*

//internal default values

internal const val defaultDateFormat = "yyyy-MM-dd"
internal const val defaultTimeFormat = "HH:mm:ss"
internal const val defaultDateTimeFormat = "$defaultDateFormat $defaultTimeFormat"
internal val defaultLocale = Locale.getDefault(Locale.Category.FORMAT)
internal val defaultTimeZone = TimeZone.getTimeZone("UTC")

//internal caches

internal val calendar: Calendar = Calendar.getInstance()
internal val threadLocalDateFormatMapCache: MutableMap<String, ThreadLocal<DateFormat>> = ConcurrentHashMap<String, ThreadLocal<DateFormat>>()
//internal val enumValuesCache:MutableMap<Class<out Enum<*>>,List<Enum<*>>> = ConcurrentHashMap()
//internal val enumValueMapCache:MutableMap<Class<out Enum<*>>,Map<String,Enum<*>>> = ConcurrentHashMap()

//internal string extensions

internal fun CharSequence.firstCharToUpperCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].toUpperCase() + this.substring(1)
	}
}

internal fun CharSequence.firstCharToLowerCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].toLowerCase() + this.substring(1)
	}
}

private val splitWordsRegex = """\B([A-Z][a-z])""".toRegex()

internal fun CharSequence.splitWords(): String {
	return this.replace(splitWordsRegex, " $1")
}

//internal component extensions

private val componentTargetTypeMapCache = ConcurrentHashMap<Class<*>,ConcurrentHashMap<Class<*>,Class<*>>>()

/**
 * 推断组件的目标类型。`IntConverter: Converter<Int> -> Int`
 */
@Suppress("UNCHECKED_CAST")
internal fun <T> inferComponentTargetType(type:Class<*>,componentType:Class<*>):Class<T>{
	val targetMap = componentTargetTypeMapCache.getOrPut(componentType) { ConcurrentHashMap() }
	return targetMap.getOrPut(type){
		var currentType:Type = type
		while(currentType != Any::class.java){
			val currentClass = when {
				currentType is Class<*> -> currentType
				currentType is ParameterizedType -> currentType.rawType as? Class<*> ?: error("Cannot infer target type for type '$type'.")
				else -> error("Cannot infer target type for type '$type'.")
			}
			val genericSuperClass = currentClass.genericSuperclass
			if(genericSuperClass is ParameterizedType && genericSuperClass.actualTypeArguments.isNotEmpty()){
				val genericType = genericSuperClass.actualTypeArguments[0]
				if(genericType is Class<*>) return@getOrPut genericType
				if(genericType is ParameterizedType){
					val genericTypeRawType = genericType.rawType
					if(genericTypeRawType is Class<*>) return@getOrPut genericTypeRawType
				}
			}
			val genericInterfaces = currentClass.genericInterfaces
			val genericInterface = genericInterfaces.find { it is ParameterizedType && (it.rawType as? Class<*>) == componentType }
			if(genericInterface is ParameterizedType && genericInterface.actualTypeArguments.isNotEmpty()){
				val genericType = genericInterface.actualTypeArguments[0]
				if(genericType is Class<*>) return@getOrPut genericType
				if(genericType is ParameterizedType){
					val genericTypeRawType = genericType.rawType
					if(genericTypeRawType is Class<*>) return@getOrPut genericTypeRawType
				}
			}
			currentType = genericSuperClass
		}
		error("Cannot infer target type for type '$type'.")
	} as Class<T>
}

internal fun optimizeConfigParams(configParams:Map<String,Any?>,vararg names:String):Map<String,Any?>{
	val result = mutableMapOf<String,Any?>()
	for(name in names) {
		val configParam = configParams.get(name)
		if(configParam != null) result.put(name,configParam)
	}
	return result
}
