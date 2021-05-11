// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:NotTested

package icu.windea.breezeframework.core.model

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*

/**泛型单例接口。*/
@UnstableApi
@Suppress("UNCHECKED_CAST")
interface TSingleton<T : Any> {
	val instance: T
		get() = singletonMap.getOrPut(this::class.java) { this::class.java.getConstructor().newInstance() } as T

	companion object {
		private val singletonMap = concurrentMapOf<Class<*>, Any>()
	}
}
