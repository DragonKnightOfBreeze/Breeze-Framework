package com.windea.breezeframework.mapper.impl

import com.windea.breezeframework.mapper.*
import kotlin.test.*

class MapperTest {
	@Test
	fun mapObjectTest() {
		Mapper.mapObject(Foo("a", 1)).also { println(it) }
		Mapper.mapObject(Bar("a", 1, Any())).also { println(it) }
	}

	@Test
	fun unmapObjectTest() {
		Mapper.unmapObject<Foo>(mapOf("name" to "a", "age" to 1)).also { println(it) }
		Mapper.unmapObject<Foo>(mapOf("age" to 1, "name" to "a")).also { println(it) }
		Mapper.unmapObject<Foo>(mapOf()).also { println(it) }
		Mapper.unmapObject<Bar>(mapOf("name" to "a", "age" to 1, "weapon" to Any())).also { println(it) }
		Mapper.unmapObject<Bar>(mapOf("age" to 1, "name" to "a", "weapon" to Any())).also { println(it) }
		Mapper.unmapObject<Bar>(mapOf("age" to 1, "name" to "a", "weapon" to Any(), "height" to 190)).also { println(it.height) }
		assertFails {
			Mapper.unmapObject<Bar>(mapOf()).also { println(it) }
		}
	}

	data class Foo(
		val name: String = "abc",
		val age: Int = 123
	)

	data class Bar(
		val name: String,
		val age: Int,
		val weapon: Any
	) {
		val height: Int = 170
	}
}
