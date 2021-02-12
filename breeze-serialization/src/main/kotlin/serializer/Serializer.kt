// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.annotation.*
import java.lang.reflect.*

/**
 * 序列化器。
 *
 * 序列化器用于基于特定的格式，对数据进行序列化和反序列化。
 *
 * @see DataSerializer
 * @see MapLikeSerializer
 */
interface Serializer<V>{
	/**
	 * 序列化指定对象。
	 */
	fun <T> serialize(target:T):V

	/**
	 * 反序列化指定的格式。
	 */
	fun <T> deserialize(value:V,type: Class<T>):T

	/**
	 * 反序列化指定的格式。
	 */
	fun <T> deserialize(value:V,type: Type):T
}
