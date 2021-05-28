// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("StringBuilderExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

/**将指定的数组加入到当前的字符串构建器。*/
inline fun <T> StringBuilder.appendJoin(
	value: Array<out T>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	noinline transform: ((T) -> CharSequence)? = null,
): StringBuilder {
	return value.joinTo(this, separator, prefix, postfix, limit, truncated, transform)
}

/**将指定的列表加入到当前的字符串构建器。*/
inline fun <T> StringBuilder.appendJoin(
	value: Iterable<T>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	noinline transform: ((T) -> CharSequence)? = null,
): StringBuilder {
	return value.joinTo(this, separator, prefix, postfix, limit, truncated, transform)
}

/**将指定的映射加入到当前的字符串构建器。默认的转化操作是`$k=$v`。*/
inline fun <K, V> StringBuilder.appendJoin(
	value: Map<K, V>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	noinline transform: ((Map.Entry<K, V>) -> CharSequence)? = null,
): StringBuilder {
	return value.joinTo(this, separator, prefix, postfix, limit, truncated, transform)
}
