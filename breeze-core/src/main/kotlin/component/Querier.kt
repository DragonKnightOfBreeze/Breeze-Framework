// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

/**
 * 查询器。
 *
 * 查询器用于根据指定类型的查询对象，查询目标对象。
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
interface Querier<T : Any, R> : Component {
	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则抛出异常。
	 */
	fun query(value: Any, queryObject: T): R

	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则返回null。
	 */
	fun queryOrNull(value: Any, queryObject: T): R? {
		return runCatching { query(value, queryObject) }.getOrNull()
	}

	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则返回默认值。
	 */
	fun queryOrDefault(value: Any, queryObject: T, defaultValue: R): R? {
		return queryOrNull(value, queryObject) ?: defaultValue
	}

	/**
	 * 根据指定类型的查询对象，查询查询对象。如果查询失败，则返回默认值。
	 */
	fun queryOrElse(value: Any, queryObject: T, defaultValue: (Any, T) -> R): R? {
		return queryOrNull(value, queryObject) ?: defaultValue(value, queryObject)
	}
}
