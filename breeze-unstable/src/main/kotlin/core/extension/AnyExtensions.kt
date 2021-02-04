// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.model.*

fun <T : Any> T.toBreeze(): Breeze<T> {
	return Breeze(this)
}
