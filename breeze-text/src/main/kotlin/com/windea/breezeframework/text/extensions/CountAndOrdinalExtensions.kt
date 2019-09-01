package com.windea.breezeframework.text.extensions

/**转化为序数。*/
fun Int.toOrdinal(): String {
	return this.toString().toOrdinal()
}

/**转化为序数。*/
fun String.toOrdinal(): String {
	return when {
		this.endsWith("1") && this != "11" -> "${this}st"
		this.endsWith("2") && this != "12" -> "${this}nd"
		this.endsWith("3") && this != "13" -> "${this}rd"
		else -> "${this}th"
	}
}
