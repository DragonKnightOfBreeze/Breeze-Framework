// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class DefaultGeneratorBenchmark {
	//重构了之后 + 缓存
	//DefaultGeneratorBenchmark.test1  avgt    5  7.173 ± 0.146  ns/op
	//DefaultGeneratorBenchmark.test2  avgt    5  7.177 ± 0.214  ns/op
	//DefaultGeneratorBenchmark.test3  avgt    5  3.701 ± 0.119  ns/op
	//DefaultGeneratorBenchmark.test4  avgt    5  3.690 ± 0.069  ns/op

	//可以看出性能影响并不大
	//Benchmark                        Mode  Cnt  Score   Error  Units
	//DefaultGeneratorBenchmark.test1  avgt    5  5.706  1.365  ns/op
	//DefaultGeneratorBenchmark.test2  avgt    5  5.350  0.191  ns/op
	//DefaultGeneratorBenchmark.test3  avgt    5  3.761  0.118  ns/op
	//DefaultGeneratorBenchmark.test4  avgt    5  3.770  0.077  ns/op

	@Benchmark
	fun test1() = defaultValue<Int>()

	@Benchmark
	fun test2() = defaultValue<String>()

	@Benchmark
	fun test3() = getDefaultValue<Int>()

	@Benchmark
	fun test4() = getDefaultValue<String>()

	final inline fun <reified T> getDefaultValue() = when(T::class.java){
		Int::class.java -> 0
		String::class.java -> ""
		else -> null
	}

}
