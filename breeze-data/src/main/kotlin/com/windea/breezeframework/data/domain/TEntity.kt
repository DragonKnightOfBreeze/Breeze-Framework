package com.windea.breezeframework.data.domain

import com.windea.breezeframework.core.extensions.*
import com.windea.breezeframework.data.extensions.*
import java.io.*

/**泛型实体类。此类的子类应当是开放的。*/
@Deprecated("使用`equalsByDistinct()`、`hashcodeBySelect()`和`toStringBySelect()`。")
abstract class TEntity<ID> : Serializable {
	open var id: ID? = null
	
	
	@Suppress("DEPRECATION")
	override fun equals(other: Any?): Boolean {
		return (this === other) || (other is TEntity<*> && this::class.java == other::class.java && this.id == other.id)
	}
	
	override fun hashCode(): Int {
		return this.id.hashCode()
	}
	
	override fun toString(): String {
		return this.toPropertyMap().joinToString(", ", "${this::class.java.name}[", "]") { (k, v) -> "$k=$v" }
	}
}
