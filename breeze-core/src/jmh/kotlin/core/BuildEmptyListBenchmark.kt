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
@Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
class BuildEmptyListBenchmark {
	//BuildEmptyListBenchmark.test1  avgt    5  6.182 ± 0.203  ns/op    ArrayList<String>()
	//BuildEmptyListBenchmark.test2  avgt    5  6.397 ± 1.246  ns/op    LinkedList<String>()
	//BuildEmptyListBenchmark.test3  avgt    5  3.763 ± 0.065  ns/op    Collections.emptyList<String>()
	//BuildEmptyListBenchmark.test4  avgt    5  4.014 ± 0.757  ns/op    ImmutableList.of<String>()
	//BuildEmptyListBenchmark.test5  avgt    5  4.014 ± 0.933  ns/op    emptyList<String>()

	@Benchmark fun test1() = ArrayList<String>()

	@Benchmark fun test2() = LinkedList<String>()

	@Benchmark fun test3() = Collections.emptyList<String>()

	@Benchmark fun test4() = ImmutableList.of<String>()

	@Benchmark fun test5() = emptyList<String>()
}
