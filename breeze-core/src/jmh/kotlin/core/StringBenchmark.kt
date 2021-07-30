// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core

import org.openjdk.jmh.annotations.*
import java.util.concurrent.*

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
class StringBenchmark {
	//测试java和kotlin的String的split和replace方法的性能差异
	//注意java的String的相关方法是基于正则的

	//Benchmark                       Mode  Cnt    Score   Error  Units
	//StringBenchmark.splitString1    avgt    5   58.501 ± 0.824  ns/op
	//StringBenchmark.splitString2    avgt    5   71.052 ± 0.602  ns/op
	//StringBenchmark.splitString3    avgt    5   72.372 ± 1.200  ns/op
	//StringBenchmark.replaceString1  avgt    5   36.672 ± 0.858  ns/op
	//StringBenchmark.replaceString2  avgt    5    8.486 ± 0.120  ns/op
	//StringBenchmark.replaceString3  avgt    5    8.469 ± 0.159  ns/op
	//StringBenchmark.replaceString4  avgt    5   37.419 ± 0.869  ns/op
	//StringBenchmark.replaceString5  avgt    5  237.925 ± 3.552  ns/op

	@Benchmark fun splitString1() = "1,2,3,4".split(',')
	@Benchmark fun splitString2() = "1,2,3,4".split(",")
	@Benchmark fun splitString3() = ("1,2,3,4" as java.lang.String).split(",")

	@Benchmark fun replaceString1() = "abc\tdef\tg".replace("\t", " ")
	@Benchmark fun replaceString2() = "abc\tdef\tg".replace('\t', ' ')
	@Benchmark fun replaceString3() = ("abc\tdef\tg" as java.lang.String).replace('\t',' ')
	@Benchmark fun replaceString4() = ("abc\tdef\tg" as java.lang.String).replace("\t"," ")
	@Benchmark fun replaceString5() = ("abc\tdef\tg" as java.lang.String).replaceAll("\t"," ")
}
