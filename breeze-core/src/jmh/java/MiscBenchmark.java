// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core;

import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.*;

@BenchmarkMode(Mode.AverageTime)
@State(value = Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class MiscBenchmark {
	//buildList

	//Benchmark                           Mode  Cnt    Score     Error  Units
	//MiscBenchmark.buildList1            avgt    5    9.128 ±   0.697  ns/op
	//MiscBenchmark.buildList2            avgt    5   12.397 ±   0.835  ns/op
	//MiscBenchmark.buildList3            avgt    5   12.694 ±   0.786  ns/op
	//MiscBenchmark.buildList4            avgt    5   16.022 ±   0.410  ns/op

	//最优方案
	@Benchmark
	public List<String> buildList1(){
		return Arrays.asList("1","2","3");
	}

	@Benchmark
	public List<String> buildList2(){
		return Collections.unmodifiableList(Arrays.asList("1","2","3"));
	}

	@Benchmark
	public List<String> buildList3(){
		return new ArrayList<>(Arrays.asList("1", "2", "3"));
	}

	@Benchmark
	public List<String> buildList4(){
		return new ArrayList<String>(){
			{
				add("1");
				add("2");
				add("3");
			}
		};
	}

	//buildSet
	//Benchmark                           Mode  Cnt    Score     Error  Units
	//MiscBenchmark.buildSet1             avgt    5   74.606 ±   2.125  ns/op
	//MiscBenchmark.buildSet3             avgt    5   69.322 ±   2.275  ns/op
	//MiscBenchmark.buildSet4             avgt    5   72.263 ±   1.780  ns/op

	@Benchmark
	public Set<String> buildSet1(){
		return Collections.unmodifiableSet(new LinkedHashSet<>(Arrays.asList("1","2","3")));
	}

	//最优方案
	@Benchmark
	public Set<String> buildSet3(){
		return new LinkedHashSet<>(Arrays.asList("1", "2", "3"));
	}

	@Benchmark
	public Set<String> buildSet4(){
		return new LinkedHashSet<String>(){
			{
				add("1");
				add("2");
				add("3");
			}
		};
	}

	//buildMap
	//Benchmark                           Mode  Cnt    Score     Error  Units
	//MiscBenchmark.buildMap1             avgt    5   53.919 ±   1.577  ns/op
	//MiscBenchmark.buildMap2             avgt    5   71.137 ±   4.220  ns/op

	//最优方案
	@Benchmark
	public Map<String,String> buildMap1(){
		Pair<String,String>[] pairs = new Pair[]{Pair.of("1", "a"), Pair.of("2", "b"), Pair.of("3", "c")};
		Map<String, String> map = new LinkedHashMap<>();
		for(Pair<String, String> pair : pairs) {
			map.put(pair.getK(),pair.getV());
		}
		return map;
	}

	@Benchmark
	public Map<String,String> buildMap2(){
		return new LinkedHashMap<String,String>(){
			{
				put("1","a");
				put("2","b");
				put("3","c");
			}
		};
	}
}

class Pair<K,V>{
	private K k;
	private V v;

	public K getK() {
		return k;
	}

	public void setK(K k) {
		this.k = k;
	}

	public V getV() {
		return v;
	}

	public void setV(V v) {
		this.v = v;
	}

	public Pair(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public static <K,V> Pair<K,V> of(K k,V v){
		return new Pair<>(k,v);
	}
}
