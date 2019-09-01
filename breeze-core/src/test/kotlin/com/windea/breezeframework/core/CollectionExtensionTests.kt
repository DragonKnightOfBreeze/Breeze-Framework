package com.windea.breezeframework.core

import com.windea.breezeframework.core.enums.*
import com.windea.breezeframework.core.extensions.*
import org.junit.*

class CollectionExtensionTests {
	//TESTED
	@Test
	fun test1() {
		val list1 = mutableListOf(1, 2, 3, 4).also { it.removeAllAt(1..2) }
		println(list1)
		
		val list2 = mutableListOf(1, 2, 3, 4).also { it.move(1, 3) }
		println(list2)
		
		val list3 = mutableListOf(1, 2, 3, 4, 5, 6).also { it.moveAll(1..3, 5) }
		println(list3)
	}
	
	@Test
	fun test2() {
		println(listOf(1) * 3)
		println(listOf(1, 1, 1) / 3)
		println(listOf(1) * -3)
		println(listOf(1) / -3)
	}
	
	@Test
	fun test3() {
		println(listOf(1, 2, 3)[1])
		println(listOf(1, 2, 3)[1..2])
		println("123"[1])
		println("123"[1..2])
	}
	
	@Test
	fun test4() {
		val list = mutableListOf(1, 2, 3, mutableListOf(41, 42, 43), 5)
		//TESTED
		println(list.deepGet("[3][1]"))
		//TESTED
		println(list.deepFlatten())
		println(list.deepFlatten(1))
		println(list.deepFlatten(2))
		//TESTED
		println(list.deepQuery("#/[]"))
		println(list.deepQuery("#/-"))
		println(list.deepQuery("#/{}"))
		println(list.deepQuery("#/1..2"))
		println(list.deepQuery("#/3"))
		println(list.deepQuery("#/3/2"))
		println(list.deepQuery("#/[]", PathCase.JsonPath))
		println(list.deepQuery("#/-", PathCase.JsonPath))
		println(list.deepQuery("#/{}", PathCase.JsonPath))
		println(list.deepQuery("#/1..2", PathCase.JsonPath))
		println(list.deepQuery("#/3", PathCase.JsonPath))
		println(list.deepQuery("#/3/2", PathCase.JsonPath))
	}
}


