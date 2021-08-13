// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import icu.windea.breezeframework.core.extension.*
import kotlinx.coroutines.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ForEachAsyncBenchmark {
	companion object{
		private val list = listOf(1000L,1100L,1200L,1300L,1400L,1500L)
	}

	//Benchmark                                       Mode  Cnt           Score          Error  Units
	//ForEachAsyncBenchmark.forEachAsync1ByExecutor   avgt    5  1508144420.000 ± 20858702.922  ns/op
	//ForEachAsyncBenchmark.forEachAsync2ByStream     avgt    5  1509301320.000 ± 19319054.006  ns/op
	//ForEachAsyncBenchmark.forEachAsync3ByCoroutine  avgt    5  1513109500.000 ±  7588277.508  ns/op

	@Benchmark fun forEachAsync1ByExecutor() = run{
		list.parallelForEach { //其中的操作将会并发执行
			Thread.sleep(it)
			println(it)
		}
	}

	@Benchmark fun forEachAsync2ByStream() = run{
		list.parallelStream().forEach { //其中的操作将会并发执行
			Thread.sleep(it)
			println(it)
		}
	}

	@Benchmark fun forEachAsync3ByCoroutine() = run{
		runBlocking {
			list.forEach {
				launch { //launch{}中的操作将会并发执行
					delay(it)
					println(it)
				}
			}
		}
	}
}
