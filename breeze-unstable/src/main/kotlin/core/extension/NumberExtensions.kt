// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.model.*
import java.text.*
import java.util.*

//region Format Extensions
/**根据指定的格式化类型，格式化当前数字。可以指定可选的语言环境。*/
@UnstableApi
fun Number.formatBy(type: NumberFormatType, locale: Locale? = null): String {
	return getNumberFormatInstance(type, locale ?: Locale.getDefault(Locale.Category.FORMAT)).format(this)
}

/**根据指定的格式化类型，格式化当前数字。可以指定可选的语言环境。可以进行额外的配置。*/
@UnstableApi
fun Number.formatBy(type: NumberFormatType, locale: Locale? = null, configBlock: NumberFormat.() -> Unit): String {
	return getNumberFormatInstance(type, locale ?: Locale.getDefault(Locale.Category.FORMAT)).apply(configBlock).format(this)
}

private fun getNumberFormatInstance(type: NumberFormatType, locale: Locale): NumberFormat {
	return when(type) {
		NumberFormatType.Default -> NumberFormat.getInstance(locale)
		NumberFormatType.Number -> NumberFormat.getNumberInstance(locale)
		NumberFormatType.Integer -> NumberFormat.getIntegerInstance(locale)
		NumberFormatType.Percent -> NumberFormat.getPercentInstance(locale)
		NumberFormatType.Currency -> NumberFormat.getCurrencyInstance(locale)
		else -> throw UnsupportedOperationException("Target number format type is not yet supported.")
	}
}
//endregion
