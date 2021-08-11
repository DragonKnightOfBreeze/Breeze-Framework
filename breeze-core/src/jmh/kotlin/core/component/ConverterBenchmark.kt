// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ConverterBenchmark {

	@Benchmark
	fun testStringConvertDate() = "2020-01-01".convert<Date>()

	@Benchmark
	fun testStringToDate() = "2020-01-01".toDate("yyyy-MM-dd")

	@Benchmark
	fun testDateConvertString() = Date().convert<String>()

	@Benchmark
	fun testDateToString() = Date().toString("yyyy-MM-dd")

	@Benchmark
	fun testStringConvertInt() = "123".convert<Int>()

	@Benchmark
	fun testStringToInt() = "123".toInt()
}
