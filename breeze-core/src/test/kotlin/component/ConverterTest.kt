// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.junit.*
import java.util.*

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
		println(Date().convert<String>())
		println(Date().convert<String>(mapOf("raw" to true)))
		println(0.convert<NV>(mapOf("className" to "icu.windea.breezeframework.core.component.ConverterTest\$NV")))
		println("Name".convert<NV>(mapOf("className" to "icu.windea.breezeframework.core.component.ConverterTest\$NV")))
	}

	enum class NV{
		Name,Value
	}
}
