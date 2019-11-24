package com.windea.breezeframework.linq

import com.windea.breezeframework.linq.LinqImplementationType.*
import org.junit.*

class LinqTest {
	@Test
	fun test1() {
		val source = listOf("foo", "Bar", "FooBar", "abc", "DDD", "Windea", "BreezesLanding", "Maple", "Kotlin")
		
		val linq1 = from<String>() select { it.toLowerCase() } where { it.length <= 5 } orderBy { it.first() } limit 1..5
		println(source linq linq1)
		
		val linq2 = from<String>(Stream) select { it.toLowerCase() } where { it.length <= 5 } orderBy { it.first() } limit 1..5
		println(source linq linq2)
	}
}
