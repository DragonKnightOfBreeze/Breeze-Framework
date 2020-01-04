package com.windea.breezeframework.core.extensions

import kotlin.test.*

class CloneableExtensionsTest {
	class Obj : Cloneable

	class DeepObj : Cloneable {
		var a = 1
		var b = arrayOf(1)
		var c = listOf(1)
		var obj: Obj = Obj()
	}

	@Test //TESTED
	fun test1() {
		val obj1 = Obj()
		val obj2 = obj1.shallowClone()

		val deepObj1 = DeepObj()
		val deepObj2 = deepObj1.deepClone()

		assertNotEquals(obj1, obj2)
		assertNotEquals(deepObj1, deepObj2)
		assertNotEquals(deepObj1.b, deepObj2.b)
		assertNotEquals(deepObj1.c, deepObj2.c)
		assertNotEquals(deepObj1.obj, deepObj2.obj)
	}
}


