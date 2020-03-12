package com.windea.breezeframework.dsl.json

import org.junit.*

class JsonDslTest {
	@Test
	fun test1() {
		json {
			listOf(
				"a",
				listOf("b1", "b2"),
				mapOf("c1" to "c1", "c2" to "c2")
			)
		}.also { println(it) }
	}

	@Test
	fun test2() {
		jsonTree {
			jsonArrayOf(
				jsonString("a"),
				jsonArrayOf(jsonString("b1"), jsonString("b2")),
				jsonObjectOf("c1" to jsonString("c1"), "c2" to jsonString("c2"))
			)
		}.also { println(it) }
	}

	@Test
	fun test3() {
		jsonTree {
			jsonArrayOf(
				"a".map(),
				jsonArrayOf("b1".map(), "b2".map()),
				jsonObjectOf("c1" to "c1".map(), "c2" to "c2".map())
			)
		}.also { println(it) }
	}

	@Test
	fun test4() {
		JsonConfig.prettyPrint = false
		json {
			listOf(
				"a",
				listOf("b1", "b2"),
				mapOf("c1" to "c1", "c2" to "c2")
			)
		}.also { println(it) }
	}

	@Test
	fun test5() {
		json {
			listOf(
				"a",
				listOf("b1", "b2"),
				mapOf("c1" to "c1", "c2" to "c2"),
				null,
				123,
				true
			)
		}.also { println(it) }
	}
}
