// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import java.lang.reflect.*

/**
 * Tsv序列化器。
 */
interface TsvSerializer : Serializer {
	override val dataType: DataType get()= DataType.Tsv

	//region Tsv Serializers
	/**
	 * 默认的Tsv序列化器。
	 */
	object BreezeTsvSerializer:TsvSerializer{
		override fun <T : Any> serialize(value: T): String {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T : Any> deserialize(value: String, type: Type): T {
			TODO()
		}
	}
	//endregion
}
