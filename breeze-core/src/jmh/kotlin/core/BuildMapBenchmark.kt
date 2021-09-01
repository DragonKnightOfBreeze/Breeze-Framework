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
	//Benchmark                Mode  Cnt    Score    Error  Units
	//BuildMapBenchmark.test1  avgt    5   44.707 ±  1.253  ns/op    HashMap().apply{ ... }
	//BuildMapBenchmark.test2  avgt    5   70.900 ± 12.724  ns/op    LinkedHashMap().apply{ ... }
	//BuildMapBenchmark.test3  avgt    5   73.258 ±  7.930  ns/op    Collections.unmodifiableMap(LinkedHashMap().apply{ ... })
	//BuildMapBenchmark.test4  avgt    5  135.810 ±  6.153  ns/op    ImmutableMap.copyOf(LinkedHashMap().apply{ ... })
	//BuildMapBenchmark.test5  avgt    5   71.563 ±  7.220  ns/op    mapOf(...)
	//BuildMapBenchmark.test6  avgt    5   79.752 ±  7.877  ns/op    mutableMapOf(...)
	//BuildMapBenchmark.test7  avgt    5   48.432 ±  1.484  ns/op    buildMap{ ... }
	//BuildMapBenchmark.test8  avgt    5   49.905 ±  1.627  ns/op    ImmutableMap.of(...)
	//BuildMapBenchmark.test9  avgt    5   59.035 ±  3.054  ns/op    ImmutableMap.copyOf(pairs)

	@Benchmark fun test1() = HashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test2() = LinkedHashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test3() = Collections.unmodifiableMap(LinkedHashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") })

	@Benchmark fun test4() = ImmutableMap.copyOf(LinkedHashMap<String, String>().apply { put("1", "a");put("2", "b");put("3", "c") })

	@Benchmark fun test5() = mapOf("a" to "1", "b" to "2", "c" to "3")

	@Benchmark fun test6() = mutableMapOf("a" to "1", "b" to "2", "c" to "3")

	@Benchmark fun test7() = buildMap<String,String> { put("1", "a");put("2", "b");put("3", "c") }

	@Benchmark fun test8() = ImmutableMap.of("a", "1", "b", "2", "c", "3")

	@Benchmark fun test9() = arrayOf("a" to "1", "b" to "2", "c" to "3").let {
		val builder = ImmutableMap.builder<String, String>()
		for ((k,v) in it) builder.put(k,v)
		builder.build()
	}
}
