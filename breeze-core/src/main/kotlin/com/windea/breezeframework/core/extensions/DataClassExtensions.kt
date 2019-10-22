package com.windea.breezeframework.core.extensions

import java.util.*
import kotlin.reflect.*

//https://github.com/consoleau/kassava

//NOTE 为了避免污染Any?的代码提示，不要作为扩展方法
//DONE 特殊对待数组类型
//DONE toString时可以省略值为null的属性
//DONE toString时可以考虑父类型的toString
//DONE 可以考虑是否进行深层判断

/**通过选择并比较指定类型中的属性，判断两个对象是否相等。特殊对待数组类型。*/
inline fun <reified T> equalsBySelect(target: T?, other: Any?, deepOperation: Boolean = false,
	selector: T .() -> Array<*>): Boolean {
	if(target === other) return true
	if(target == null) return false
	if(other !is T) return false
	return (target.selector() zip other.selector()).all { (a, b) ->
		//NOTE treat Arrays specially
		if(a is Array<*> && b is Array<*>)
			if(deepOperation) a contentDeepEquals b else a contentEquals b
		else a == b
	}
}

/**通过选择指定类型中的属性，得到指定对象的哈希码。特殊对待数组类型。*/
inline fun <reified T> hashCodeBySelect(target: T?, deepOperation: Boolean = false, selector: T.() -> Array<*>): Int {
	Objects.hash()
	if(target == null) return 0
	val result = 0
	return target.selector().fold(result) { r, k ->
		//NOTE treat Arrays specially, but `Objects.hash` do NOT
		31 * r + (if(k is Array<*>)
			if(deepOperation) k.contentDeepHashCode() else k.contentHashCode()
		else k.hashCode())
	}
}

/**通过选择指定类型中的属性，将指定对象转化为字符串。特殊对待数组类型。*/
inline fun <reified T> toStringBySelect(target: T?, omitNulls: Boolean = false, deepOperation: Boolean = false,
	selector: T.() -> Array<Pair<String, *>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector().toMap()
		.where(omitNulls) { it.filterValueNotNull() }
		.mapValues { (_, v) ->
			//NOTE treat Arrays specially
			if(v is Array<*>)
				if(deepOperation) v.contentDeepToString() else v.contentToString()
			else v.toString()
		}.joinToString()
	return "$className($propertiesSnippet)"
}

/**通过选择指定类型中的属性引用，将指定对象转化为字符串。特殊对待数组类型。*/
inline fun <reified T> toStringBySelectRef(target: T?, omitNulls: Boolean = false, deepOperation: Boolean = false,
	superToString: String? = null,
	selector: T.() -> Array<KProperty0<*>>): String {
	if(target == null) return "null"
	val className = T::class.java.simpleName
	val propertiesSnippet = target.selector().associate { it.name to it.get() }
		.where(omitNulls) { it.filterValueNotNull() }
		.mapValues { (_, v) ->
			//NOTE treat Arrays specially
			if(v is Array<*>)
				if(deepOperation) v.contentDeepToString() else v.contentToString()
			else v.toString()
		}.joinToString()
	val superToStringSnippet = superToString?.let { ", $it" }.orEmpty()
	return "$className($propertiesSnippet$superToStringSnippet)"
}
