package com.windea.breezeframework.mapper

/**
 * 映射器。
 *
 * 映射器用于将数据映射为特定的数据格式，或者从特定的数据格式反映射为数据。
 */
interface Mapper {
	fun <T> map(data: T): String

	fun <T> unmap(string: String, type: Class<T>): T

	companion object {
		inline fun <reified T> mapObject(data: T): Map<*, *> {
			TODO()
		}

		inline fun <reified T> unmapObject(map: Map<*, *>): T {
			TODO()
		}
	}
}
