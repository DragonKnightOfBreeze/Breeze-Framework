// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

import java.io.*
import java.util.*
import java.util.concurrent.*
import kotlin.system.*
import kotlin.test.*

class StringTest {
	//79254201
	//9470000
	//11854100
	//156398400
	@Test
	fun buildStringTest() {
		val uuid = UUID.randomUUID().toString()
		measureNanoTime {
			val buffer = StringBuilder()
			repeat(10) {
				buffer.append(uuid)
			}
		}.also { println(it) }

		measureNanoTime {
			val buffer = StringWriter()
			repeat(10) {
				buffer.append(uuid)
			}
		}.also { println(it) }

		measureNanoTime {
			var str = ""
			repeat(10) {
				str += uuid
			}
		}.also { println(it) }

		measureNanoTime {
			val buffer = StringBuilder()
			repeat(10000) {
				buffer.append(uuid)
			}
		}.also { println(it) }

		measureNanoTime {
			val buffer = StringWriter()
			repeat(10000) {
				buffer.append(uuid)
			}
		}.also { println(it) }

		measureNanoTime {
			var str = ""
			repeat(10000) {
				str += uuid
			}
		}.also { println(it) }
	}

	//1: 1901668300
	//2: 177258400
	//2: 72184800
	@Test
	fun mapToArrayAsyncTest() {
		//并发map不会有线程数据可见性问题，不需要使用volatile或ThreadLocal
		//线程数量过多，速度会明显下降

		val n = 1000
		val uuid = UUID.randomUUID().toString()
		run {
			val array = arrayOfNulls<String>(n)
			measureNanoTime {
				repeat(n) {
					array[it] = uuid
				}
			}.also { println("1: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newFixedThreadPool(64)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("2: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newFixedThreadPool(256)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("3: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newWorkStealingPool(64)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("4: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newWorkStealingPool(256)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("5: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newWorkStealingPool(256)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("5: $it") }
		}
		run {
			val a = ThreadLocal.withInitial { arrayOfNulls<String>(n) }
			val array = a.get()
			val executor = Executors.newWorkStealingPool(256)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = uuid
					}.get()
				}
			}.also { println("5: $it") }
		}
	}

	@Test
	fun joinToStringTest() {
		val array = Array(1000) { it * 10 }
		println(measureNanoTime {
			array.joinToString(", ") { (it * 3 / 5 + 2).toString().repeat(2) }
		})
	}
}
