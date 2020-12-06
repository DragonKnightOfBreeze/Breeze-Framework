// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

/**
 * 数据的写入器。
 */
internal interface DataWriter{
	/**
	 * 写入数据。
	 */
	fun <T> write(target:T):String
}

