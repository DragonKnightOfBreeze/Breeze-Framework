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
	fun test() {
		prettyPrintln(defaultValue<Byte>())
		prettyPrintln(defaultValue<Short>())
		prettyPrintln(defaultValue<Int>())
		prettyPrintln(defaultValue<Long>())
		prettyPrintln(defaultValue<Float>())
		prettyPrintln(defaultValue<Double>())
		prettyPrintln(defaultValue<BigInteger>())
		prettyPrintln(defaultValue<BigDecimal>())
		prettyPrintln(defaultValue<Char>())
		prettyPrintln(defaultValue<Boolean>())
		prettyPrintln(defaultValue<String>())
		prettyPrintln(defaultValue<Charset>())
		prettyPrintln(defaultValue<Locale>())
		prettyPrintln(defaultValue<TimeZone>())
		prettyPrintln(defaultValue<ZoneId>())
		prettyPrintln(defaultValue<Date>())
		prettyPrintln(defaultValue<LocalDate>())
		prettyPrintln(defaultValue<LocalTime>())
		prettyPrintln(defaultValue<LocalDateTime>())
		prettyPrintln(defaultValue<Instant>())
		prettyPrintln(defaultValue<Weapon>())
		prettyPrintln(defaultValue<Array<*>>())
		prettyPrintln(defaultValue<IntArray>())
		prettyPrintln(defaultValue<Iterator<*>>())
		prettyPrintln(defaultValue<MutableIterator<*>>())
		prettyPrintln(defaultValue<Iterable<*>>())
		prettyPrintln(defaultValue<MutableIterable<*>>())
		prettyPrintln(defaultValue<Collection<*>>())
		prettyPrintln(defaultValue<MutableCollection<*>>())
		prettyPrintln(defaultValue<List<*>>())
		prettyPrintln(defaultValue<MutableList<*>>())
		prettyPrintln(defaultValue<Set<*>>())
		prettyPrintln(defaultValue<MutableSet<*>>())
		prettyPrintln(defaultValue<Map<*, *>>())
		prettyPrintln(defaultValue<MutableMap<*, *>>())
		prettyPrintln(defaultValue<Stream<*>>())
		prettyPrintln(defaultValue<Sequence<*>>())
		prettyPrintln(defaultValue<IntRange>())
		prettyPrintln(defaultValue<LongRange>())
		prettyPrintln(defaultValue<CharRange>())
	}

	enum class Weapon {
		Sword, Katana
	}
}
