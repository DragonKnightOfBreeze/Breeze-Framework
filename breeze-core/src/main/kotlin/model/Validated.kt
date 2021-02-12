// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.model

/**
 * 可验证的对象。
 */
interface Validated {
	/**
	 * 验证对象是否合法。
	 */
	fun validate(): Boolean {
		return true
	}
}
