package com.windea.breezeframework.core.domain.core

interface CanEqual {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}
