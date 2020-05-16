@file:JvmName("ClassExtensions")

package com.windea.breezeframework.reflect.extensions

import java.io.*
import java.lang.reflect.*

/**判断当前类型是否是基本类型的包装类型。*/
val <T> Class<T>.isBoxed:Boolean
	get() = java.lang.Byte::class.java == this || java.lang.Short::class.java == this ||
	        java.lang.Integer::class.java == this || java.lang.Long::class.java == this ||
	        java.lang.Float::class.java == this || java.lang.Double::class.java == this ||
	        java.lang.Character::class.java == this || java.lang.Boolean::class.java == this

/**判断当前类型是否是字符序列类型。*/
val <T> Class<T>.isCharSequence:Boolean get() = CharSequence::class.java.isAssignableFrom(this)

/**判断当前类型是否是可迭代类型。*/
val <T> Class<T>.isIterable:Boolean get() = Iterable::class.java.isAssignableFrom(this)

/**判断当前类型是否是列表类型。*/
val <T> Class<T>.isList:Boolean get() = List::class.java.isAssignableFrom(this)

/**判断当前类型是否是集类型。*/
val <T> Class<T>.isSet:Boolean get() = Set::class.java.isAssignableFrom(this)

/**判断当前类型是否是映射类型。*/
val <T> Class<T>.isMap:Boolean get() = Map::class.java.isAssignableFrom(this)

/**判断当前类型是否是可序列化类型。*/
val <T> Class<T>.isSerializable:Boolean get() = Serializable::class.java.isAssignableFrom(this)


/**得到当前类型的所有公开的取值方法。忽略`getClass()`。*/
val <T> Class<T>.getters:List<Method>
	get() = this.methods.filter { it.name.startsWith("get") && it.name != "getClass" && it.parameterCount == 0 }

/**得到当前类型的所有公开的赋值方法。*/
val <T> Class<T>.setters:List<Method>
	get() = this.methods.filter { it.name.startsWith("set") && it.parameterCount == 1 }
