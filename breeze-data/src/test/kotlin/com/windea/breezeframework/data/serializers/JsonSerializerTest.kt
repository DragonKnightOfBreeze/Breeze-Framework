package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.extensions.*
import kotlin.reflect.*
import kotlin.test.*

class JsonSerializerTest {
	@Test //TESTED NO PROBLEM
	fun testForString() {
		val json = """
			{"name":"Windea","age":"5000+"}
		""".trimIndent()

		val jsonObj = json.deserialize<Person>(DataType.Json)
		println(jsonObj)

		val jsonMap = json.deserialize<Map<*, *>>(DataType.Json)
		println(jsonMap)

		val jsonString1 = jsonObj.serialize(DataType.Json)
		println(jsonString1)

		val jsonString2 = jsonMap.serialize(DataType.Json)
		println(jsonString2)
	}

	@Test //TESTED PRETTY PRINTED
	fun testForFile() {
		val jsonFile = javaClass.getResource("/test.json").toFile()

		val jsonObj = jsonFile.deserialize<Person>(DataType.Json)
		println(jsonObj)

		val jsonMap = jsonFile.deserialize<Map<*, *>>(DataType.Json)
		println(jsonMap)

		val newFile1 = jsonFile.changeName("test2.json").also { it.createNewFile() }
		jsonObj.serialize(DataType.Json, newFile1)
		println(newFile1.readText())

		val newFile2 = jsonFile.changeName("test3.json").also { it.createNewFile() }
		jsonMap.serialize(DataType.Json, jsonFile)
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
