// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 构建器。
 */
interface Builder<T> {
	/**
	 * 构建对应类型的对象。
	 */
	fun build(): T
}
