@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.data.extensions

import com.windea.breezeframework.core.annotations.api.*
import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.serializers.*
import com.windea.breezeframework.reflect.extensions.java.*
import java.io.*
import kotlin.reflect.full.*

//REGION serialize extensions

/**序列化当前对象，返回序列化后的字符串。*/
inline fun <T> T.serialize(dataType: DataType): String {
	return dataType.serializer.dump(this)
}

/**序列化当前对象，将序列化后的字符串写入指定文件。*/
inline fun <T> T.serialize(dataType: DataType, file: File) {
	dataType.serializer.dump(this, file)
}

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T> String.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this)
}

/**反序列化当前文件中文本，返回指定泛型的对象。*/
inline fun <reified T> File.deserialize(dataType: DataType): T {
	return dataType.serializer.load(this)
}

//REGION object and property map extensions

/**将当前对象转化为对应的成员属性名-属性值映射。可指定是否递归转化，默认为false。*/
@Deprecated("使用'kotlinx-serialization'的'Mapper.map()'。", ReplaceWith("kotlinx.serialization.Mapper.map<T>(this)"))
@LowPerformanceApi
@Suppress("DEPRECATION")
fun <T : Any> T.toPropertyMap(recursive: Boolean = false): Map<String, Any?> {
	return this::class.memberProperties.associate { it.name to it.call(this) }.let { map ->
		when {
			recursive -> map.mapValues { (_, v) ->
				when {
					v == null || v is Array<*> || v is Iterable<*> || v is Map<*, *> || v is Sequence<*> -> v
					else -> v.toPropertyMap(true)
				}
			}
			else -> map
		}
	}
}

/**将当前映射转化为指定类型的对象。可指定是否递归转化，默认为false。*/
@Deprecated("使用'kotlinx-serialization'的'Mapper.unmapNullable()'。", ReplaceWith("kotlinx.serialization.Mapper.unmapNullable<T>(this)"))
@LowPerformanceApi
@Suppress("DEPRECATION")
inline fun <reified T> Map<String, Any?>.toObject(recursive: Boolean = false): T {
	return toObject(T::class.java, recursive)
}

/**将当前映射转化为指定类型的对象。可指定是否递归转化，默认为false。*/
@Deprecated("使用具象化泛型。", ReplaceWith("this.toObject<T>(recursive)"))
@LowPerformanceApi
fun <T> Map<String, Any?>.toObject(type: Class<T>, recursive: Boolean = false): T {
	val newObject = type.getConstructor().newInstance()
	val propertyMap = type.setterMap
	for((propertyName, setMethod) in propertyMap) {
		if(!propertyMap.containsKey(propertyName)) {
			continue
		}
		val propertyValue = this[propertyName]
		try {
			val propertyType = type.getDeclaredField(propertyName).type
			val fixedPropertyValue = convertProperty(propertyType, propertyValue, recursive)
			setMethod.invoke(newObject, fixedPropertyValue)
		} catch(e: Exception) {
			throw IllegalArgumentException("Property type mismatch. Class: ${type.name}, Name: $propertyName, Value: $propertyValue}.")
		}
	}
	return newObject
}

@Suppress("DEPRECATION")
private fun convertProperty(propertyType: Class<*>, propertyValue: Any?, recursive: Boolean = false): Any? {
	return when {
		propertyType.isPrimitive || propertyType.isCharSequence -> propertyValue
		propertyType.isEnum -> propertyValue.toString().toEnumValue(propertyType)
		//使用高阶函数后，无法直接得到运行时泛型
		propertyType.isArray -> (propertyValue as Array<*>)
		propertyType.isList -> (propertyValue as List<*>).map {
			it?.let { convertProperty(it.javaClass, it, recursive) }
		}
		propertyType.isSet -> (propertyValue as Set<*>).map {
			it?.let { convertProperty(it.javaClass, it, recursive) }
		}.toSet()
		propertyType.isMap -> (propertyValue as Map<*, *>).mapValues { (_, v) ->
			v?.let { convertProperty(v.javaClass, v, recursive) }
		}
		propertyType.isSerializable && recursive -> {
			(propertyValue as Map<*, *>).toStringKeyMap().toObject(propertyType)
		}
		else -> null
	}
}
