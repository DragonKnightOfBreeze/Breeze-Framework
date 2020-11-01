// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.*

inline fun <T : Any> T.toBreeze(): Breeze<T> {
	return Breeze(this)
}
