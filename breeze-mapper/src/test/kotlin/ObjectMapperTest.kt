package com.windea.breezeframework.mapper

import kotlin.test.*

class ObjectMapperTest {
	@Test
	fun mapObjectTest() {
		ObjectMapper.map(Foo("a", 1)).also { println(it) }
		ObjectMapper.map(Bar("a", 1, Any())).also { println(it) }
	}

	@Test
	fun unmapObjectTest() {
		ObjectMapper.unmap<Foo>(mapOf("name" to "a", "age" to 1)).also { println(it) }
		ObjectMapper.unmap<Foo>(mapOf("age" to 1, "name" to "a")).also { println(it) }
		ObjectMapper.unmap<Foo>(mapOf()).also { println(it) }
		ObjectMapper.unmap<Bar>(mapOf("name" to "a", "age" to 1, "weapon" to Any())).also { println(it) }
		ObjectMapper.unmap<Bar>(mapOf("age" to 1, "name" to "a", "weapon" to Any())).also { println(it) }
		ObjectMapper.unmap<Bar>(mapOf("age" to 1, "name" to "a", "weapon" to Any(), "height" to 190)).also { println(it.height) }
		assertFails {
			ObjectMapper.unmap<Bar>(mapOf()).also { println(it) }
		}
	}

	data class Foo(
		val name: String = "abc",
		val age: Int = 123,
	)

	data class Bar(
		val name: String,
		val age: Int,
		val weapon: Any,
	) {
		val height: Int = 170
	}
}
