package com.windea.breezeframework.core.domain.core

/**可克隆的对象的接口。*/
interface Cloneable<T : kotlin.Cloneable> : kotlin.Cloneable {
	@Suppress("UNCHECKED_CAST")
	override fun clone(): T {
		return super.clone() as T
	}
}
