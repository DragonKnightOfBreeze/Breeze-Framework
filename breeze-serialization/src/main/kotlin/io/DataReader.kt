// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.io

import icu.windea.breezeframework.serialization.config.*
import java.lang.reflect.*

/**
 * 数据的读取器。
 */
internal interface DataReader{
	/**
	 * 数据的配置。
	 */
	val config: DataConfig

	/**
	 * 读取数据。
	 */
	fun <T> read(value:String,type:Class<T>):T

	/**
	 * 读取数据。
	 */
	fun <T> read(value:String,type: Type):T
}
