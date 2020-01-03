package com.windea.breezeframework.core.tests

import com.windea.breezeframework.core.extensions.*
import kotlin.test.*

class CloneableTest {
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

		println(obj1 === obj2)
		println(deepObj1 === deepObj2)
		println(deepObj1.b === deepObj2.b)
		println(deepObj1.c === deepObj2.c)
		println(deepObj1.obj === deepObj2.obj)
	}
}


