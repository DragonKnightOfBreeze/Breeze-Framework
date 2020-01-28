@file:JvmName("RegexExtensions")

package com.windea.breezeframework.core.extensions

/**得到指定字符串对于当前正则表达式的匹配分组。*/
fun Regex.matchGroups(string: String): MatchGroupCollection? = this.matchEntire(string)?.groups

/**得到指定字符串对于当前正则表达式的匹配字符串分组。*/
fun Regex.matchGroupValues(string: String): List<String>? = this.matchEntire(string)?.groupValues


/**得到当前匹配结果的指定索引的匹配字符串。*/
operator fun MatchResult.get(index: Int): String = this.groupValues[index]


/**将整数范围转化为合法的正则表达式字符串。*/
fun Regex.Companion.fromRange(range: ClosedRange<Int>): String {
	return fromRange(range.start, range.endInclusive)
}

/**将整数范围转化为合法的正则表达式字符串。*/
fun Regex.Companion.fromRange(first: Int, last: Int): String {
	//参考：[2-13] -> [2-9]|[10-13] -> [2-9]|1[0-3]
	//参考：[23-45] -> [23-29]|[30-39]|[40-45] -> 2[3-9]|3[0-9]|4[0-5]
	return when {
		//如果first大于last，则抛出异常
		first > last -> throw IllegalArgumentException("first $first is greater than last $last.")
		//如果first等于last，则直接返回这个数值
		first == last -> return first.toString()
		//如果first和last都小于10，则直接返回对应的范围字符串
		first < 10 && last < 10 -> return "[$first-$last]"
		//否则做进一步的复杂逻辑处理
		else -> {
			//每个分组，除了m-9（最多一次）、0-9或0-n（允许多次）的情况以外，其他位必须相等
			//首先找出first和比first大的满足上述情况的数
			val pairs = mutableListOf<Pair<Int, Int>>()
			var firstLimit = first
			var lastLimit = findLastLimitIn(firstLimit, last)
			pairs.add(firstLimit to lastLimit)
			//然后遍历找出后者+1和比后者+1大的满足上述情况的数，直到后者超出b
			while(lastLimit < last) {
				firstLimit = lastLimit + 1
				lastLimit = findLastLimitIn(firstLimit, last)
				pairs.add(firstLimit to lastLimit)
			}
			// 2-9, 10-13 -> [2-9]|1[0-3]
			pairs.joinToString("|") { (a, b) ->
				a.toString().toCharArray().zip(b.toString().toCharArray()) { a1, b1 ->
					if(a1 == b1) "$a1" else "[$a1-$b1]"
				}.joinToString("")
			}
		}
	}
}

private fun findLastLimitIn(firstLimit: Int, last: Int): Int {
	val lastLimitAtLeast = coerceLastLimitAtLeast(firstLimit)
	val lastLimitAtMost = coerceLastLimitAtMost(firstLimit, last)
	return minOf(lastLimitAtLeast, lastLimitAtMost)
}

private fun coerceLastLimitAtLeast(firstLimit: Int): Int {
	//返回比firstLimit大，最低位是9的数，如果是0-9，那么可以处理更高位
	var temp = firstLimit
	var bit = 1
	while(temp % 10 == 0) {
		temp /= 10
		bit++
	}
	return (firstLimit / 10.pow(bit) + 1) * 10.pow(bit) - 1
}

private fun coerceLastLimitAtMost(firstLimit: Int, last: Int): Int {
	//返回比last小，最低位尽可能地是9的数
	var temp1 = firstLimit / 10
	var temp2 = last / 10
	var bit = 1
	//得到firstLimit和last的一位数字不同的最高位数
	while(temp1 != temp2) {
		temp1 /= 10
		temp2 /= 10
		bit++
	}
	return if(bit == 1) last else last / 10.pow(bit - 1) * 10.pow(bit - 1) - 1
}
