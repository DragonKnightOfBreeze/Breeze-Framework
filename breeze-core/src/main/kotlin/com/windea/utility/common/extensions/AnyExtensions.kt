package com.windea.commons.kotlin.extension

import com.windea.utility.common.annotations.marks.*
import com.windea.utility.common.enums.*
import kotlin.reflect.full.*

/**序列化当前对象。*/
fun Any.serialize(dataType: DataType): String {
	return this.let { dataType.loader.toString(it) }
}


/**将当前对象转化为对应的成员属性名-属性值映射。*/
@NotSuitable("需要得到扩展属性信息时")
fun Any.toPropertyMap(): Map<String, Any?> {
	return this::class.memberProperties.associate { it.name to it.call(this) }
}

/**将当前对象转化为对应的成员属性名-属性值映射。其中的对象属性将会被递归转化。*/
@NotSuitable("需要得到扩展属性信息时")
fun Any.toDeepPropertyMap(): Map<String, Any?> {
	return this.toPropertyMap().mapValues {
		if(it !is Array<*> && it !is Iterable<*> && it !is Map<*, *>) {
			it.toDeepPropertyMap()
		} else {
			it
		}
	}
}
