package com.windea.breezeframework.data.extensions

import com.windea.breezeframework.core.extensions.*
import kotlin.reflect.*

//REGION equals, hashcode ad toString extensions

/**通过比较指定类型中的属性，判断两个对象是否相等。不需要调用反射api。*/
inline fun <reified T> equalsByPredicate(target: T?, other: Any?, predicate: (T, T) -> Boolean): Boolean {
	if(target === other) return true
	if(target == null) return false
	if(other !is T) return false
	return predicate(target, other)
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。不需要调用反射api。*/
inline fun <reified T> hashcodeBySelect(target: T?, selector: (T) -> Array<*>): Int {
	if(target == null) return 0
	val result = 0
	return selector(target).fold(result) { r, k -> 31 * r + k.hashCode() }
}

/**通过选择指定类型中的顺序，将指定对象转化为字符串。不需要调用反射api。*/
inline fun <reified T> toStringBySelect(target: T?, selector: (T) -> Array<Pair<KProperty<*>, *>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = selector(target).toMap()
		.map { (k, v) ->
			k.name to v.let { if(it is String) "'$it'" else it.toString() }
		}.toMap().joinToString()
	return "$className($propertiesSnippet)"
}
