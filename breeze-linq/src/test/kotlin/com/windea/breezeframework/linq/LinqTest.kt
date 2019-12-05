package com.windea.breezeframework.linq

import com.windea.breezeframework.linq.LinqImplementationType.*
import org.junit.*
import java.util.stream.*

class LinqTest {
	@Test
	fun test1() {
		val source = listOf("foo", "Bar", "FooBar", "abc", "DDD", "Windea", "BreezesLanding", "Maple", "Kotlin")
		
		val linq1 = from<String>() where { it.length <= 5 } orderBy { it.first() } limit 1..5 select { it.toLowerCase() }
		println(source linq linq1)
		
		val linq2 = from<String>(ByStream) where { it.length <= 5 } orderBy { it.first() } limit 1..5 select { it.toLowerCase() }
		println(source linq linq2)
	}
	
	@Test
	fun test2() {
		val source = listOf("foo", "Bar", "FooBar", "abc", "DDD", "Windea", "BreezesLanding", "Maple", "Kotlin")
		val linq = from<String>() where { it.length <= 5 } limit 1..5 orderBy { it.first() } select { it.toLowerCase() }
		println(source linq linq)
	}
	
	@Test
	fun test3() {
		println(Stream.concat(Stream.of(1, 2, 3), Stream.of(2, 3, 4)).collect(Collectors.toList()).toList())
	}
}
