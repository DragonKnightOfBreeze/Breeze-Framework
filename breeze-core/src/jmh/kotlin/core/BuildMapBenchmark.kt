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
	//Benchmark                Mode  Cnt    Score     Error  Units
	//BuildMapBenchmark.test1  avgt    5   43.385 ±  12.298  ns/op    HashMap().apply{ ... }
	//BuildMapBenchmark.test2  avgt    5   72.233 ±   9.938  ns/op    LinkedHashMap().apply{ ... }
	//BuildMapBenchmark.test3  avgt    5   52.149 ±   8.330  ns/op    Collections.unmodifiableMap(LinkedHashMap().apply{ ... })
	//BuildMapBenchmark.test4  avgt    5  189.099 ± 111.274  ns/op    ImmutableMap.copyOf(LinkedHashMap().apply{ ... })
	//BuildMapBenchmark.test5  avgt    5   65.384 ±  13.226  ns/op    mapOf(...)
	//BuildMapBenchmark.test6  avgt    5   88.053 ±  37.805  ns/op    mutableMapOf(...)
	//BuildMapBenchmark.test7  avgt    5   51.561 ±  13.756  ns/op    buildMap{ ... }
	//BuildMapBenchmark.test8  avgt    5   44.437 ±   7.382  ns/op    ImmutableMap.of(...)
	//BuildMapBenchmark.test9  avgt    5   97.129 ±  44.223  ns/op    ImmutableMap.copyOf(pairs)

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
