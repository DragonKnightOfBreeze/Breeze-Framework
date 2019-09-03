@file:Reference("[Humanizer](https://github.com/MehdiK/Humanizer.jvm/blob/master/src/main/kotlin/org/humanizer/jvm/Ordinalize.kt)")

package com.windea.breezeframework.text.extensions

import com.windea.breezeframework.core.annotations.internal.*

/**转化为序数。*/
fun Int.toOrdinal(): String = this.toString().toOrdinal()

/**转化为序数。*/
fun Long.toOrdinal(): String = this.toString().toOrdinal()

/**转化为序数。*/
fun String.toOrdinal(): String {
	return when {
		this.endsWith("1") && this != "11" -> "${this}st"
		this.endsWith("2") && this != "12" -> "${this}nd"
		this.endsWith("3") && this != "13" -> "${this}rd"
		else -> "${this}th"
	}
}
