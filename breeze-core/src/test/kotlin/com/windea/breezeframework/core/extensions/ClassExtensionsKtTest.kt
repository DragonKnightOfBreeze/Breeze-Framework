package com.windea.breezeframework.core.extensions

import org.junit.*
import org.junit.Assert.*
import java.io.*

class ClassExtensionsKtTest {
	@Test
	fun isCharSequence() {
		assertTrue(String::class.java.isCharSequence)
		assertTrue(CharSequence::class.java.isCharSequence)
	}
	
	@Test
	fun isString() {
		assertTrue(String::class.java.isString)
	}
	
	@Test
	fun isIterable() {
		assertTrue(List::class.java.isIterable)
		assertTrue(ArrayList::class.java.isIterable)
		assertTrue(Iterable::class.java.isIterable)
	}
	
	@Test
	fun isList() {
		assertTrue(ArrayList::class.java.isList)
		assertTrue(List::class.java.isList)
	}
	
	@Test
	fun isSet() {
		assertTrue(HashSet::class.java.isSet)
		assertTrue(Set::class.java.isSet)
	}
	
	@Test
	fun isMap() {
		println(Map::class.java) //java.util.Map
		println(Map::class.java.superclass) //null
		println(Map::class.java.interfaces.map { it.name }) //[]
		//[java.util.Map, java.lang.Cloneable, java.io.Serializable]
		println(HashMap::class.java.interfaces.map { it.name })
		println(Map::class.java.isAssignableFrom(Map::class.java))
		println(Map::class.java.isAssignableFrom(HashMap::class.java))
		assertTrue(Map::class.java.isMap)
		assertTrue(HashMap::class.java.isMap)
	}
	
	@Test //TESTED
	fun isSerializable() {
		assertTrue(List::class.java.isSerializable)
		assertTrue(Foo::class.java.isSerializable)
		assertTrue(Serializable::class.java.isSerializable)
	}
	
	class Foo : Serializable {
		val a = 1
		var b = 2
	}
	
	@Test //TESTED
	fun getSetterMap() {
		println(Foo::class.java.setterMap)
		assertTrue(Foo::class.java.setterMap.size == 1)
	}
	
	@Test //TESTED
	fun getGetterMap() {
		println(Foo::class.java.getterMap)
		assertTrue(Foo::class.java.getterMap.size == 2)
	}
}
