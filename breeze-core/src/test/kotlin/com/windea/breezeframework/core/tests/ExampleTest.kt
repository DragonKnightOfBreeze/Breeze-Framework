package com.windea.breezeframework.core.tests

import com.windea.breezeframework.core.enums.text.*
import com.windea.breezeframework.core.enums.text.LetterCase.*
import com.windea.breezeframework.core.enums.text.ReferenceCase.*
import com.windea.breezeframework.core.extensions.*
import kotlin.test.*

class ExampleTest {
	@Test
	fun test() {
		//true
		println(arrayOf(1, 2, 3) anyIn arrayOf(3, 4, 5))
		//true
		println(arrayOf(3, 4, 5) anyIn arrayOf(3, 4, 5))

		//42
		println(listOf(1, 2, 3, listOf(41, 42)).deepGet<Int>("/3/1"))
		//[1, 2, 3, [41, 233]]
		println(mutableListOf(1, 2, 3, mutableListOf(41, 42)).also { it.deepSet("/3/1", 233) })
		//{/0/0=1, /1/0=2, /2/0=41}
		println(listOf(arrayOf(1), listOf(2), listOf(41, 42), mapOf("a" to 5)).deepQuery<Any>("/[list]/0"))
		//{/0/0=1, /1/0=2, /2/0=41, /2/1=42, /3/a=5}
		println(listOf(arrayOf(1), listOf(2), listOf(41, 42), mapOf("a" to 5)).deepFlatten())

		//{0=a, 1=b, 2=c}
		println(listOf("a", "b", "c").toIndexKeyMap())
		//{1=1, 2=2, 3=3}
		println(mapOf(1 to 1, 2 to 2, 3 to 3).toStringKeyMap())

		//[b, c]
		println(listOf("a", "b", "c")[1..2])
		//[a, b, c, a, b, c, a, b, c]
		println(listOf("a", "b", "c") * 3)
		//[[a, b, c], [a, b, c], [a, b, c]]
		println(listOf("a", "b", "c", "a", "b", "c", "a", "b", "c") / 3)

		//true
		println("Hello world" endsWith "world")
		//true
		println("Hello world" endsWithIgnoreCase "World")

		//1{0}2{1}3{2}
		println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })

		//bc
		println("abc"[1..2])
		//**********
		println("*" * 10)
		//[***, ***, ***]
		println("*********" / 3)

		//<element>Here also indented.</element>
		println("""
		  <element>
		    Here also indented.
		  </element>
		""".trimWrap())
		//  <element>
		//    Here also indented.
		//  </element>
		println("""
		  <element>
		    Here also indented.
		  </element>
		""".trimRelativeIndent())

		//abcAbc
		println("Abc abc".switchCaseBy(camelCase))
		//AbcAbc
		println("ABC_ABC".switchCaseBy(PascalCase))
		//abc-abc
		println("ABC_ABC".switchCaseBy(`kebab-case`))
		//abc_abc
		println("ABC_ABC".switchCaseBy(snake_case))

		///a/b/1/2/c/3
		println("a.b[1][2].c[3]".switchCaseBy(PathReference))
		//a.b[1][2].c[3]
		println("/a/b/1/2/c/3".switchCaseBy(JavaReference))

		//true
		println("/home/123/detail" matches "/home/*/detail".toRegexBy(MatchType.AntPath))
		//true
		println("/home/123/detail" matches "/home/**".toRegexBy(MatchType.AntPath))
		//true
		println("Test.kt" matches "*.kt".toRegexBy(MatchType.EditorConfigPath))
		//true
		println("Test.kt" matches "*.{kt, kts}".toRegexBy(MatchType.EditorConfigPath))
		//true
		println("/abc/123/def" matches "/abc/-/def".toRegexBy(MatchType.PathReference))
		//true
		println("/abc/123/def" matches "/{}/123/def".toRegexBy(MatchType.PathReference))
	}
}
