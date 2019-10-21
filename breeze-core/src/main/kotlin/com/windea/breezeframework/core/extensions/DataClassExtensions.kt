package com.windea.breezeframework.core.extensions

import kotlin.reflect.*

//https://github.com/consoleau/kassava

//NOTE 为了避免污染Any?的代码提示，不作为扩展方法
//DONE 特殊对待数组类型
//DONE toString时可以省略值为null的属性
//TODO 考虑父类

/**通过选择并比较指定类型中的属性，判断两个对象是否相等。*/
inline fun <reified T> equalsBySelect(target: T?, other: Any?, selector: T.() -> Array<*>): Boolean {
	if(target === other) return true
	if(target == null) return false
	if(other !is T) return false
	return (target.selector() zip other.selector()).all { (a, b) ->
		//treat Arrays specially
		if(a is Array<*> && b is Array<*>) a contentDeepEquals b else a == b
	}
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。*/
inline fun <reified T> hashcodeBySelect(target: T?, selector: T.() -> Array<*>): Int {
	if(target == null) return 0
	val result = 0
	return target.selector().fold(result) { r, k -> 31 * r + k.hashCode() }
}

/**通过选择指定类型中的属性（属性名-属性值），将指定对象转化为字符串。*/
inline fun <reified T> toStringBySelect(target: T?, omitNulls: Boolean = false,
	selector: T.() -> Array<Pair<String, *>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector().toMap()
		.where(omitNulls) { it.filter { (_, v) -> v == null } } //TODO
		.mapValues { (_, v) ->
			//treat Arrays specially
			if(v is Array<*>) v.contentDeepToString() else v.toString()
		}.joinToString()
	return "$className($propertiesSnippet)"
}

/**通过选择指定类型中的属性引用，将指定对象转化为字符串。*/
inline fun <reified T> toStringBySelectRef(target: T?, omitNulls: Boolean = false,
	selector: T.() -> Array<KProperty0<*>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector().associate { it.name to it.get() }
		.where(omitNulls) { it.filter { (_, v) -> v == null } } //TODO
		.mapValues { (_, v) ->
			//treat Arrays specially
			if(v is Array<*>) v.contentDeepToString() else v.toString()
		}.joinToString()
	return "$className($propertiesSnippet)"
}
