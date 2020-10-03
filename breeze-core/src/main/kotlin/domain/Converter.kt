// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.domain

interface Converter<in T: Any,out R: Any>{
	fun convert(value:T):R

	fun convertOrNull(value:T):R? = convert(value)
}
