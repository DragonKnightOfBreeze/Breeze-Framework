// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import java.io.*

/**
 * 可枚举的对象。
 *
 * 此接口拥有两个属性，[code]用于数据库存储，[text]用于序列化。
 *
 * @property code 码值。
 * @property text 文本。
 */
interface Enumerable<T : Serializable> : Serializable {
	val code: T

	val text: String
}
