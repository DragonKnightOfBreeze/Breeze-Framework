@file:NotSure

package com.windea.breezeframework.text.extensions

import com.windea.breezeframework.core.annotations.marks.*
import com.windea.breezeframework.text.extensions.CrossLineState.prepareCrossLineSurroundingWith
import kotlin.contracts.*

@PublishedApi internal var enableCrossLine = false

@PublishedApi
internal object CrossLineState {
	var prepareCrossLineSurroundingWith = false
	
	fun resetState() {
		prepareCrossLineSurroundingWith = false
	}
}

/**执行跨行操作。*/
@ExperimentalContracts
inline fun <R> List<String>.crossLine(block: List<String>.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return this.also {
		enableCrossLine = true
		CrossLineState.resetState()
	}.let(block).also {
		enableCrossLine = false
		CrossLineState.resetState()
	}
}

/**执行跨行操作。*/
@ExperimentalContracts
inline fun <R> Sequence<String>.crossLine(block: Sequence<String>.() -> R): R {
	contract {
		callsInPlace(block, InvocationKind.EXACTLY_ONCE)
	}
	return this.also { enableCrossLine = true }.let(block).also { enableCrossLine = false }
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(prefix: String, suffix: String, ignoreCase: Boolean = false): Boolean {
	checkEnableCrossLine()
	
	val isBeginBound = this.contains(prefix, ignoreCase)
	val isEndBound = this.contains(suffix, ignoreCase)
	if(isBeginBound && !prepareCrossLineSurroundingWith) prepareCrossLineSurroundingWith = true
	if(isEndBound) prepareCrossLineSurroundingWith = false
	return !isBeginBound && prepareCrossLineSurroundingWith
}

private fun checkEnableCrossLine() {
	check(enableCrossLine) { "[ERROR] Cross line operations are not enabled. They are enabled in crossLine { ... } block." }
}
