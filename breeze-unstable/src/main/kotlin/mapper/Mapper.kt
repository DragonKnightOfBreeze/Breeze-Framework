// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.mapper

import java.lang.reflect.*

/**
 * 映射器。
 *
 * 映射器用于将数据映射为特定的数据格式，或者从特定的数据格式反映射为数据。
 */
interface Mapper {
	/**将指定的数据映射为特定的数据格式。*/
	fun <T> map(data: T): String

	/**将字符串从特定的数据格式反映射为数据。*/
	fun <T> unmap(string: String, type: Class<T>): T

	/**将字符串从特定的数据格式反映射为数据。*/
	fun <T> unmap(string: String, type: Type): T
}
