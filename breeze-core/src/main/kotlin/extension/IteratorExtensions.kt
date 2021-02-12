// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("IteratorExtensions")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*

/**
 * 遍历迭代器中的元素到指定条件处，或者返回null。
 */
@UnstableApi
fun <T> Iterator<T>.next(predicate: (current: T, prev: T?) -> Boolean): T? {
	var current: T
	var prev: T? = null
	while(this.hasNext()) {
		current = this.next()
		if(predicate(current, prev)) return current
		prev = current
	}
	return null
}
