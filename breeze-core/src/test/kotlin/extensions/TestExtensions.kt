package com.windea.breezeframework.core.extensions

import kotlin.system.*

internal fun Any?.andPrintln(){
	println(this)
}

/**
 * 比较两段代码块的性能。若返回`true`则表示第一段代码块性能更好
 */
internal fun comparePerformance(repeatTimes: Int, block1: () -> Unit, block2: () -> Unit): Boolean {
	//重复n次，每次记录两者中谁的性能更好，然后总结累积性能最好的
	val nanoTimes1 = arrayOfNulls<Int>(repeatTimes).map { measureNanoTime { block1() } }
	val nanoTimes2 = arrayOfNulls<Int>(repeatTimes).map { measureNanoTime { block2() } }
	var result1 = 0
	var result2 = 0
	nanoTimes1.zip(nanoTimes2).forEach { (a, b) ->
		if(a < b) result1++
		else if(a > b) result2++
	}
	println(result1)
	println(result2)
	return result1 >= result2
}
