// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.junit.*
import java.math.*
import java.nio.charset.*
import java.time.*
import java.util.*
import java.util.stream.*

class DefaultGeneratorTest {
	@Test
	fun test(){
		println(defaultValue<Byte>())
		println(defaultValue<Short>())
		println(defaultValue<Int>())
		println(defaultValue<Long>())
		println(defaultValue<Float>())
		println(defaultValue<Double>())
		println(defaultValue<BigInteger>())
		println(defaultValue<BigDecimal>())
		println(defaultValue<Char>())
		println(defaultValue<Boolean>())
		println(defaultValue<String>())
		println(defaultValue<Charset>())
		println(defaultValue<Locale>())
		println(defaultValue<TimeZone>())
		println(defaultValue<ZoneId>())
		println(defaultValue<Date>())
		println(defaultValue<LocalDate>())
		println(defaultValue<LocalTime>())
		println(defaultValue<LocalDateTime>())
		println(defaultValue<Instant>())
		println(defaultValue<Weapon>())
		println(defaultValue<List<*>>())
		println(defaultValue<Array<*>>())
		println(defaultValue<Set<*>>())
		println(defaultValue<Map<*,*>>())
		println(defaultValue<Stream<*>>())
		println(defaultValue<Sequence<*>>())
		println(defaultValue<IntRange>())
		println(defaultValue<LongRange>())
		println(defaultValue<CharRange>())
	}

	enum class Weapon{
		Sword,Katana
	}
}
