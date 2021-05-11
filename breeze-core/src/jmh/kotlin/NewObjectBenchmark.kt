// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import com.fasterxml.jackson.databind.*
import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class NewObjectBenchmark {
	//Benchmark                        Mode  Cnt    Score    Error  Units
	//NewObjectMapper.newObject        avgt    5    5.256 ±  0.132  ns/op
	//NewObjectMapper.newObjectMapper  avgt    5  613.648 ± 17.907  ns/op

	@Benchmark fun newObject() = Any()

	@Benchmark fun newObjectMapper() = ObjectMapper()
}
