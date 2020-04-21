package com.windea.breezeframework.game

import com.windea.breezeframework.reflect.extensions.*
import org.junit.*
import org.junit.Assert.*
import java.io.*

class ClassExtensionsTest {
	@Test
	fun isCharSequence() {
		assertTrue(String::class.java.isCharSequence)
		assertTrue(CharSequence::class.java.isCharSequence)
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

	@Test
	fun isSerializable() {
		assertTrue(Foo::class.java.isSerializable)
		assertTrue(Serializable::class.java.isSerializable)
		//assertTrue(List::class.java.isSerializable) //will assert fail
	}

	class Foo : Serializable {
		val a = 1
		var b = 2
	}

	@Test
	fun getSetterMap() {
		println(Foo::class.java.setters)
		assertTrue(Foo::class.java.setters.size == 1)
	}

	@Test
	fun getGetterMap() {
		println(Foo::class.java.getters)
		assertTrue(Foo::class.java.getters.size == 2)
	}
}
