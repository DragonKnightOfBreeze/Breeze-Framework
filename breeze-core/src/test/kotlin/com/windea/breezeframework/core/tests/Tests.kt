package com.windea.breezeframework.core.tests

import com.windea.breezeframework.core.enums.LetterCase.*
import com.windea.breezeframework.core.enums.ReferenceCase.*
import com.windea.breezeframework.core.extensions.*
import org.junit.*

class Tests {
	@Test
	fun test() {
		//[, , , abc, , abc]
		println("___abc__abc".split("_").map { s -> s.trim('_') })
	}
	
	@Test //TESTED
	fun example() {
		//true
		println(arrayOf(1, 2, 3) anyIn arrayOf(3, 4, 5))
		//{[0]=1, [1]=2, [2]=3, [3][0]=4, [3][1]=5, [4].a=6}
		println(listOf(1, 2, 3, listOf(4, 5), mapOf("a" to 6)).deepFlatten())
		//{0=a, 1=b, 2=c}
		println(listOf("a", "b", "c").toIndexKeyMap())
		//[a, b, c, a, b, c, a, b, c]
		println(listOf("a", "b", "c") * 3)
		//[b, c]
		println(listOf("a", "b", "c")[1..2])
		
		//true
		println("Hello world" endsWithIc "World")
		//[abc, def]
		println("1abc2def3".substrings("\\d(\\w*)\\d(\\w*)\\d".toRegex()))
		//1{0}2{1}3{2}
		println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })
		//**********
		println("*" * 10)
		//  <element>
		//    Here also indented.
		//  </element>
		println("""
		  <element>
		    Here also indented.
		  </element>
		""".toMultilineText())
		
		//abcAbc
		println("Abc abc".to(camelCase))
		//AbcAbc
		println("ABC_ABC".to(PascalCase))
		//ABC_ABC
		println("abc-abc".to(SCREAMING_SNAKE_CASE))
		//a.b[1][2].c[3]
		println("/a/b/1/2/c/3".to(StandardReference))
	}
}
