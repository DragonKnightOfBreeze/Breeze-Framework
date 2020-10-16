// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.performance

import com.windea.breezeframework.core.extensions.*
import java.util.concurrent.*
import kotlin.system.*
import kotlin.test.*

class PathTypeTest {
	//2404051900 splitToSequence
	//1717388600 split
	//3217532000 regex
	@Test
	fun matchesTest() {
		val n = 1000000
		val executor = Executors.newFixedThreadPool(3)
		val a = executor.submit(Callable{
			measureNanoTime {
				repeat(n) { matches1("/foo/bar/far", "/foo/{bar}/far") }
			}
		})
		val b = executor.submit(Callable{
			measureNanoTime {
				repeat(n) { matches2("/foo/bar/far", "/foo/{bar}/far") }
			}
		})
		val c = executor.submit(Callable{
			measureNanoTime {
				repeat(n) { matches3("/foo/bar/far", "/foo/{bar}/far") }
			}
		})
		println(a.get())
		println(b.get())
		println(c.get())
	}

	private fun matches1(value: String, path: String): Boolean {
		val iterator1 = value.splitToSequence('/').iterator()
		val iterator2 = path.splitToSequence('/').iterator()
		loop@ while(iterator1.hasNext() && iterator2.hasNext()) {
			val a = iterator1.next()
			val b = iterator2.next()
			when {
				b.surroundsWith('{', '}') -> continue@loop
				b != a -> return false
			}
		}
		if(iterator1.hasNext() || iterator2.hasNext()) return false
		return true
	}

	private fun matches2(value: String, path: String): Boolean {
		val iterator1 = value.split('/').iterator()
		val iterator2 = path.split('/').iterator()
		loop@ while(iterator1.hasNext() && iterator2.hasNext()) {
			val a = iterator1.next()
			val b = iterator2.next()
			when {
				b.surroundsWith('{', '}') -> continue@loop
				b != a -> return false
			}
		}
		if(iterator1.hasNext() || iterator2.hasNext()) return false
		return true
	}

	private fun matches3(value: String, path: String): Boolean {
		return Regex.escape(path).replace("""\{.*?\}""".toRegex(), "\\E[^/]+?\\Q").toRegex().matches(value)
	}
}
