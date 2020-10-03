// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("NumberAndOrdinalExtensions")

package com.windea.breezeframework.text.extensions

//https://github.com/MehdiK/Humanizer.jvm/blob/master/src/main/kotlin/org/humanizer/jvm/Ordinalize.kt

/**转化为序数形式。*/
fun Int.toOrdinal(): String = getOrdinal(this.toString())

/**转化为序数形式。*/
fun Long.toOrdinal(): String = getOrdinal(this.toString())

private fun getOrdinal(valueSnippet: String): String {
	return when {
		valueSnippet.endsWith("1") && valueSnippet != "11" -> "${valueSnippet}st"
		valueSnippet.endsWith("2") && valueSnippet != "12" -> "${valueSnippet}nd"
		valueSnippet.endsWith("3") && valueSnippet != "13" -> "${valueSnippet}rd"
		else -> "${valueSnippet}th"
	}
}
