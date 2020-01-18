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


/**判断当前类型是否是基本类型的包装类型。*/
val <T> Class<T>.isBoxed: Boolean
	get() = java.lang.Byte::class.java == this || java.lang.Short::class.java == this ||
	        java.lang.Integer::class.java == this || java.lang.Long::class.java == this ||
	        java.lang.Float::class.java == this || java.lang.Double::class.java == this ||
	        java.lang.Character::class.java == this || java.lang.Boolean::class.java == this

/**判断当前类型是否是可迭代类型。*/
val <T> Class<T>.isIterable: Boolean get() = Iterable::class.java isDeepAssignableFrom this

/**判断当前类型是否是列表类型。*/
val <T> Class<T>.isList: Boolean get() = List::class.java isDeepAssignableFrom this

/**判断当前类型是否是集类型。*/
val <T> Class<T>.isSet: Boolean get() = Set::class.java isDeepAssignableFrom this

/**判断当前类型是否是映射类型。*/
val <T> Class<T>.isMap: Boolean get() = Map::class.java isDeepAssignableFrom this

/**判断当前类型是否是可序列化类型。*/
val <T> Class<T>.isSerializable: Boolean get() = Serializable::class.java isDeepAssignableFrom this


/**得到当前类型的属性名-取值方法映射。忽略class属性。*/
val <T> Class<T>.getterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("get") && it.name != "getClass" }
		.associateBy { it.name.substring(3).let { s -> s[0].toLowerCase() + s.substring(1, s.length) } }

/**得到当前类型的属性名-赋值方法映射。*/
val <T> Class<T>.setterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("set") }
		.associateBy { it.name.substring(3).let { s -> s[0].toLowerCase() + s.substring(1, s.length) } }
