// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import icu.windea.breezeframework.core.annotation.*
import java.io.*

/**拥有唯一标识符的泛型实体类。此类的子类应当是开放的。*/
@UnstableApi
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")
abstract class TEntity<ID> : Serializable {
	open var id: ID? = null

	@Suppress("DEPRECATION")
	override fun equals(other: Any?): Boolean {
		if(this === other) return true
		if(javaClass != other?.javaClass) return false
		other as TEntity<*>
		if(id != other.id) return false
		return true
	}

	override fun hashCode(): Int {
		return id?.hashCode() ?: 0
	}
}
