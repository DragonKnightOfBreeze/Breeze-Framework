// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.performance

import com.windea.breezeframework.core.extensions.*
import java.util.concurrent.*
import kotlin.system.*
import kotlin.test.*

class PathTypeTest {
	//2764383900 splitToSequence
	//2338413100 split
	//4465709400 regex
	//209347500 char iterator
	@Test
	fun matchesTest() {
		val n = 1000000
		val executor = Executors.newFixedThreadPool(3)
		val f1 = executor.submit(Callable{ measureNanoTime { repeat(n) { matches1("/foo/bar/far", "/foo/{bar}/far") } } })
		val f2 = executor.submit(Callable{ measureNanoTime { repeat(n) { matches2("/foo/bar/far", "/foo/{bar}/far") } } })
		val f3 = executor.submit(Callable{ measureNanoTime { repeat(n) { matches3("/foo/bar/far", "/foo/{bar}/far") } } })
		val f4 = executor.submit(Callable{ measureNanoTime { repeat(n) { matches4("/foo/bar/far", "/foo/{bar}/far") } } })

		println(matches1("/foo/bar/far", "/foo/{bar}/far"))
		println(matches2("/foo/bar/far", "/foo/{bar}/far"))
		println(matches3("/foo/bar/far", "/foo/{bar}/far"))
		println(matches4("/foo/bar/far", "/foo/{bar}/far"))

		println(f1.get())
		println(f2.get())
		println(f3.get())
		println(f4.get())
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
		return Regex.escape(path).replace("""\{.*?\}""".toRegex()){ "\\E[^/]+?\\Q" }.toRegex().matches(value)
	}

	private fun matches4(value:String,path:String):Boolean{
		val delimiter = '/'
		//遍历并比较两个字符串中的字符
		val valueCharIterator = value.toCharArray().iterator()
		val pathCharIterator = path.toCharArray().iterator()
		var valueChar:Char? = null
		var pathChar:Char? = null
		loop1@ while(valueCharIterator.hasNext() && pathCharIterator.hasNext()){
			valueChar = valueCharIterator.nextChar()
			pathChar = pathCharIterator.nextChar()
			when{
				//如果path遍历到'{'并且之前是'/'处，则需要跳到'}'并且之后是'/'处
				//此时value要遍历到'/'处
				pathChar == '{' -> {
					valueChar = valueCharIterator.next{ current, _ -> current == delimiter }
					pathChar = pathCharIterator.next { current, prev -> current == delimiter && prev == '}' }
					if(valueChar == null || pathChar == null) return false
				}
				pathChar != valueChar -> return false
			}
		}
		if(valueCharIterator.hasNext() || pathCharIterator.hasNext()) return false
		return true
	}
}
