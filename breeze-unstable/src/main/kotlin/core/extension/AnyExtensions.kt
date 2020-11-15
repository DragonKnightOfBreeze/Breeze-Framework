// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.model.*

inline fun <T : Any> T.toBreeze(): Breeze<T> {
	return Breeze(this)
}
