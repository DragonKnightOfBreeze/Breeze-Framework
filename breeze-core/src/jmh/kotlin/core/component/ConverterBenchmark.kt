// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.component.extension.convert
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

	//Benchmark                                 Mode  Cnt    Score    Error  Units
	//ConverterBenchmark.testStringConvertDate  avgt    5  462.139 ±  5.972  ns/op
	//ConverterBenchmark.testStringToDate       avgt    5  443.904 ± 19.041  ns/op
	//ConverterBenchmark.testDateConvertString  avgt    5  253.509 ± 14.846  ns/op
	//ConverterBenchmark.testDateToString       avgt    5  243.298 ± 11.606  ns/op
	//ConverterBenchmark.testStringConvertInt   avgt    5   34.122 ±  2.212  ns/op
	//ConverterBenchmark.testStringToInt        avgt    5    6.374 ±  3.193  ns/op

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
