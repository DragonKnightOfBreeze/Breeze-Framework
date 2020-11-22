// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.serialization.component.*
import com.windea.breezeframework.serialization.extension.*
import java.util.concurrent.*
import kotlin.random.*
import kotlin.system.*
import kotlin.test.*

class BreezeSerializerTest {
	@Test
	fun jsonSerializeTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze"),
			"belong" to mapOf(
				"Country" to "Invoka",
				"Organization" to "BreezeKnights"
			)
		)
		println(a.serializeBy(BreezeSerializer.BreezeJsonSerializer()))
		println(a.serializeBy(BreezeSerializer.BreezeJsonSerializer { prettyPrint = true }))
	}

	//3~10倍 性能差距到底是怎么来的？
	//1458238000
	//274115400
	//537588500
	//342167900
	@Test
	fun jsonSerializePerformanceTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze")
		)
		val b = List(100) { a }
		println(measureNanoTime {  b.serializeBy(JsonSerializer.BreezeJsonSerializer()) })
		println(measureNanoTime {  b.serializeBy(JsonSerializer.JacksonJsonSerializer()) })
		println(measureNanoTime { b.serializeBy(JsonSerializer.GsonSerializer()) })
		println(measureNanoTime { b.serializeBy(JsonSerializer.FastJsonSerializer()) })
	}

	@Test
	fun asyncMapTest(){
		val range = (0..20).toList().toTypedArray()
		println(measureNanoTime{range.mapToArray{ i->
			Thread.sleep(200)
			buildString{repeat(Random.nextInt(10)) { append(i) }} }.contentToString()})
		println(measureNanoTime{range.mapToArrayAsync(Executors.newFixedThreadPool(20)){ i->
			Thread.sleep(200)
			buildString{repeat(Random.nextInt(10)) { append(i) }} }.contentToString()})
	}
}

internal inline fun <T, reified R> Array<T>.mapToArray(block:(T)->R) :Array<R>{
	return Array(this.size){ block(this[it]) }
}

internal inline fun <T, reified R> Array<T>.mapToArrayAsync(executor:ExecutorService,crossinline block:(T)->R) :Array<R>{
	val result = arrayOfNulls<R>(this.size)
	for(index in this.indices) {
		result[index] = executor.submit(Callable{ block(this[index]) }).get()
	}
	return Array(this.size){ block(this[it]) }
}
