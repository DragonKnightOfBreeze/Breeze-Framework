// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.junit.*
import java.nio.charset.*
import java.sql.*
import java.util.*
import java.util.Date

class ConverterTest {
	private fun println(value: Any) {
		kotlin.io.println(value.convert<String>())
	}

	@Test
	fun test() {
		println("2020-01-01".convert<Date>())
		println("2020-01-01".convert<Date>(mapOf("format" to "yyyy-MM-dd")))
		println("2020 01 01".convert<Date>(mapOf("format" to "yyyy MM dd")))

		println(Date().convert<String>())
		println(Date().convert<String>(mapOf("format" to "yyyy-MM-dd")))
		println(Date().convert<String>(mapOf("format" to "yyyy MM dd")))

		println(123.convert<String>())
		println("java.lang.Object".convert<Class<*>>())

		println("123".convert<Int>())
		println("123".convert<String>())
		println(true.convert<Int>())
		println("UTF-8".convert<Charset>())
		println(Date().convert<String>())
		println(Date().convert<String>(mapOf("raw" to true)))
		println("1..2".convert<IntRange>())
		println("1..2".convert<LongRange>())
		println("a..b".convert<CharRange>())

		println(0.convert<NV>())
		println("Name".convert<NV>())

		//println("1,2,3".convert<List<Int>>())
		//println("1,2,3".convert<List<Long>>())
		//println("1,2,3".convert<List<String>>())
		//
		//
		////println(arrayOf(1, 2, 3).convert<Iterator<String>>())
		////println(arrayOf(1, 2, 3).convert<Iterable<String>>())
		////println(arrayOf(1, 2, 3).convert<Collection<String>>())
		//
		//println(arrayOf(1, 2, 3).convert<List<Int>>())
		//println(arrayOf(1, 2, 3).convert<List<Long>>())
		//println(arrayOf(1, 2, 3).convert<List<String>>())
		//println(listOf(1, 2, 3).convert<List<Int>>())
		//println(listOf(1, 2, 3).convert<List<Long>>())
		//println(listOf(1, 2, 3).convert<List<String>>())
		//println(setOf(1, 2, 3).convert<List<Int>>())
		//println(setOf(1, 2, 3).convert<List<Long>>())
		//println(setOf(1, 2, 3).convert<List<String>>())
		//
		//println(arrayOf(1, 2, 3).convert<Set<Int>>())
		//println(arrayOf(1, 2, 3).convert<Set<Long>>())
		//println(arrayOf(1, 2, 3).convert<Set<String>>())
		//println(listOf(1, 2, 3).convert<Set<Int>>())
		//println(listOf(1, 2, 3).convert<Set<Long>>())
		//println(listOf(1, 2, 3).convert<Set<String>>())
		//println(setOf(1, 2, 3).convert<Set<Int>>())
		//println(setOf(1, 2, 3).convert<Set<Long>>())
		//println(setOf(1, 2, 3).convert<Set<String>>())
		//
		//println(arrayOf(1, 2, 3).convert<Array<Int>>())
		//println(arrayOf(1, 2, 3).convert<Array<Long>>())
		//println(arrayOf(1, 2, 3).convert<Array<String>>())
		//println(listOf(1, 2, 3).convert<Array<Int>>())
		//println(listOf(1, 2, 3).convert<Array<Long>>())
		//println(listOf(1, 2, 3).convert<Array<String>>())
		//println(setOf(1, 2, 3).convert<Array<Int>>())
		//println(setOf(1, 2, 3).convert<Array<Long>>())
		//println(setOf(1, 2, 3).convert<Array<String>>())
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

	@Test
	fun testNull() {
		try {
			println(null.convert<String>())
		} catch(e: Exception) {
			e.printStackTrace()
		}
		try {
			println(null.convert<String?>())
		} catch(e: Exception) {
			e.printStackTrace()
		}
		try {
			println(null.convertOrNull<String>())
		} catch(e: Exception) {
			e.printStackTrace()
		}
		try {
			println(null.convertOrNull<String?>())
		} catch(e: Exception) {
			e.printStackTrace()
		}
	}

	enum class NV {
		Name, Value
	}
}
