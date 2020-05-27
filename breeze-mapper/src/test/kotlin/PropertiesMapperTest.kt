package com.windea.breezeframework.mapper

import com.windea.breezeframework.mapper.impl.*
import org.junit.*

class PropertiesMapperTest {
	@Test
	fun map() {
		val data = mapOf("a" to 1, "b" to 2, "c" to arrayOf(3, 4, 5))
		PropertiesMapper().map(data).also { println(it) }

		println()

		val data2 = listOf("foo", "bar")
		PropertiesMapper().map(data2).also { println(it) }
	}

	@Test
	fun unmap() {
	}
}
