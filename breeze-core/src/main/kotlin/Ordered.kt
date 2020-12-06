// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core

/**
 * 可排序的对象。
 *
 * 按照[order]的值从小到大排列。
 *
 * @property order 顺序。
 */
interface Ordered:Comparable<Ordered> {
	val order:Int

	override fun compareTo(other: Ordered): Int {
		return other.order - order
	}
}
