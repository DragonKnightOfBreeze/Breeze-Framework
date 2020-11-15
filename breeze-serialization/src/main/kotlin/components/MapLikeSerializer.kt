// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.serialization.extensions.*

/**
 * 类映射对象的序列化器。
 *
 * 用于类映射对象和映射之间的相互转化。
 */
@BreezeComponent
interface MapLikeSerializer:Serializer<Map<String,Any?>> {
	/**
	 * 默认的类映射对象的序列化器。
	 */
	companion object Default:MapLikeSerializer by defaultMapLikeSerializer

	/**
	 * 由Breeze Framework实现的轻量的类映射对象的序列化器。
	 */
	@Suppress("UNCHECKED_CAST")
	class BreezeMapLikeSerializer : BreezeSerializer.BreezeMapLikeSerializer()
}
