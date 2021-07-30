// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import java.io.*

data class Holder<out T>(
	val value: T
) : Serializable {
	override fun toString(): String = value.toString()
}
