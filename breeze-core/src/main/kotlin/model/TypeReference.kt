// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.lang.reflect.*

//com.fasterxml.jackson.core.type.TypeReference
//com.alibaba.fastjson.TypeReference

/**
 * 类型引用。
 */
abstract class TypeReference<T> {
	val type: Type

	init {
		val superClass = javaClass.genericSuperclass
		if(superClass !is ParameterizedType) {
			throw IllegalArgumentException("TypeReference must be constructed with actual type information.")
		}
		type = superClass.actualTypeArguments[0]
	}
}
