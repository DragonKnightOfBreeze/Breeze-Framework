// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.performance

import org.junit.*
import java.util.concurrent.*
import kotlin.system.*

class MiscTest {
	//1:2
	//832269300
	//1754138500
	@Test
	fun insertCharBeforeTest() {
		val n = 1000000
		println(insertCharBeforeByLoop("Foo.bar[1].abc[def]", '.', '['))
		println(insertCharBeforeByReplace("Foo.bar[1].abc[def]", '.', '['))
		val executor = Executors.newFixedThreadPool(2)
		val a = executor.submit(Callable {
			measureNanoTime {
				repeat(n) { insertCharBeforeByLoop("Foo.bar[1].abc[def]", '.', '[') }
			}
		})
		val b = executor.submit(Callable {
			measureNanoTime {
				repeat(n) { insertCharBeforeByReplace("Foo.bar[1].abc[def]", '.', '[') }
			}
		})
		println(a.get())
		println(b.get())
	}

	private fun insertCharBeforeByLoop(string: String, beforeChar: Char, afterChar: Char): String {
		return buildString {
			val chars = string.toCharArray()
			for(char in chars) {
				when {
					char == afterChar -> append(beforeChar).append(afterChar)
					else -> append(char)
				}
			}
		}
	}

	private fun insertCharBeforeByReplace(string: String, beforeChar: Char, afterChar: Char): String {
		return string.replace(afterChar.toString(), beforeChar.toString() + afterChar)
	}

	@Test
	fun constTest() {
		val n = 1000000
		val abc = "abc"
		val executor = Executors.newFixedThreadPool(2)
		val a = executor.submit(Callable {
			measureNanoTime {
				repeat(n) { abc.toCharArray().joinToString("\n") }
			}
		})
		val b = executor.submit(Callable {
			measureNanoTime {
				repeat(n) { "ABC".toCharArray().joinToString("\n") }
			}
		})
		println(a.get())
		println(b.get())
	}
}
