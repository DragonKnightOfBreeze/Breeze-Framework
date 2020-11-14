package com.windea.breezeframework.core

import com.windea.breezeframework.core.components.CaseType.*
import com.windea.breezeframework.core.extensions.*
import kotlin.test.*

class ExampleTest {
	@Test
	fun test() {
		////Generic Collection Extensions

		//true
		println(arrayOf(1, 2, 3) anyIn arrayOf(3, 4, 5))
		//true
		println(arrayOf(3, 4, 5) anyIn arrayOf(3, 4, 5))

		//{/0/0=1, /1/0=2, /2/0=41, /2/1=42, /3/a=5}
		println(listOf(arrayOf(1), listOf(2), listOf(41, 42), mapOf("a" to 5)).deepFlatten<Any>())

		////Collection Covert Extensions

		//{0=a, 1=b, 2=c}
		println(listOf("a", "b", "c").toIndexKeyMap())
		//{1=1, 2=2, 3=3}
		println(mapOf(1 to 1, 2 to 2, 3 to 3).toStringKeyMap())

		////Collection Operator Override Extensions

		//[b, c]
		println(listOf("a", "b", "c")[1..2])
		//[a, b, c, a, b, c, a, b, c]
		println(listOf("a", "b", "c") * 3)
		//[[a, b, c], [a, b, c], [a, b, c]]
		println(listOf("a", "b", "c", "a", "b", "c", "a", "b", "c") / 3)

		////Generic String extensions

		//1{0}2{1}3{2}
		println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })

		////String Operator Override Extensions

		//bc
		println("abc"[1..2])
		//**********
		println("*" * 10)
		//[***, ***, ***]
		println("*********" / 3)

		////String Output Extensions

		//<element>Here also indented.</element>
		println("""
		  <element>
		    Here also indented.
		  </element>
		""".trimWrap())
		// <element>
		//   Here also indented.
		// </element>
		println("""
		  <element>
		    Here also indented.
		  </element>
		""".trimRelativeIndent())

		////Switch DisplayCase Extensions

		//abcAbc
		println("Abc abc".switchCaseBy(CamelCase))
		//AbcAbc
		println("ABC_ABC".switchCaseBy(PascalCase))
		//abc-abc
		println("ABC_ABC".switchCaseBy(KebabCase))
		//abc_abc
		println("ABC_ABC".switchCaseBy(SnakeCase))

		////String Convert Extensions

		println("UTF-8".toCharset())

		println("java.lang.String".toClass())
	}
}
