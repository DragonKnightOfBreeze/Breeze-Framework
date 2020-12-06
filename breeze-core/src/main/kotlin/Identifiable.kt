// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core

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
@Suppress("METHOD_OF_ANY_IMPLEMENTED_IN_INTERFACE", "UNCHECKED_CAST")
interface Identifiable<T : Serializable> : Serializable {
	val id: T

	override fun equals(other: Any?): Boolean{
		return other != null && other is Identifiable<*> && id == other.id
	}

	override fun hashCode(): Int{
		return id.hashCode()
	}

	class Delegate<T : Serializable> @PublishedApi internal constructor(
		override val id: T,
	) : Identifiable<T> {
		override fun equals(other: Any?) = javaClass == other?.javaClass && id == (other as Identifiable<T>).id

		override fun hashCode(): Int = id.hashCode()
	}

	companion object {
		fun <T : Serializable> delegate(id: T): Delegate<T> = Delegate(id)
	}
}
