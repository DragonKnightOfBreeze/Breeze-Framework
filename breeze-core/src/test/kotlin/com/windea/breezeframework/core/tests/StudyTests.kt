package com.windea.breezeframework.core.tests

import org.junit.*

class StudyTests {
	@Test
	fun test1() {
		readLine()
	}
	
	@Test
	fun sequenceTest() {
		val seq1 = sequence { this.yield(1) }
		seq1.take(5).forEach { println(it) }
		
		val seq2 = generateSequence(0) { it + 1 }
		seq2.take(10).forEach { println(it) }
	}
	
	fun test2() {
	}
	
}

object Singleton {
	@Synchronized
	fun a() {
	}
}
