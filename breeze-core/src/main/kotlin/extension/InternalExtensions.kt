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


//internal val genericTypesCache = ConcurrentHashMap<Class<*>,ConcurrentHashMap<Class<*>,Class<*>>>()
//
//@Suppress("UNCHECKED_CAST")
//internal fun <T> inferGenericType(target:Any,interfaceType:Class<*>):Class<T>{
//	val targetMap = genericTypesCache.getOrPut(interfaceType) { ConcurrentHashMap() }
//	val targetType = target::class.javaObjectType
//	return targetMap.getOrPut(targetType){
//		val genericInterfaces = targetType.genericInterfaces
//		//TODO 递归查找
//		val genericInterface = genericInterfaces.find {
//			it is ParameterizedType && it.rawType == interfaceType
//		} as? ParameterizedType ?: throw error("Framework internal error.")
//		val genericTypes = genericInterface.actualTypeArguments
//		if(genericTypes.isEmpty()) throw error("Framework internal error.")
//		val genericType = genericTypes[0]
//		if(genericType !is Class<*>) throw error("Framework internal error.")
//		genericType
//	} as Class<T>
//}

