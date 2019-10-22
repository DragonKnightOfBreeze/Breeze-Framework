package com.windea.breezeframework.core.interfaces

interface CanEqual {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}
