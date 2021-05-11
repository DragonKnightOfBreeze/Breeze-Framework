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
class BuildListBenchmark {
	//Benchmark                  Mode  Cnt   Score   Error  Units
	//BuildListBenchmark.test1   avgt    5   9.979 ±  0.569  ns/op    Arrays.asList(...)
	//BuildListBenchmark.test2   avgt    5  13.698 ±  1.214  ns/op    Collections.unmodifiableList(Arrays.asList(..))
	//BuildListBenchmark.test3   avgt    5  14.767 ±  2.880  ns/op    ArrayList(Arrays.asList(...))
	//BuildListBenchmark.test4   avgt    5  22.162 ±  5.879  ns/op    ArrayList().apply {...}
	//BuildListBenchmark.test5   avgt    5  18.545 ±  0.439  ns/op    ArrayList() {...}
	//BuildListBenchmark.test6   avgt    5  36.671 ± 10.686  ns/op    LinkedList(Arrays.asList(...))
	//BuildListBenchmark.test7   avgt    5  25.283 ±  1.240  ns/op    LinkedList().apply {...}
	//BuildListBenchmark.test8   avgt    5  25.232 ±  0.316  ns/op    LinkedList<String?>() {...}
	//BuildListBenchmark.test9   avgt    5   9.990 ±  1.818  ns/op    ImmutableList.of(...)
	//BuildListBenchmark.test10  avgt    5  10.194 ±  0.491  ns/op    listOf(...) -> Arrays.asList(...)
	//BuildListBenchmark.test11  avgt    5  16.358 ±  4.001  ns/op    mutableListOf(...) -> ArrayList(ArrayAsCollection(...))

	@Benchmark fun test1() = Arrays.asList("1", "2", "3","4","5")

	@Benchmark fun test2() = Collections.unmodifiableList(Arrays.asList("1", "2", "3","4","5"))

	@Benchmark fun test3() = ArrayList(Arrays.asList("1", "2", "3","4","5"))

	@Benchmark fun test4() = ArrayList<String>().apply { add("1");add("2");add("3");add("4");add("5") }

	@Benchmark fun test5() = object : ArrayList<String>() {
		init {
			add("1")
			add("2")
			add("3")
			add("4")
			add("5")
		}
	}

	@Benchmark fun test6() = LinkedList(Arrays.asList("1", "2", "3","4","5"))

	@Benchmark fun test7() = LinkedList<String>().apply { add("1");add("2");add("3");add("4");add("5") }

	@Benchmark fun test8() = object : LinkedList<String?>() {
		init {
			add("1")
			add("2")
			add("3")
			add("4")
			add("5")
		}
	}

	@Benchmark fun test9() = ImmutableList.of("1", "2", "3","4","5")

	@Benchmark fun test10() = listOf("1", "2", "3","4","5")

	@Benchmark fun test11() = mutableListOf("1", "2", "3","4","5")

	@Benchmark fun test12() = ImmutableList.copyOf(arrayOf("1","2","3","4","5"))
}
