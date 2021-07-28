// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.performance

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.extension.*
import java.util.concurrent.*
import kotlin.system.*
import kotlin.test.*

class PathFormatTest {
	//3519328300    splitToSequence
	//2992920300    split
	//5065847100    regex
	//300982700     char iterator
	//196391600     char indexer
	@Test
	fun matchesTest() {
		val n = 1000000
		val executor = Executors.newFixedThreadPool(3)
		val f1 = executor.submit(Callable { measureNanoTime { repeat(n) { matches1("/foo/bar/far", "/foo/{bar}/far") } } })
		val f2 = executor.submit(Callable { measureNanoTime { repeat(n) { matches2("/foo/bar/far", "/foo/{bar}/far") } } })
		val f3 = executor.submit(Callable { measureNanoTime { repeat(n) { matches3("/foo/bar/far", "/foo/{bar}/far") } } })
		val f4 = executor.submit(Callable { measureNanoTime { repeat(n) { matches4("/foo/bar/far", "/foo/{bar}/far") } } })
		val f5 = executor.submit(Callable { measureNanoTime { repeat(n) { matches5("/foo/bar/far", "/foo/{bar}/far") } } })

		println(matches1("/foo/bar/far", "/foo/{bar}/far"))
		println(matches2("/foo/bar/far", "/foo/{bar}/far"))
		println(matches3("/foo/bar/far", "/foo/{bar}/far"))
		println(matches4("/foo/bar/far", "/foo/{bar}/far"))
		println(matches5("/foo/bar/far", "/foo/{bar}/far"))

		println(f1.get())
		println(f2.get())
		println(f3.get())
		println(f4.get())
		println(f5.get())
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
		return Regex.escape(path).replace("""\{.*?}""".toRegex()) { "\\E[^/]+?\\Q" }.toRegex().matches(value)
	}

	private fun matches4(value: String, path: String): Boolean {
		val delimiter = '/'
		val variablePrefix = '{'
		val variableSuffix: Char? = '}'
		//遍历并比较两个字符串中的字符
		val valueCharIterator = value.toCharArray().iterator()
		val pathCharIterator = path.toCharArray().iterator()
		var valueChar: Char? = null
		var pathChar: Char? = null
		loop1@ while(valueCharIterator.hasNext() && pathCharIterator.hasNext()) {
			valueChar = valueCharIterator.nextChar()
			pathChar = pathCharIterator.nextChar()
			//如果path遍历到'{'并且之前是'/'处，则需要跳到'}'并且之后是'/'处
			//此时value要遍历到'/'处
			if(pathChar == delimiter) {
				valueChar = valueCharIterator.nextChar()
				pathChar = pathCharIterator.nextChar()
				if(pathChar == variablePrefix) {
					valueChar = valueCharIterator.next { current, _ -> current == delimiter }
					pathChar = pathCharIterator.next { current, prev ->
						current == delimiter
					}
					if(valueChar == null || pathChar == null) return false
				}
			} else if(pathChar != valueChar) return false
		}
		if(valueCharIterator.hasNext() || pathCharIterator.hasNext()) return false
		return true
	}

	private fun matches5(value: String, path: String): Boolean {
		val delimiter = '/'
		val variablePrefix = '{'
		val variableSuffix: Char? = '}'
		//遍历并比较两个字符串中的字符
		var valueIndex = 0
		var pathIndex = 0
		val lastValueIndex = value.lastIndex
		val lastPathIndex = path.lastIndex
		while(valueIndex <= lastValueIndex && pathIndex <= lastPathIndex) {
			//如果path遍历到下一个'/'并且之后是'{'处，则需要跳到'/'并且之前必须是'}'处，此时value要遍历到下一个'/'处
			if(path[pathIndex] == delimiter && (pathIndex < lastPathIndex && path[pathIndex + 1] == variablePrefix)) {
				do valueIndex++ while(valueIndex <= lastValueIndex && value[valueIndex] != delimiter)
				do pathIndex++ while(pathIndex <= lastPathIndex && path[pathIndex] != delimiter)
				if(variableSuffix != null && path[pathIndex - 1] != variableSuffix) throw IllegalArgumentException()
				if(valueIndex == lastValueIndex || pathIndex == lastPathIndex) return false
			} else if(value[valueIndex] != path[pathIndex]) return false
			valueIndex++
			pathIndex++
		}
		if(valueIndex == lastValueIndex || pathIndex == lastPathIndex) return false
		return true
	}


	//性能得到了极大的提升！再也不需使用正则了！
	//503807600
	//1850404300
	@Test
	fun matchesByVsMatchesTest() {
		val n = 1000000
		val executor = Executors.newFixedThreadPool(3)
		val f1 = executor.submit(Callable { measureNanoTime { repeat(n) { "/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathFormat.AntPath) } } })
		val f2 = executor.submit(Callable { measureNanoTime { repeat(n) { "/foo/bar/bar/bar".matches("/foo/[^/?]*/b.r/.*".toRegex()) } } })

		println("/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathFormat.AntPath))
		println("/foo/bar/bar/bar".matches("/foo/[^/?]*/b.r/.*".toRegex()))

		println(f1.get())
		println(f2.get())
	}
}
