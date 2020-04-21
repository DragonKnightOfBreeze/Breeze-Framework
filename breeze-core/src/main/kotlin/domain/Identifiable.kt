@file:Suppress( "UNCHECKED_CAST")

package com.windea.breezeframework.core.domain

import java.io.*

/**
 * 可识别的类。
 *
 * 此类的实例拥有一个唯一标识符，其`equals`和`hashCode`方法基于此唯一标识符实现。
 *
 * 此接口可以通过[Identifiable.delegate]委托实现。
 */
interface Identifiable<T:Identifiable<T,ID>,ID : Serializable> {
	val id:ID

	override fun equals(other:Any?):Boolean

	override fun hashCode():Int

	override fun toString():String


	/**可识别的类的委托。*/
	class Delegate<T:Identifiable<T,ID>, ID : Serializable> @PublishedApi internal constructor(
		override val id:ID,
		val type:Class<T>
	) : Identifiable<T,ID> {
		override fun equals(other:Any?):Boolean = type == other?.javaClass && id == (other as Identifiable<T,ID>).id

		override fun hashCode():Int = id.hashCode()

		override fun toString():String = "${type.simpleName}(id=$id)"
	}

	companion object {
		/**
		 * 可识别的类的委托方法。用法如下：
		 * ```
		 * class Foo(override val id:Int):Identifiable<Foo,Int> by delegate(id)
		 * ```
		 */
		inline fun <reified T:Identifiable<T,ID>,ID:Serializable> delegate(id:ID):Delegate<T,ID> = Delegate(id, T::class.java)
	}
}
