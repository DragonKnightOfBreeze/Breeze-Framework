package com.windea.utility.common.domain

import com.windea.commons.kotlin.extension.*
import com.windea.utility.common.extensions.*
import java.io.*
import java.util.*

/**泛型实体类。此类的子类应当是开放的。*/
@Deprecated("使用`list.distinct{ it.id }`保证唯一性，使用`entity.toPropertyMap()`得到属性信息。")
abstract class TEntity<ID> : Serializable {
	open var id: ID? = null
	
	
	override fun equals(other: Any?): Boolean {
		return (this === other) || (other is TEntity<*> && this::class.java == other::class.java && this.id == other.id)
	}
	
	override fun hashCode(): Int {
		return Objects.hash(super.hashCode(), this.id.hashCode())
	}
	
	override fun toString(): String {
		return this.toPropertyMap().joinToString(", ", "${this::class.java.name}[", "]") { (k, v) -> "$k=$v" }
	}
}
