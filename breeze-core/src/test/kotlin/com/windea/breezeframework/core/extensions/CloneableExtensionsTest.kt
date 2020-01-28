package com.windea.breezeframework.core.extensions

import kotlin.test.*

class CloneableExtensionsTest {
	class Obj : Cloneable

	class DeepObj : Cloneable {
		var a = 1
		var b = intArrayOf(1)
		var c = listOf(1)
		var d = listOf(Obj())
		var e = mutableListOf(Obj())
		var obj: Obj = Obj()
	}

	@Test
	fun test1() {
		val obj1 = Obj()
		val obj2 = obj1.shallowClone()

		val deepObj1 = DeepObj()
		val deepObj2 = deepObj1.deepClone()

		assertNotSame(obj1, obj2)
		assertNotSame(deepObj1, deepObj2)
		assertNotSame(deepObj1.c, deepObj2.c)
		assertNotSame(deepObj1.d, deepObj2.d)
		assertNotSame(deepObj1.e, deepObj2.e)
		assertNotSame(deepObj1.obj, deepObj2.obj)
	}
}


