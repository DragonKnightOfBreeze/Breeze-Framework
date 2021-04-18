// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.extension.*
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
	}
}
