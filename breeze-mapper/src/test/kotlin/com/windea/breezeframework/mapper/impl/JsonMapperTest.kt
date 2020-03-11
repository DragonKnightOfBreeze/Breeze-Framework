package com.windea.breezeframework.mapper.impl

import org.junit.*

class JsonMapperTest {

	@Test
	fun map() {
		val data = mapOf("a" to 1, "b" to "2", "c" to listOf(3, 4, arrayOf(5, 6), arrayOf(7)))
		JsonMapper().map(data).also { println(it) }
		JsonMapper(JsonMapper.Config(uglyFormat = true)).map(data).also { println(it) }
		JsonMapper(JsonMapper.Config(prettyFormat = true)).map(data).also { println(it) }
	}

	@Test
	fun unmap() {
	}
}
