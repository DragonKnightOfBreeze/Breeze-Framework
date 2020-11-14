// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

import com.windea.breezeframework.core.annotations.*

/**
 * 可计数的类。
 *
 * 此接口的属性不应该在构造器中实现。
 * @property count 数量。
 * @property totalCount 最大数量。
 */
@UnstableApi
interface Countable<T> where T : Number, T : Comparable<T> {
	var count: T
	var totalCount: T

	/**优化数量，避免出现非法的值。*/
	fun optimizeCount() {
		count = count.coerceAtMost(totalCount)
	}
}
