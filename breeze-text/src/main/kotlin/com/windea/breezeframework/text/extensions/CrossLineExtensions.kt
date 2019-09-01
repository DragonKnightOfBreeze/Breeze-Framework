package com.windea.breezeframework.text.extensions

private var enableCrossLine = false
private var prepareCrossLineSurroundingWith = false

/**执行跨行操作。*/
fun <R> List<String>.crossLine(block: (List<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**执行跨行操作。*/
fun <R> Sequence<String>.crossLine(block: (Sequence<String>) -> R): R {
	enableCrossLine = true
	return this.let(block).also {
		enableCrossLine = false
		prepareCrossLineSurroundingWith = false
	}
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(prefix: String, suffix: String, ignoreCase: Boolean = false): Boolean {
	check(enableCrossLine) { "[ERROR] Cross line operations are not enabled. They are enabled in crossLine { ... } block." }
	
	val isBeginBound = this.contains(prefix, ignoreCase)
	val isEndBound = this.contains(suffix, ignoreCase)
	if(isBeginBound && !prepareCrossLineSurroundingWith) prepareCrossLineSurroundingWith = true
	if(isEndBound) prepareCrossLineSurroundingWith = false
	return !isBeginBound && prepareCrossLineSurroundingWith
}

/**判断当前行是否在指定的跨行前后缀之间。在[crossLine]之中调用这个方法。*/
fun String.crossLineSurroundsWith(delimiter: String, ignoreCase: Boolean = false): Boolean =
	this.crossLineSurroundsWith(delimiter, delimiter, ignoreCase)
