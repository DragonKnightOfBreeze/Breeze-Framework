// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE")

package icu.windea.breezeframework.core.model

import java.io.*

/**
 * 可识别的对象。
 *
 * 此接口的实例拥有一个唯一标识符，其`equals`和`hashCode`方法基于此唯一标识符实现。
 * 它们拥有默认的实现，也可以通过[Identifiable.delegate]委托实现。
 *
 * 注意：数据类会覆盖此接口的方法的默认实现。
 *
 * @property id 主键。
 */
interface Identifiable<T : Serializable> : Serializable {
	val id: T

	override fun equals(other: Any?): Boolean {
		return other != null && other is Identifiable<*> && id == other.id
	}

	override fun hashCode(): Int {
		return id.hashCode()
	}

	override fun toString(): String {
		return "${javaClass.name}@${id}"
	}

	class Delegate<T : Serializable> @PublishedApi internal constructor(
		override val id: T,
	) : Identifiable<T> {
		override fun equals(other: Any?): Boolean {
			return javaClass == other?.javaClass && other is Identifiable<*> && id == other.id
		}

		override fun hashCode(): Int {
			return id.hashCode()
		}
	}

	companion object {
		/**
		 * 委托实现一个可识别的对象。
		 */
		fun <T : Serializable> delegate(id: T): Identifiable<T> {
			return Delegate(id)
		}
	}
}
