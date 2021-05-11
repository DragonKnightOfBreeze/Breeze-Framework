// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import com.google.common.collect.*
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class BuildMapBenchmark {
	//Benchmark                Mode  Cnt    Score   Error  Units
	//BuildMapBenchmark.test1  avgt    5   50.862 ± 1.306  ns/op
	//BuildMapBenchmark.test2  avgt    5   63.678 ± 1.769  ns/op
	//BuildMapBenchmark.test3  avgt    5   47.417 ± 2.914  ns/op
	//BuildMapBenchmark.test4  avgt    5  150.116 ± 6.008  ns/op
	//BuildMapBenchmark.test5  avgt    5   73.322 ± 6.741  ns/op
	//BuildMapBenchmark.test6  avgt    5   67.481 ± 7.106  ns/op

	@Benchmark fun test1() = HashMap<String,String>().apply { put("1","a");put("2","b");put("3","c") }

	@Benchmark fun test2() = LinkedHashMap<String,String>().apply { put("1","a");put("2","b");put("3","c") }

	@Benchmark fun test3() = Collections.unmodifiableMap(HashMap<String,String>().apply { put("1","a");put("2","b");put("3","c") })

	@Benchmark fun test4() = ImmutableMap.copyOf(HashMap<String,String>().apply { put("1","a");put("2","b");put("3","c") })

	@Benchmark fun test5() = mapOf("a" to "1","b" to "2","c" to "3")

	@Benchmark fun test6() = mutableMapOf("a" to "1","b" to "2","c" to "3")

	@Benchmark fun test7() = ImmutableMap.of("a", "1", "b" , "2","c" , "3")
}
