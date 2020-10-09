// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

/**
 * 转化器。
 *
 * 转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 */
interface Converter<in T: Any,out R: Any>{
	fun convert(value:T):R

	fun convertOrNull(value:T):R? = convert(value)
}

