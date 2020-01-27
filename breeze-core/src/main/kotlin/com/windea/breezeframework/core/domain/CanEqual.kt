package com.windea.breezeframework.core.domain

/**可相等的类。*/
interface CanEqual {
	override fun equals(other: Any?): Boolean

	override fun hashCode(): Int
}
