package com.windea.breezeframework.data.serializers

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.enums.*
import com.windea.breezeframework.data.extensions.*
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
	
	data class Person(val name: String, val age: String)
}
