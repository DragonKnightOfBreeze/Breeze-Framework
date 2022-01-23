// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("CharMatcherExtensions")

package icu.windea.breezeframework.core.component.extension

import icu.windea.breezeframework.core.component.CharMatcher
import icu.windea.breezeframework.core.component.StringMatcher

/**
 * 判断指定的字符是否匹配指定的字符匹配器。
 *
 * @see StringMatcher
 */
fun Char.matchesBy(charMatcher: CharMatcher): Boolean {
	return charMatcher.matches(this)
}
//endregion

//region string matcher extensions
/**
 * 判断指定的字符串是否匹配指定的字符串匹配器。
 *
 * @see StringMatcher
 */
fun String.matchesBy(stringMatcher: StringMatcher): Boolean {
	return stringMatcher.matches(this)
}
