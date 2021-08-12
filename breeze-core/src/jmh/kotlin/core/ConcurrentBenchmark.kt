// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import icu.windea.breezeframework.core.extension.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class ConcurrentBenchmark {
	companion object{
		private val list = listOf(1000L,1500L,2000L)
	}

	@Benchmark fun forEachAsyncByStream() = run{
		list.parallelStream().forEach {
			Thread.sleep(it)
			println(it)
		}
	}

	@Benchmark fun forEachAsyncByExecutor() = run{
		list.parallelForEach {
			Thread.sleep(it)
			println(it)
		}
	}

	@Benchmark fun forEachAsyncByCoroutine() = run{
		//runBlocking{
		//
		//}
	}
}
