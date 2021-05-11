// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*

/**
 * 数据的写入器。
 */
internal interface DataWriter{
	/**
	 * 数据的配置。
	 */
	val config: DataConfig

	/**
	 * 写入数据。
	 */
	fun <T> write(target:T):String
}

