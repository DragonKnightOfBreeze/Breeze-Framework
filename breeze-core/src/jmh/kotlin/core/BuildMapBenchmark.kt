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
class BuildMapBenchmark {
	//Benchmark                Mode  Cnt    Score   Error  Units
	//BuildMapBenchmark.test1  avgt    5   36.233 ±  1.858  ns/op    HashMap().apply{ ... }
	//BuildMapBenchmark.test2  avgt    5   62.812 ±  3.225  ns/op    LinkedHashMap().apply{ ... }
	//BuildMapBenchmark.test3  avgt    5   40.179 ±  3.919  ns/op    Collections.unmodifiableMap(HashMap().apply{ ... })
	//BuildMapBenchmark.test4  avgt    5  115.077 ± 55.175  ns/op    ImmutableMap.copyOf(HashMap().apply{ ... })
	//BuildMapBenchmark.test5  avgt    5   77.029 ± 12.807  ns/op    mapOf(...)
	//BuildMapBenchmark.test6  avgt    5   76.770 ± 25.132  ns/op    mutableMapOf(...)
	//BuildMapBenchmark.test7  avgt    5   49.887 ± 20.615  ns/op    buildMap{ ... }
	//BuildMapBenchmark.test8  avgt    5   39.068 ±  4.241  ns/op    ImmutableMap.of(...)

	@Benchmark fun test1() = HashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test2() = LinkedHashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test3() =
		Collections.unmodifiableMap(HashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") })

	@Benchmark fun test4() =
		ImmutableMap.copyOf(HashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") })

	@Benchmark fun test5() = mapOf("a" to "1", "b" to "2", "c" to "3")

	@Benchmark fun test6() = mutableMapOf("a" to "1", "b" to "2", "c" to "3")

	@Benchmark fun test7() = buildMap<String,String> { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test8() = ImmutableMap.of("a", "1", "b", "2", "c", "3")
}
