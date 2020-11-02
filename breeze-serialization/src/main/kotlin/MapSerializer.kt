// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.reflect.extensions.*

//TODO 完善

/**
 * 映射的序列化器。
 */
object MapSerializer {
	/**
	 * 序列化指定对象为映射。
	 */
	fun <T : Any> serialize(data: T): Map<String, Any?> {
		//使用Java反射，映射第一层属性，不进行递归映射
		return data::class.java.getters.associateBy(
			{ it.name[3].toLowerCase() + it.name.substring(4) },
			{ it.invoke(data) }
		)
	}

	/**
	 * 反序列化指定映射为对象。
	 */
	inline fun <reified T : Any> deserialize(map: Map<String, Any?>): T {
		return deserialize(map, T::class.java)
	}

	/**
	 * 反序列化指定映射为对象。
	 */
	fun <T : Any> deserialize(map: Map<String, Any?>, type: Class<T>): T {
		return runCatching {
			//存在无参构造时，使用Java反射，直接实例化对象
			val result = type.getConstructor().newInstance()
			//然后尝试根据名字对所有非final的字段赋值
			for((name, value) in map) {
				type.getDeclaredField(name).apply { trySetAccessible() }.set(result, value)
			}
			result
		}.getOrElse {
			//不存在无参构造时，使用Kotlin反射，尝试根据主构造方法实例化对象，并尝试根据名字对所有参数赋值
			val filteredMap = map.toMutableMap()
			val result = type.kotlin.constructors.first().let { c ->
				c.callBy(c.parameters.associateWith { filteredMap.remove(it.name);map[it.name] })
			}
			//然后再尝试对剩下的所有非final的字段赋值
			for((name, value) in filteredMap) {
				type.getDeclaredField(name).apply { trySetAccessible() }.set(result, value)
			}
			return result
		}
	}
}
