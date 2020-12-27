// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 拥有名字的对象。
 *
 * @property name 名字。
 */
interface Named<T : CharSequence> {
	val name: T
}
