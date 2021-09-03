// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.component.extension.*
import org.junit.*
import java.nio.charset.*
import java.util.*
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class ConverterTest {
	@Test
	fun test() {
		prettyPrintln("2020-01-01".convert<Date>())
		prettyPrintln("2020-01-01".convert<Date>(mapOf("format" to "yyyy-MM-dd")))
		prettyPrintln("2020 01 01".convert<Date>(mapOf("format" to "yyyy MM dd")))

		prettyPrintln(Date().convert<String>())
		prettyPrintln(Date().convert<String>(mapOf("format" to "yyyy-MM-dd")))
		prettyPrintln(Date().convert<String>(mapOf("format" to "yyyy MM dd")))

		prettyPrintln(123.convert<String>())
		prettyPrintln("java.lang.Object".convert<Class<*>>())

		prettyPrintln("123".convert<Int>())
		prettyPrintln("123".convert<String>())
		prettyPrintln(true.convert<Int>())
		prettyPrintln("UTF-8".convert<Charset>())
		prettyPrintln(Date().convert<String>())
		prettyPrintln(Date().convert<String>(mapOf("raw" to true)))
		prettyPrintln("1..2".convert<IntRange>())
		prettyPrintln("1..2".convert<LongRange>())
		prettyPrintln("a..b".convert<CharRange>())

		prettyPrintln(0.convert<NV>())
		prettyPrintln("Name".convert<NV>())

		prettyPrintln("1,2,3".convert<List<Int>>())
		prettyPrintln("1,2,3".convert<List<Long>>())
		prettyPrintln("1,2,3".convert<List<String>>())

		prettyPrintln(arrayOf(1, 2, 3).convert<Iterator<String>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Iterable<String>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Collection<String>>())

		prettyPrintln(arrayOf(1, 2, 3).convert<List<Int>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<List<Long>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<List<String>>())
		prettyPrintln(listOf(1, 2, 3).convert<List<Int>>())
		prettyPrintln(listOf(1, 2, 3).convert<List<Long>>())
		prettyPrintln(listOf(1, 2, 3).convert<List<String>>())
		prettyPrintln(setOf(1, 2, 3).convert<List<Int>>())
		prettyPrintln(setOf(1, 2, 3).convert<List<Long>>())
		prettyPrintln(setOf(1, 2, 3).convert<List<String>>())

		prettyPrintln(arrayOf(1, 2, 3).convert<Set<Int>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Set<Long>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Set<String>>())
		prettyPrintln(listOf(1, 2, 3).convert<Set<Int>>())
		prettyPrintln(listOf(1, 2, 3).convert<Set<Long>>())
		prettyPrintln(listOf(1, 2, 3).convert<Set<String>>())
		prettyPrintln(setOf(1, 2, 3).convert<Set<Int>>())
		prettyPrintln(setOf(1, 2, 3).convert<Set<Long>>())
		prettyPrintln(setOf(1, 2, 3).convert<Set<String>>())

		prettyPrintln(arrayOf(1, 2, 3).convert<Array<Int>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Array<Long>>())
		prettyPrintln(arrayOf(1, 2, 3).convert<Array<String>>())
		prettyPrintln(listOf(1, 2, 3).convert<Array<Int>>())
		prettyPrintln(listOf(1, 2, 3).convert<Array<Long>>())
		prettyPrintln(listOf(1, 2, 3).convert<Array<String>>())
		prettyPrintln(setOf(1, 2, 3).convert<Array<Int>>())
		prettyPrintln(setOf(1, 2, 3).convert<Array<Long>>())
		prettyPrintln(setOf(1, 2, 3).convert<Array<String>>())
	}

	@Test
	fun testReturnNull() {
		try {
			null.convertAAA<String>()
		} catch(e: Exception) {
			e.printStackTrace()
		}
		try {
			null.convertAAA<String?>()
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}

	inline fun <reified T> Any?.convertAAA(): T {
		return this as T
	}

	enum class NV {
		Name, Value
	}
}
