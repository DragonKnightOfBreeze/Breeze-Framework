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
class BuildSetBenchmark {
	//Benchmark                Mode  Cnt    Score     Error  Units
	//BuildSetBenchmark.test1  avgt    5   87.245 ±  35.172  ns/op
	//BuildSetBenchmark.test2  avgt    5   78.961 ±  15.336  ns/op
	//BuildSetBenchmark.test3  avgt    5  124.740 ±  78.231  ns/op
	//BuildSetBenchmark.test4  avgt    5  107.326 ±   3.077  ns/op
	//BuildSetBenchmark.test5  avgt    5   70.242 ±  10.744  ns/op
	//BuildSetBenchmark.test6  avgt    5  108.406 ±  11.344  ns/op
	//BuildSetBenchmark.test7  avgt    5   49.452 ±   0.864  ns/op
	//BuildSetBenchmark.test8  avgt    5  124.236 ±  50.183  ns/op
	//BuildSetBenchmark.test9  avgt    5  150.689 ± 201.401  ns/op

	@Benchmark fun test1() = HashSet<String>().apply { add("1");add("2");add("3");add("4");add("5") }

	@Benchmark fun test2() = object : HashSet<String>() {
		init {
			add("1")
			add("2")
			add("3")
			add("4")
			add("5")
		}
	}

	@Benchmark fun test3() = LinkedHashSet<String>().apply { add("1");add("2");add("3");add("4");add("5") }

	@Benchmark fun test4() = object : LinkedHashSet<String>() {
		init {
			add("1")
			add("2")
			add("3")
			add("4")
			add("5")
		}
	}

	@Benchmark fun test5() =
		Collections.unmodifiableSet(HashSet<String>().apply { add("1");add("2");add("3");add("4");add("5") })

	@Benchmark fun test6() =
		Collections.unmodifiableSet(LinkedHashSet<String>().apply { add("1");add("2");add("3");add("4");add("5") })

	@Benchmark fun test7() = ImmutableSet.of("1", "2", "3", "4", "5")

	@Benchmark fun test8() = setOf("1", "2", "3", "4", "5")

	@Benchmark fun test9() = mutableSetOf("1", "2", "3", "4", "5")

	@Benchmark fun test10() = ImmutableSet.copyOf(arrayOf("1", "2", "3", "4", "5"))
}
