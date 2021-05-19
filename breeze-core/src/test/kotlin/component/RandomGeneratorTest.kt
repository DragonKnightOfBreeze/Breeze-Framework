// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.junit.*
import java.math.*
import java.time.*
import java.util.*

class RandomGeneratorTest {
	@Test
	fun test(){
		println(randomValue<Byte>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Short>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Int>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Long>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Float>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Double>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<BigInteger>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<BigDecimal>(mapOf("min" to 1,"max" to "100")))
		println(randomValue<Char>())
		println(randomValue<Boolean>())
		println(randomValue<String>(mapOf("length" to 3,"source" to "abc")))
		println(randomValue<Date>(mapOf("min" to "2020-01-01","max" to "2022-01-01")))
		println(randomValue<LocalDate>(mapOf("min" to "2020-01-01","max" to "2022-01-01")))
		println(randomValue<LocalTime>(mapOf("min" to "12:00:00","max" to "13:00:00")))
		println(randomValue<LocalDateTime>(mapOf("min" to "2020-01-01 12:00:00","max" to "2022-01-01 13:00:00")))
		println(randomValue<Gender>())
	}

	enum class Gender{
		MALE,FEMALE
	}
}
