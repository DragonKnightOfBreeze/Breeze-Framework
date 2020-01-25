@file:JvmName("RegexExtensions")

package com.windea.breezeframework.core.extensions

/**得到指定字符串对于当前正则表达式的匹配分组。*/
fun Regex.matchGroups(string: String): MatchGroupCollection? = this.matchEntire(string)?.groups

/**得到指定字符串对于当前正则表达式的匹配字符串分组。*/
fun Regex.matchGroupValues(string: String): List<String>? = this.matchEntire(string)?.groupValues


/**得到当前匹配结果的指定索引的匹配字符串。*/
operator fun MatchResult.get(index: Int): String = this.groupValues[index]


//DELAY 过于复杂的代码逻辑
/**
 * 接收整数范围，转化为合法的正则表达式字符串。
 *
 * 例如：`[2-13] -> [1][0-3]|[3-9]`。
 */
fun Regex.Companion.fromIntRange(first: Int, last: Int): String {
	return when {
		//如果a大于b，则抛出异常
		first > last -> throw IllegalArgumentException("first $first is greater than last $last.")
		//如果a等于b，则直接返回这个数值
		first == last -> return first.toString()
		//否则做进一步的复杂逻辑处理
		else -> {
			//每次只能改变一位数，其他必须相同，或者是0-9
			//如果较高高位全部相同，则只有一种情况，如 [123-127]->123

			//1. 找出比a大，比b小，然后最后一位是9的数
			//2. 找出比(a/10+1)*10大，比b小，然后最后两位数尽可能是9的数
			//3. 重复以上步骤，直到最后找出的数+1等于b%10^n*10^n n=位数-1
			//4. 这时对应的数的最高位是相等的，忽略最高位，从步骤1重新开始
			//5. 循环直到已经找不出满足条件的最后一位是9的数，这时只能找出b
			val pairs = mutableListOf<Pair<Int, Int>>()
			var firstLimit = first
			var lastLimit = toNumberWhereLastBitIs9In(firstLimit, last)
			while(lastLimit < last) {
				firstLimit = toNumberWhereLastBitIs0Gt(first)
				lastLimit = toNumberWhereLastBitIs9In(firstLimit, last)
				pairs.add(firstLimit to lastLimit)
				if(lastLimit + 1 == toNumberWhereRoundToFirstBit(last)) {
					TODO()
				}
			}
			pairs.joinToString("]|[", "[", "]") {
				//参考：10-13|2-9 ->  -> 1][0-3 2-9 -> [1][0-3]|[2-9]
				(a, b) ->
				a.toString().toCharArray().zip(b.toString().toCharArray()) { a1, b1 -> "$a1-$b1" }.joinToString("][")
			}
		}
	}
}

private fun toNumberWhereRoundToFirstBit(a: Int): Int {
	TODO()
}

private fun toNumberWhereLastBitIs0Gt(a: Int): Int {
	TODO()
}

private fun toNumberWhereLastBitIs9In(a: Int, b: Int): Int {
	TODO()
}

