package com.windea.breezeframework.serializer.impl

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.serializer.*
import kotlin.reflect.*
import kotlin.test.*

class JsonSerializerTest {
	@Test //NO PROBLEM
	fun testForString() {
		val json = """
			{"name":"Windea","age":"5000+"}
		""".trimIndent()

		val jsonObj = json.deserialize<Person>(DataFormat.Json)
		println(jsonObj)

		val jsonMap = json.deserialize<Map<*, *>>(DataFormat.Json)
		println(jsonMap)

		val jsonString1 = jsonObj.serialize(DataFormat.Json)
		println(jsonString1)

		val jsonString2 = jsonMap.serialize(DataFormat.Json)
		println(jsonString2)
	}

	@Test //PRETTY PRINTED
	fun testForFile() {
		val jsonFile = javaClass.getResource("/test.json").toFile()

		val jsonObj = jsonFile.deserialize<Person>(DataFormat.Json)
		println(jsonObj)

		val jsonMap = jsonFile.deserialize<Map<*, *>>(DataFormat.Json)
		println(jsonMap)

		val newFile1 = jsonFile.resolveSibling("test2.json").also { it.createNewFile() }
		jsonObj.serialize(DataFormat.Json, newFile1)
		println(newFile1.readText())

		val newFile2 = jsonFile.resolveSibling("test3.json").also { it.createNewFile() }
		jsonMap.serialize(DataFormat.Json, jsonFile)
		println(newFile2.readText())
	}

	fun testForFileRaw() {

	}

	@Test
	fun testTypeOf() {
		println(typeOf<Int>())

		println(typeOf<List<String>>())

		println(typeOf<Map<*, *>>())

		println(getType<Int>())

		println(getType<List<String>>())

		println(getType<Map<*, *>>())

		println(getType2<Int>())

		println(getType2<List<String>>())

		println(getType2<Map<*, *>>())
	}

	inline fun <reified T> getType() = typeOf<T>()

	inline fun <reified T> getType2() = getType<T>()

	data class Person(val name: String, val age: String)

	data class Person2(val name: String, val age: String)
}
