package com.windea.breezeframework.core.tests


class ReflectTests {
	//@Test
	//fun test1() {
	//	val json = """
	//		{"name":"Windea","age":"???"}
	//	""".trimIndent()
	//	//println(Gson().fromJson(json, Person::class.java))
	//	println(JsonMapper().readValue(json, Person::class.java))
	//}
}

@ModelB
data class Person(
	val name: String,
	val age: String
)

annotation class Model

@Model
annotation class ModelB
