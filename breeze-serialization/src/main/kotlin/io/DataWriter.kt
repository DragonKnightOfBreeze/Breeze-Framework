// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.config.*

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

