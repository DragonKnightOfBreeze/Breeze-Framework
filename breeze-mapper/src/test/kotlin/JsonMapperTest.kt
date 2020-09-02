package com.windea.breezeframework.mapper

import com.windea.breezeframework.mapper.impl.*
import org.junit.*

class JsonMapperTest {
	@Test
	fun map() {
		val data = mapOf("a" to 1, "b" to "2", "c" to listOf(3, 4, arrayOf(5, 6), arrayOf(7)))
		JsonMapper().map(data).also {
			println(it)
		}
		println()
		JsonMapper(JsonMapper.Config.PrettyFormat).map(data).also {
			println(it)
		}
	}

	@Test
	fun unmap() {
		//val string = """{"a": 1, "b": "2", "c": [3, 4, [5, 6], [7]]}"""
		//JsonMapper().unmap(string,Any::class.java).also {
		//	println(it)
		//}
	}
}
