package com.windea.breezeframework.core.extensions

import kotlin.reflect.*

/**通过选择并比较指定类型中的属性，判断两个对象是否相等。*/
inline fun <reified T> equalsBySelect(target: T?, other: Any?, selector: T.() -> Array<*>): Boolean {
	if(target === other) return true
	if(target == null) return false
	if(other !is T) return false
	return (target.selector() zip other.selector()).all { (a, b) -> a == b }
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。*/
inline fun <reified T> hashcodeBySelect(target: T?, selector: T.() -> Array<*>): Int {
	if(target == null) return 0
	val result = 0
	return target.selector().fold(result) { r, k -> 31 * r + k.hashCode() }
}

/**通过选择指定类型中的属性（属性名-属性值），将指定对象转化为字符串。*/
inline fun <reified T> toStringBySelect(target: T?, selector: T.() -> Array<Pair<String, *>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector().toMap()
		.mapValues { (_, v) -> v.let { if(it is String) "'$it'" else it.toString() } }
		.joinToString()
	return "$className($propertiesSnippet)"
}

/**通过选择指定类型中的属性引用，将指定对象转化为字符串。*/
inline fun <reified T> toStringBySelectRef(target: T?, selector: T.() -> Array<KProperty0<*>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector()
		.associate { prop -> prop.name to prop.get().let { v -> if(v is String) "'$v'" else v.toString() } }
		.joinToString()
	return "$className($propertiesSnippet)"
}
