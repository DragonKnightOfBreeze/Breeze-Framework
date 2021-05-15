// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("InternalExtensions")

package icu.windea.breezeframework.core.extension

import java.lang.reflect.*
import java.text.*
import java.util.*
import java.util.concurrent.*

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


internal const val defaultDateFormat = "yyyy-MM-dd"
internal const val defaultTimeFormat = "HH:mm:ss"
internal const val defaultDateTimeFormat = "$defaultDateFormat $defaultTimeFormat"
internal val defaultLocale = Locale.getDefault(Locale.Category.FORMAT)
internal val defaultTimeZone = TimeZone.getTimeZone("UTC")

internal val calendar: Calendar = Calendar.getInstance()
internal val threadLocalDateFormatMap = ConcurrentHashMap<String, ThreadLocal<DateFormat>>()


//internal fun Any.isNullLike():Boolean{
//	return when(this){
//		is Boolean -> !this
//		is Number -> toString().let{ it=="0" || it=="0.0" }
//		is CharSequence -> isEmpty()
//		is Array<*> -> isEmpty()
//		is Collection<*> -> isEmpty()
//		is Iterable<*> -> none()
//		is Sequence<*> -> none()
//		is Map<*,*> -> isEmpty()
//		else -> false
//	}
//}
//
//internal fun Any.isNotNullLike():Boolean{
//	return when(this){
//		is Boolean -> this
//		is Number -> toString().let{ it!="0" || it!="0.0" }
//		is CharSequence -> isNotEmpty()
//		is Array<*> -> isNotEmpty()
//		is Collection<*> -> isNotEmpty()
//		is Iterable<*> -> any()
//		is Sequence<*> -> any()
//		is Map<*,*> -> isNotEmpty()
//		else -> false
//	}
//}


private val targetTypeCache = ConcurrentHashMap<Class<*>,ConcurrentHashMap<Class<*>,Class<*>>>()

@Suppress("UNCHECKED_CAST")
internal fun <T> inferTargetType(target:Any,componentType:Class<*>):Class<T>{
	val targetMap = targetTypeCache.getOrPut(componentType) { ConcurrentHashMap() }
	val targetType = target::class.javaObjectType
	return targetMap.getOrPut(targetType){
		val genericSuperclass = targetType.genericSuperclass //AbstractComponent<T>
		if(genericSuperclass !is ParameterizedType) throw error("Cannot infer target type for target '$target'.")
		val genericTypes = genericSuperclass.actualTypeArguments
		if(genericTypes.isEmpty()) throw error("Cannot infer target type for target '$target'.")
		val genericType = genericTypes[0]
		if(genericType !is Class<*>) throw error("Cannot infer target type for target '$target'.")
		genericType
	} as Class<T>
}
//
////public inline fun <T> Array<out T>.find(predicate: (T) -> Boolean): T?
//@PublishedApi
//internal fun <T> Array<out T>.recursiveFind(flatSelector:(T)->Array<out T>,predicate:(T)->Boolean):T?{
//	val result = find(predicate)
//	if(result != null) return result
//	for(e in this){
//		val flatThis = flatSelector(e)
//		val flatResult = flatThis.recursiveFind(flatSelector, predicate)
//		if(flatResult != null) return flatResult
//	}
//	return null
//}
