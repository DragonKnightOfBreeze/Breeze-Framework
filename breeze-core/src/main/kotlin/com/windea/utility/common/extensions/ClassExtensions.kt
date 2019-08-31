package com.windea.utility.common.extensions

import java.io.*
import java.lang.reflect.*

/**判断是否是字符序列。*/
val <T> Class<T>.isCharSequence: Boolean get() = CharSequence::class.java.isAssignableFrom(this::class.java)

/**判断是否是字符串。*/
val <T> Class<T>.isString: Boolean get() = String::class.java.isAssignableFrom(this::class.java)

/**判断是否是可迭代类/接口。*/
val <T> Class<T>.isIterable: Boolean get() = Iterable::class.java.isAssignableFrom(this::class.java)

/**判断是否是列表。*/
val <T> Class<T>.isList: Boolean get() = List::class.java.isAssignableFrom(this::class.java)

/**判断是否是集。*/
val <T> Class<T>.isSet: Boolean get() = Set::class.java.isAssignableFrom(this::class.java)

/**判断是否是映射。*/
val <T> Class<T>.isMap: Boolean get() = Map::class.java.isAssignableFrom(this::class.java)

/**判断是否是可序列化对象。*/
val <T> Class<T>.isSerializable: Boolean get() = Serializable::class.java.isAssignableFrom(this::class.java)


/**得到类型的属性名-取值方法映射。*/
val <T> Class<T>.getterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("get") }.associateBy { it.name.substring(3).firstCharToLowerCase() }


/**得到类型的属性名-赋值方法映射。*/
val <T> Class<T>.setterMap: Map<String, Method>
	get() = this.methods.filter { it.name.startsWith("set") }.associateBy { it.name.substring(3).firstCharToLowerCase() }
