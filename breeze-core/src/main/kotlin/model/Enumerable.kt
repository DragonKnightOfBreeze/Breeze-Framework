// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

/**
 * 可枚举的对象。
 *
 * 此接口拥有两个属性，[code]用于数据库存储，[text]用于序列化。
 */
interface Enumerable<T : Serializable> : Serializable {
	/**
	 * 码值。
	 */
	val code: T

	/**
	 * 文本。
	 */
	val text: String
}
