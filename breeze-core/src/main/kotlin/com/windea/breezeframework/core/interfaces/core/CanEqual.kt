package com.windea.breezeframework.core.interfaces.core

interface CanEqual {
	override fun equals(other: Any?): Boolean
	
	override fun hashCode(): Int
}
