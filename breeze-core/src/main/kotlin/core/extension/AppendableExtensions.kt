// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("AppendableExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package icu.windea.breezeframework.core.extension

inline fun <A :Appendable> A.appendIf(value:CharSequence, condition:Boolean):A{
	if(condition) append(value)
	return this
}

inline fun <A: Appendable> A.appendIf(value:CharSequence, predicate: ()->Boolean):A{
	if(predicate()) append(value)
	return this
}

inline fun <A : Appendable, T> A.appendJoinWith(
	target: Array<out T>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: (T) -> Unit
): A {
	append(prefix)
	var count = 0
	for(element in target) {
		if(++count > 1) append(separator)
		if(limit < 0 || count <= limit) transform(element) else break
	}
	if(limit in 0 until count) append(truncated)
	append(postfix)
	return this
}

inline fun <A : Appendable, T> A.appendJoinWith(
	target: Iterable<T>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: (T) -> Unit
): A {
	append(prefix)
	var count = 0
	for(element in target) {
		if(++count > 1) append(separator)
		if(limit < 0 || count <= limit) transform(element) else break
	}
	if(limit in 0 until count) append(truncated)
	append(postfix)
	return this
}

inline fun <A : Appendable, K, V> A.appendJoinWith(
	value: Map<K, V>, separator: CharSequence = ", ", prefix: CharSequence = "",
	postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...",
	transform: (Map.Entry<K, V>) -> Unit
): A {
	return appendJoinWith(value.entries, separator, prefix, postfix, limit, truncated, transform)
}
