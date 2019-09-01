package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.core.enums.*
import mu.*
import kotlin.reflect.full.*

private val logger = KotlinLogging.logger { }

//TODO

/**反序列化当前字符串，返回指定泛型的对象。*/
inline fun <reified T : Any> String.deserialize(dataType: DataType): T {
	return dataType.loader.fromString(this, T::class.java)
}

/**序列化当前对象。*/
fun <T : Any> T.serialize(dataType: DataType): String {
	return this.let { dataType.loader.toString(it) }
}


/**将当前映射转化为指定类型的对象。可指定是否递归转化[recursive]，默认为false。*/
inline fun <reified T> Map<String, Any?>.toObject(recursive: Boolean = false): T = this.toObject(T::class.java, recursive)

/**将当前映射转化为指定类型的对象。可指定是否递归转化[recursive]，默认为false。*/
@NotSuitable("不存在无参构造方法时，转化需要转化元素的数组时。")
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
			logger.warn("Property type mismatch. Class: ${type.name}, Name: $propertyName, Value: $propertyValue}.")
		}
	}
	return newObject
}

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


/**将当前对象转化为对应的成员属性名-属性值映射。可指定是否递归转化[recursive]，默认为false。*/
@NotSuitable("需要得到扩展属性信息时。")
fun <T : Any> T.toPropertyMap(recursive: Boolean = false): Map<String, Any?> {
	return this::class.memberProperties.associate { it.name to it.call(this) }.let { map ->
		if(recursive) {
			map.mapValues { (_, v) ->
				if(v != null && v !is Array<*> && v !is Iterable<*> && v !is Map<*, *>) {
					v.toPropertyMap(true)
				} else {
					v
				}
			}
		} else {
			map
		}
	}
}
