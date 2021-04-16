// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("InternalExtensions")

package com.windea.breezeframework.core.extension

import java.util.*

internal fun CharSequence.firstCharToUpperCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].toUpperCase() + this.substring(1)
	}
}

internal fun CharSequence.firstCharToLowerCase(): String {
	return when {
		isEmpty() -> this.toString()
		else -> this[0].toLowerCase() + this.substring(1)
	}
}

private val splitWordsRegex = """\B([A-Z][a-z])""".toRegex()

internal fun CharSequence.splitWords(): String {
	return this.replace(splitWordsRegex, " $1")
}

internal const val defaultDateFormat = "yyyy-MM-yy"
internal const val defaultTimeFormat = "HH:mm:ss"
internal const val defaultDateTimeFormat = "$defaultDateFormat $defaultTimeFormat"
internal val defaultLocale = Locale.getDefault(Locale.Category.FORMAT)
internal val defaultTimeZone = TimeZone.getTimeZone("UTC")

