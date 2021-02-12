// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import java.lang.reflect.*

/**
 * 数据的读取器。
 */
internal interface DataReader{
	/**
	 * 读取数据。
	 */
	fun <T> read(value:String,type:Class<T>):T

	/**
	 * 读取数据。
	 */
	fun <T> read(value:String,type: Type):T
}
