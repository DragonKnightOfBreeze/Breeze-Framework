package com.windea.breezeframework.core.tests

import com.windea.breezeframework.core.enums.core.LetterCase.*
import com.windea.breezeframework.core.enums.core.ReferenceCase.*
import com.windea.breezeframework.core.extensions.*
import java.time.*
import kotlin.test.*

class Tests {
	@Test
	fun test() {
		//[, , , abc, , abc]
		println("___abc__abc".split("_").map { s -> s.trim('_') })
	}
	
	@Test
	fun test2() {
		val props = System.getProperties()
		for(prop in props) {
			println(prop)
		}
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
		println("1abc2def3".substrings("""\d(\w*)\d(\w*)\d""".toRegex()))
		//1{0}2{1}3{2}
		println("1{}2{}3{}".replaceIndexed("{}") { "{$it}" })
		//**********
		println("*" * 10)
		//[***, ***, ***]
		println("*********" / 3)
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
		//ABC_ABC
		println("abc-abc".switchCaseBy(SCREAMING_SNAKE_CASE))
		//a.b[1][2].c[3]
		println("/a/b/1/2/c/3".switchCaseBy(Standard))
	}
	
	@Test
	fun testRange() {
		(1..10).forEach(::println)
	}
	
	//@Test
	//fun testIntToChar(){
	//	println(0.toChar())
	//	println(25.toChar())
	//}
	
	@Test
	fun testToString() {
		println(LocalDate.parse("2014-01-06")) //2014-01-06 OK
		//PTnHnMnS
		println(Duration.ofDays(1)) //PT24H
		println(Duration.ofHours(20)) //PT20H
	}
	
	@Test
	fun testCollectionSize() {
		val foo1 = Foo1()
		println(foo1.size)
		foo1.list += 3
		println(foo1.size)
	}
	
	fun testAB() {
		a {
			testA()
			b {
				testA()
			}
		}
	}
	
	//fun testSwap() {
	//	val a = 1
	//	val b = 2
	//	var (c, d) = Pair(a, b)
	//	var e = 1
	//	var f = 2
	//	(e,f) = Pair(a,b)
	//	(a,b) = (b,a)
	//}
}

class Foo1 {
	val list = mutableListOf<Int>(1, 2)
	val size = list.size
}

fun a(builder: A.() -> Unit) = A().builder()

class A {
	fun b(builder: B.() -> Unit) = B().builder()
	fun testA() {}
}

class B {
	fun testB() {}
}
