// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("InternalExtensions")

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.component.*
import java.lang.reflect.*
import java.math.*
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
internal val threadLocalDateFormatMapCache: MutableMap<String, ThreadLocal<DateFormat>> = ConcurrentHashMap()
internal val enumValuesCache: MutableMap<Class<out Enum<*>>, List<Enum<*>>> = ConcurrentHashMap()
internal val enumValueMapCache: MutableMap<Class<out Enum<*>>, Map<String, Enum<*>>> = ConcurrentHashMap()

//internal number extensions

internal fun BigInteger.toLongOrMax(): Long {
	return if(this >= BigInteger.valueOf(Long.MAX_VALUE)) Long.MAX_VALUE else this.toLong()
}

internal fun BigDecimal.toDoubleOrMax(): Double {
	return if(this >= BigDecimal.valueOf(Double.MAX_VALUE)) Double.MAX_VALUE else this.toDouble()
}

//internal string extensions

internal fun CharSequence.firstCharToUpperCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].uppercaseChar() + this.substring(1)
	}
}

internal fun CharSequence.firstCharToLowerCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].lowercaseChar() + this.substring(1)
	}
}

private val splitWordsRegex = """\B([A-Z][a-z])""".toRegex()

internal fun CharSequence.splitWords(): String {
	return this.replace(splitWordsRegex, " $1")
}

//internal convert extensions

internal fun Any?.convertToBooleanOrTrue(): Boolean {
	return this == null || (this == true || this.toString() == "true")
}

internal fun Any?.convertToBooleanOrFalse(): Boolean {
	return this != null && (this == true || this.toString() == "true")
}

internal fun Any?.convertToStringOrNull(): String? {
	return this?.toString()
}

internal fun <T> Collection<T>.convertToList(): List<T> {
	return when {
		size == 0 -> emptyList()
		size == 1 -> listOf(if(this is List) get(0) else iterator().next())
		this is List -> this
		else -> this.toMutableList()
	}
}

internal fun <T> Collection<T>.convertToMutableList(): MutableList<T> {
	return when {
		this is MutableList -> this
		else -> this.toMutableList()
	}
}

internal fun <T> Collection<T>.convertToSet(): Set<T> {
	return when {
		size == 0 -> emptySet()
		size == 1 -> setOf(if(this is List) get(0) else iterator().next())
		this is Set -> this
		else -> this.toMutableSet()
	}
}

internal fun <T> Collection<T>.convertToMutableSet(): MutableSet<T> {
	return when {
		this is MutableSet -> this
		else -> this.toMutableSet()
	}
}

//internal reflect extensions

internal fun inferClass(targetType: Type): Class<*> {
	return when {
		targetType is Class<*> -> targetType
		targetType is ParameterizedType -> targetType.rawType as? Class<*> ?: error("Cannot infer class for target type '$targetType'")
		targetType is WildcardType -> targetType.upperBounds?.firstOrNull() as? Class<*> ?: error("Cannot infer class for target type '$targetType'")
		else -> error("Cannot infer class for target type '$targetType'")
	}
}

@Suppress("UNCHECKED_CAST")
internal fun inferEnumClass(targetType: Type): Class<out Enum<*>> {
	return when {
		targetType is Class<*> && targetType.isEnum -> targetType as Class<out Enum<*>>
		else -> error("Cannot infer enum class for target type '$targetType'")
	}
}

internal fun inferTypeArgument(targetType: Type): Type {
	if(targetType is ParameterizedType) {
		return targetType.actualTypeArguments?.firstOrNull()
			?: error("Cannot infer class for target type '$targetType'")
	} else if(targetType is Class<*> && targetType.isArray) {
		return targetType.componentType
	}
	error("Cannot infer class for target type '$targetType'")
}

internal fun inferTypeArguments(targetType: Type, targetClass: Class<*>): Array<out Type> {
	if(targetType is ParameterizedType) {
		val rawType = targetType.rawType
		val rawClass = inferClass(rawType)
		if(rawClass == targetClass) return targetType.actualTypeArguments
			?: error("Target type '$targetType' should be a ParameterizedType of class '$targetClass'")
	} else if(targetType is Class<*> && targetType.isArray) {
		return arrayOf(targetType.componentType)
	}
	error("Target type '$targetType' should be a ParameterizedType of class '$targetClass'")
}

//internal component extensions

private val componentTargetTypeMapCache = ConcurrentHashMap<Class<*>, ConcurrentHashMap<Class<*>, Type>>()

internal fun inferComponentId(component: Component): String {
	return when {
		component.componentParams.isEmpty() -> component.javaClass.name
		else -> component.javaClass.name + '@' + component.componentParams.toString()
	}
}

@Suppress("UNCHECKED_CAST")
internal fun <T> inferComponentTargetClass(type: Class<*>, componentType: Class<*>): Class<T> {
	return inferComponentTargetType(type, componentType) as? Class<T> ?: error("Cannot infer target type for type '$type'.")
}

@Suppress("UNCHECKED_CAST")
internal fun inferComponentTargetType(type: Class<*>, componentType: Class<*>): Type {
	val targetMap = componentTargetTypeMapCache.getOrPut(componentType) { ConcurrentHashMap() }
	return targetMap.getOrPut(type) {
		var currentType: Type = type
		while(currentType != Any::class.java) {
			val currentClass = when {
				currentType is Class<*> -> currentType
				currentType is ParameterizedType -> currentType.rawType as? Class<*> ?: error("Cannot infer target type for type '$type'.")
				else -> error("Cannot infer target type for type '$type'.")
			}
			val genericSuperClass = currentClass.genericSuperclass
			if(genericSuperClass is ParameterizedType && genericSuperClass.actualTypeArguments.isNotEmpty()) {
				val genericType = genericSuperClass.actualTypeArguments[0]
				if(genericType is Class<*>) return@getOrPut genericType
				if(genericType is ParameterizedType) {
					return genericType.rawType
				}
			}
			val genericInterfaces = currentClass.genericInterfaces
			val genericInterface =
				genericInterfaces.find { it is ParameterizedType && (it.rawType as? Class<*>) == componentType }
			if(genericInterface is ParameterizedType && genericInterface.actualTypeArguments.isNotEmpty()) {
				val genericType = genericInterface.actualTypeArguments[0]
				if(genericType is Class<*>) return@getOrPut genericType
				if(genericType is ParameterizedType) {
					return genericType.rawType
				}
			}
			currentType = genericSuperClass
		}
		error("Cannot infer target type for type '$type'.")
	}
}

internal fun filterComponentParams(componentParams: Map<String, Any?>, vararg names: String): Map<String, Any?> {
	if(componentParams.isEmpty()) return emptyMap()
	val result = mutableMapOf<String, Any?>()
	for((name, configParam) in componentParams) {
		if(name in names) result.put(name, configParam)
	}
	return result
}

internal fun filterNotComponentParams(componentParams: Map<String, Any?>, vararg names: String): Map<String, Any?> {
	if(componentParams.isEmpty()) return emptyMap()
	val result = mutableMapOf<String, Any?>()
	for((name, configParam) in componentParams) {
		if(name !in names) result.put(name, configParam)
	}
	return result
}

internal fun componentEquals(component: Component, other: Any?): Boolean {
	if(component === other) return true
	if(other == null || component.javaClass != other.javaClass) return false
	other as Component
	return component.componentParams.toString() == other.componentParams.toString()
}

internal fun componentHashcode(component: Component): Int {
	return if(component.componentParams.isEmpty()) 0 else component.componentParams.toString().hashCode()
}

internal fun componentToString(component: Component): String {
	return inferComponentId(component)
}
