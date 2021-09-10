package icu.windea.breezeframework.core;

import com.google.common.collect.ImmutableList;
import org.openjdk.jmh.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 20, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LambdaBenchmark {

	//Benchmark                       Mode  Cnt    Score    Error  Units
	//LambdaBenchmark.filterNotReuse  avgt   20  244.954 ± 80.430  ns/op
	//LambdaBenchmark.filterReuse     avgt   20  224.101 ± 66.236  ns/op

	private static final List<String> list = ImmutableList.of("a","aaa","ab","abc","bcd","acd","aa","ac");

	@Benchmark
	public List<String> filterNotReuse(){
		return list.stream()
			.filter(e -> e.startsWith("a") && e.length() % 2 == 0)
			.map(e -> e + e)
			.filter(e -> e.startsWith("a") && e.length() % 2 == 0)
			.collect(Collectors.toList());
	}

	@Benchmark
	public List<String> filterReuse(){
		Predicate<String> predicate = e -> e.startsWith("a") && e.length() % 2 == 0;
		return list.stream()
			.filter(predicate)
			.map(e -> e + e)
			.filter(predicate)
			.collect(Collectors.toList());
	}
}
