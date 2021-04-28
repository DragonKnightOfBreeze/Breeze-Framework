// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.model.*

fun <T : Any> T.toBreeze(): Breeze<T> {
	return Breeze(this)
}
