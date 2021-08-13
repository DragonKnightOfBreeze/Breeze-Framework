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

	//Benchmark                                    Mode  Cnt           Score          Error  Units
	//ConcurrentBenchmark.forEachAsyncByCoroutine  avgt    5  7548870260.000 ± 28888583.423  ns/op
	//ConcurrentBenchmark.forEachAsyncByExecutor   avgt    5  1509051240.000 ± 22855332.250  ns/op
	//ConcurrentBenchmark.forEachAsyncByFlow       avgt    5  7550847620.000 ± 44819056.132  ns/op
	//ConcurrentBenchmark.forEachAsyncByStream     avgt    5  1509825640.000 ± 20520952.479  ns/op

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

	@Benchmark fun forEachAsync3BySequence() = run{
		runBlocking {
			sequence {
				list.forEach {
					Thread.sleep(it)
					yield(it)
				}
			}.forEach { println(it) }
		}
	}

	@Benchmark fun forEachAsync4ByCoroutine() = run{
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
