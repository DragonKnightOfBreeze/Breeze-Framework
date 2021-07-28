// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 *
 * 同一兼容类型的转化器可以注册多个。
 *
 * @see Converters
 */
interface Converter<T> : Component {
	val targetType: Class<T>

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
	 */
	fun convert(value: Any): T

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
	 */
	fun convertOrNull(value: Any): T? {
		return runCatching { convert(value) }.getOrNull()
	}

	override fun copy(componentParams: Map<String, Any?>): Converter<T> {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}
