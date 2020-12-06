// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.reflect.extension.*
import java.lang.reflect.*
import kotlin.jvm.internal.*
import kotlin.reflect.*

/**
 * 类映射对象的序列化器。
 *
 * 用于类映射对象和映射之间的相互转化。
 */
@BreezeComponent
interface MapLikeSerializer : Serializer<Map<String, Any?>> {
	/**
	 * 默认的类映射对象的序列化器。
	 */
	@Suppress("UNCHECKED_CAST")
	companion object Default : MapLikeSerializer {
		/**
		 * 序列化指定对象为映射。
		 */
		override fun <T> serialize(target: T): Map<String, Any?> {
			if(target == null) return emptyMap()
			//使用Java反射，映射第一层属性，不进行递归映射
			return target.javaClass.getters.associateBy(
				{ it.name[3].toLowerCase() + it.name.substring(4) },
				{ it.invoke(target) }
			)
		}

		/**
		 * 反序列化指定映射为对象。
		 */
		override fun <T> deserialize(value: Map<String, Any?>, type: Class<T>): T {
			return runCatching {
				//存在无参构造时，使用Java反射，直接实例化对象
				val result = type.getConstructor().newInstance()
				//然后尝试根据名字对所有非final的字段赋值
				for((n, v) in value) {
					type.getDeclaredField(n).apply { trySetAccessible() }.set(result, v)
				}
				result
			}.getOrElse {
				//不存在无参构造时，使用Kotlin反射，尝试根据主构造方法实例化对象，并尝试根据名字对所有参数赋值
				val filteredMap = value.toMutableMap()
				val kClass = Reflection.getOrCreateKotlinClass(type) as KClass<*>
				val result = kClass.constructors.first().let { c ->
					c.callBy(c.parameters.associateWith { filteredMap.remove(it.name);value[it.name] })
				}
				//然后再尝试对剩下的所有非final的字段赋值
				for((n, v) in filteredMap) {
					type.getDeclaredField(n).apply { trySetAccessible() }.set(result, v)
				}
				return result as T
			}
		}

		/**
		 * 反序列化指定映射为对象。
		 */
		override fun <T> deserialize(value: Map<String, Any?>, type: Type): T {
			return deserialize(value, type.erasedType) as T
		}
	}
}
