@file:Suppress("DuplicatedCode")

package com.windea.breezeframework.text.extensions

import com.windea.breezeframework.core.extensions.*
import kotlin.math.*

/**转化为简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Int.toAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toAbbreviation(scale, precision)
}

/**转化为简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Long.toAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toAbbreviation(scale, precision)
}

/**转化为简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Float.toAbbreviation(scale: Int, precision: Int = 0): String {
	require(scale in 1..9) { "Scale must between 1 and 9, but was $scale." }
	
	val scaledValue = this / 10.pow(scale)
	val valueSnippet = if(precision > 0) scaledValue.round(precision).toString() else scaledValue.roundToInt().toString()
	return getAbbreviation(valueSnippet, scale)
}

/**转化为简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Double.toAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toAbbreviation(scale, precision)
}

private fun getAbbreviation(valueSnippet: String, scale: Int): String {
	val s = if(valueSnippet == "1" || valueSnippet == "1.0") "" else "s"
	return when(scale) {
		1 -> "$valueSnippet decade$s"
		2 -> "$valueSnippet hundred$s"
		3 -> "$valueSnippet thousand$s"
		4 -> "$valueSnippet ten thousand$s"
		5 -> "$valueSnippet hundred thousand$s"
		6 -> "$valueSnippet million$s"
		7 -> "$valueSnippet ten million$s"
		8 -> "$valueSnippet hundred million$s"
		9 -> "$valueSnippet billion$s"
		else -> throw IllegalArgumentException()
	}
}


/**转化为中文简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Int.toChsAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toChsAbbreviation(scale, precision)
}

/**转化为中文简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Long.toChsAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toChsAbbreviation(scale, precision)
}

/**转化为中文简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Float.toChsAbbreviation(scale: Int, precision: Int = 0): String {
	require(scale in 1..9) { "Scale must between 1 and 9, but was $scale." }
	
	val scaledValue = this / 10.pow(scale)
	val valueSnippet = if(precision > 0) scaledValue.round(precision).toString() else scaledValue.roundToInt().toString()
	return getChsAbbreviation(valueSnippet, scale)
}

/**转化为中文简写形式。可指定1到9的缩放级别，可指定默认为0的精确度。*/
fun Double.toChsAbbreviation(scale: Int, precision: Int = 0): String {
	return this.toFloat().toChsAbbreviation(scale, precision)
}

private fun getChsAbbreviation(valueSnippet: String, scale: Int): String {
	return when(scale) {
		1 -> "${valueSnippet}十"
		2 -> "${valueSnippet}百"
		3 -> "${valueSnippet}千"
		4 -> "${valueSnippet}万"
		5 -> "${valueSnippet}十万"
		6 -> "${valueSnippet}百万"
		7 -> "${valueSnippet}千万"
		8 -> "${valueSnippet}亿"
		9 -> "${valueSnippet}十亿"
		else -> throw IllegalArgumentException()
	}
}
