package com.windea.breezeframework.mapper

import com.windea.breezeframework.reflect.extensions.*
import java.lang.reflect.*

/**
 * 映射器。
 *
 * 映射器用于将数据映射为特定的数据格式，或者从特定的数据格式反映射为数据。
 */
interface Mapper {
	/**将指定的数据映射为特定的数据格式。*/
	fun <T> map(data: T): String

	/**将字符串从特定的数据格式反映射为数据。*/
	fun <T> unmap(string: String, type: Class<T>): T

	/**将字符串从特定的数据格式反映射为数据。*/
	fun <T> unmap(string: String, type: Type): T

	companion object {
		//kotlinx.serialization.Mapper.map
		/**将对象映射为基于可读属性的映射。*/
		fun <T : Any> mapObject(data: T): Map<String, Any?> {
			//使用Java反射，映射第一层属性
			return data::class.java.getters.associateBy(
				{ it.name[3].toLowerCase() + it.name.substring(4) },
				{ it.invoke(data) }
			)
		}

		//kotlinx.serialization.Mapper.unmap
		/**将映射反映射为指定类型的基于可读写属性的对象。*/
		inline fun <reified T : Any> unmapObject(map: Map<String, Any?>): T {
			return unmapObject(map, T::class.java)
		}

		//kotlinx.serialization.Mapper.unmap
		/**将映射反映射为指定类型的基于可读写属性的对象。*/
		fun <T : Any> unmapObject(map: Map<String, Any?>, type: Class<T>): T {
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
}
