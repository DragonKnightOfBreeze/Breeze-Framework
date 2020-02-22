@file:NotTested

package com.windea.breezeframework.core.domain

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*

/**泛型单例接口。*/
@Suppress("UNCHECKED_CAST")
interface TSingleton<T : Any> {
	val instance: T
		get() = singletonMap.getOrPut(this::class.java) { this::class.java.getConstructor().newInstance() } as T

	companion object {
		private val singletonMap = concurrentMapOf<Class<*>, Any>()
	}
}
