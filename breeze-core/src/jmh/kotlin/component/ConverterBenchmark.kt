// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*

//Benchmark                 Mode  Cnt    Score     Error  Units
//ConverterBenchmark.test1  avgt    3  713.282 ± 174.956  ns/op
//ConverterBenchmark.test2  avgt    3  430.361 ±  32.819  ns/op
//ConverterBenchmark.test3  avgt    3  401.333 ± 236.036  ns/op
//ConverterBenchmark.test4  avgt    3  214.373 ±  35.540  ns/op

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ConverterBenchmark {
	@Benchmark
	fun test1() = "2020-01-01".convert<Date>()

	@Benchmark
	fun test2() = "2020-01-01".toDate("yyyy-MM-dd")

	@Benchmark
	fun test3() = Date().convert<String>()

	@Benchmark
	fun test4() = Date().toString("yyyy-MM-dd")
}
