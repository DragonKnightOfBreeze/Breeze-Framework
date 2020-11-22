// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.performance

import com.windea.breezeframework.core.extension.*
import java.io.*
import java.util.*
import java.util.concurrent.*
import kotlin.random.*
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
		}.andPrintln()

		measureNanoTime {
			val buffer = StringWriter()
			repeat(10) {
				buffer.append(uuid)
			}
		}.andPrintln()

		measureNanoTime {
			val buffer = DataWriter()
			repeat(10){
				buffer.append(uuid)
			}
		}.andPrintln()

		measureNanoTime {
			var str = ""
			repeat(10) {
				str += uuid
			}
		}.andPrintln()

		measureNanoTime {
			val buffer = StringBuilder()
			repeat(10000) {
				buffer.append(uuid)
			}
		}.andPrintln()

		measureNanoTime {
			val buffer = StringWriter()
			repeat(10000) {
				buffer.append(uuid)
			}
		}.andPrintln()

		measureNanoTime {
			val buffer = DataWriter()
			repeat(10000){
				buffer.append(uuid)
			}
		}.andPrintln()

		measureNanoTime {
			var str = ""
			repeat(10000) {
				str += uuid
			}
		}.andPrintln()
	}

	//1: 1901668300
	//2: 177258400
	//2: 72184800
	@Test
	fun mapToArrayAsyncTest() {
		//并发map不会有线程数据可见性问题，不需要使用volatile或ThreadLocal
		//线程数量过多，速度会明显下降

		val n = 1000
		run {
			val array = arrayOfNulls<String>(n)
			measureNanoTime {
				repeat(n) {
					array[it] = UUID.randomUUID().toString()
				}
			}.also { println("1: $it") }
		}
		run {
			val array = arrayOfNulls<String>(n)
			val executor = Executors.newFixedThreadPool(64)
			measureNanoTime {
				repeat(n) {
					executor.submit {
						array[it] = UUID.randomUUID().toString()
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
						array[it] = UUID.randomUUID().toString()
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
						array[it] = UUID.randomUUID().toString()
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
						array[it] = UUID.randomUUID().toString()
					}.get()
				}
			}.also { println("5: $it") }
		}
	}
}

class FJ:ForkJoinTask<String>{
	override fun exec(): Boolean {
		TODO()
	}
}
