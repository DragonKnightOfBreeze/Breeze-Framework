// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.mapper

import icu.windea.breezeframework.reflect.extension.*

/**
 * 对象的映射器。
 */
object ObjectMapper {
	/**将对象映射为基于可读属性的映射。*/
	fun <T : Any> map(data: T): Map<String, Any?> {
		//使用Java反射，映射第一层属性，不进行递归映射
		return data::class.java.getters.associateBy(
			{ it.name[3].toLowerCase() + it.name.substring(4) },
			{ it.invoke(data) }
		)
	}

	/**将映射反映射为指定类型的基于可读写属性的对象。*/
	inline fun <reified T : Any> unmap(map: Map<String, Any?>): T {
		return unmap(map, T::class.java)
	}

	/**将映射反映射为指定类型的基于可读写属性的对象。*/
	fun <T : Any> unmap(map: Map<String, Any?>, type: Class<T>): T {
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
