/*******************************************************************************
 * Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
 * Breeze is blowing...
 ******************************************************************************/

@file:Suppress("UNCHECKED_CAST")

package com.windea.breezeframework.core.domain

import java.io.*

/**
 * 可识别的对象。
 *
 * 此接口的实例拥有一个唯一标识符，其`equals`和`hashCode`方法基于此唯一标识符实现。
 *
 * 此接口可以通过[Identifiable.delegate]委托实现。
 *
 * @property id 主键。
 */
interface Identifiable<T : Serializable>:Serializable {
	val id: T

	override fun equals(other: Any?): Boolean

	override fun hashCode(): Int

	override fun toString(): String

	/**
	 * 可识别的类的委托。
	 */
	class Delegate<T : Serializable> @PublishedApi internal constructor(
		override val id: T
	) : Identifiable<T> {
		override fun equals(other: Any?): Boolean {
			return javaClass == other?.javaClass && id == (other as Identifiable<T>).id
		}

		override fun hashCode(): Int = id.hashCode()

		override fun toString(): String = "${javaClass.simpleName}(id=$id)"
	}

	companion object {
		/**
		 * 可识别的类的委托方法。用法如下：
		 * ```
		 * class Foo(override val id: Int): Identifiable<Foo, Int> by delegate(id)
		 * ```
		 */
		fun <T:Serializable> delegate(id: T): Delegate<T> = Delegate(id)
	}
}
