@file:JvmName("ClassExtensions")

package com.windea.breezeframework.reflect.extensions

import java.io.*
import java.lang.reflect.*

/**判断当前类型是否可从指定类型分配。即，判断当前类型是否是另一类型的直接父类型。*/
infix fun <T> Class<T>.isAssignableFrom(clazz: Class<*>): Boolean = this.isAssignableFrom(clazz)

/**递归判断当前类型是否可从指定类型分配。即，判断当前类型是否是另一类型的父类型。*/
infix fun <T> Class<T>.isDeepAssignableFrom(clazz: Class<*>): Boolean =
	this.isAssignableFrom(clazz) || clazz.superclass?.let { this.isDeepAssignableFrom(it) } ?: false ||
	clazz.interfaces.any { this.isDeepAssignableFrom(it) }


/**判断是否是字符序列。*/
val <T> Class<T>.isCharSequence: Boolean get() = CharSequence::class.java isDeepAssignableFrom this

/**判断是否是字符串。*/
val <T> Class<T>.isString: Boolean get() = String::class.java == this

/**判断是否是可迭代类/接口。*/
val <T> Class<T>.isIterable: Boolean get() = Iterable::class.java isDeepAssignableFrom this

/**判断是否是列表。*/
val <T> Class<T>.isList: Boolean get() = List::class.java isDeepAssignableFrom this

/**判断是否是集。*/
val <T> Class<T>.isSet: Boolean get() = Set::class.java isDeepAssignableFrom this

/**判断是否是映射。*/
val <T> Class<T>.isMap: Boolean get() = Map::class.java isDeepAssignableFrom this

/**判断是否是可序列化对象。*/
val <T> Class<T>.isSerializable: Boolean get() = Serializable::class.java isDeepAssignableFrom this


/**得到类型的属性名-取值方法映射。忽略class属性。*/
val <T> Class<T>.getterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("get") && it.name != "getClass" }
		.associateBy { it.name.substring(3).let { s -> s[0].toLowerCase() + s.substring(1, s.length) } }

/**得到类型的属性名-赋值方法映射。*/
val <T> Class<T>.setterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("set") }
		.associateBy { it.name.substring(3).let { s -> s[0].toLowerCase() + s.substring(1, s.length) } }
