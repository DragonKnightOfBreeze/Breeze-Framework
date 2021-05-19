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
	@Test
	fun test(){
		println("2020-01-01".convert<Date>())
		println("2020-01-01".convert<Date>(mapOf("format" to "yyyy-MM-dd")))
		println("2020 01 01".convert<Date>(mapOf("format" to "yyyy MM dd")))

		println(Date().convert<String>())
		println(Date().convert<String>(mapOf("format" to "yyyy-MM-dd")))
		println(Date().convert<String>(mapOf("format" to "yyyy MM dd")))

		println(123.convert<String>())
		println("java.lang.Object".convert<Class<*>>())
	}

	@Test
	fun test2(){
		println("123".convert<Int>())
		println("123".convert<String>())
		println(true.convert<Int>())
		println("UTF-8".convert<Charset>())
		println(Date().convert<String>())
		println(Date().convert<String>(mapOf("raw" to true)))
		println(0.convert<NV>())
		println("Name".convert<NV>())
		println("1..2".convert<IntRange>())
		println("1..2".convert<LongRange>())
		println("a..b".convert<CharRange>())
	}

	enum class NV{
		Name,Value
	}
}
