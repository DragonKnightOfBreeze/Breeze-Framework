// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import com.google.common.collect.*
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class BuildSingletonListBenchmark {
	//Benchmark                          Mode  Cnt   Score   Error  Units
	//BuildSingletonListBenchmark.test1  avgt    5   6.168 ± 0.340  ns/op    Collections.singletonList(...)
	//BuildSingletonListBenchmark.test2  avgt    5   8.469 ± 0.915  ns/op    Arrays.asList(...)
	//BuildSingletonListBenchmark.test3  avgt    5  14.132 ± 2.883  ns/op    ArrayList<String>().apply {...}
	//BuildSingletonListBenchmark.test4  avgt    5  10.551 ± 2.930  ns/op    LinkedList<String>().apply {...}
	//BuildSingletonListBenchmark.test5  avgt    5   6.030 ± 1.991  ns/op    ImmutableList.of(...)
	//BuildSingletonListBenchmark.test6  avgt    5   6.289 ± 0.526  ns/op    listOf(...)

	@Benchmark fun test1() = Collections.singletonList("1")

	@Benchmark fun test2() = Arrays.asList("1")

	@Benchmark fun test3() = ArrayList<String>().apply { add("1") }

	@Benchmark fun test4() = LinkedList<String>().apply { add("1") }

	@Benchmark fun test5() = ImmutableList.of("1")

	@Benchmark fun test6() = listOf("1")
}
