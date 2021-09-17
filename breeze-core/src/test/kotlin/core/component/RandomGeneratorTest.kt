// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.component.extension.*
import icu.windea.breezeframework.core.extension.*
import org.junit.*
import java.math.*
import java.time.*
import java.util.*

class RandomGeneratorTest {
	@Test
	fun test() {
		prettyPrintln(randomValue<Byte>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Short>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Int>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Long>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Float>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Double>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<BigInteger>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<BigDecimal>(mapOf("min" to 1, "max" to "10")))
		prettyPrintln(randomValue<Char>())
		prettyPrintln(randomValue<Boolean>())
		prettyPrintln(randomValue<String>(mapOf("length" to 3, "source" to "abc")))
		prettyPrintln(randomValue<Date>(mapOf("min" to "2020-01-01", "max" to "2022-01-01")))
		prettyPrintln(randomValue<LocalDate>(mapOf("min" to "2020-01-01", "max" to "2022-01-01")))
		prettyPrintln(randomValue<LocalTime>(mapOf("min" to "12:00:00", "max" to "13:00:00", "format" to "HH:mm:ss")))
		prettyPrintln(randomValue<LocalDateTime>(mapOf("min" to "2020-01-01 12:00:00", "max" to "2022-01-01 13:00:00", "format" to "yyyy-MM-dd HH:mm:ss")))
		prettyPrintln(randomValue<Gender>())
	}

	enum class Gender {
		MALE, FEMALE
	}
}
