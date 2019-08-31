package com.windea.utility.common.extensions

/**得到两个范围的并集。*/
infix fun <T : Comparable<T>> ClosedRange<T>.intersect(other: ClosedRange<T>): ClosedRange<T> {
	val start = if(this.start <= other.start) this.start else other.start
	val endInclusive = if(this.endInclusive >= other.endInclusive) this.endInclusive else other.endInclusive
	return start..endInclusive
}

/**得到两个范围的交集。*/
infix fun <T : Comparable<T>> ClosedRange<T>.union(other: ClosedRange<T>): ClosedRange<T> {
	val start = if(this.start >= other.start) this.start else other.start
	val endInclusive = if(this.endInclusive <= other.endInclusive) this.endInclusive else other.endInclusive
	return start..endInclusive
}
