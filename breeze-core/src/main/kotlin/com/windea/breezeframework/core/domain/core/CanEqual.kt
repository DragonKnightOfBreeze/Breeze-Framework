package com.windea.breezeframework.core.domain.core

/**可相等的对象的接口。*/
interface CanEqual {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}
