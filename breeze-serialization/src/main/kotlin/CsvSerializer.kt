// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import java.lang.reflect.*

/**
 * Csv序列化器。
 */
interface CsvSerializer : Serializer{
	override val dataType: DataType get() = DataType.Csv

	//region Csv Serializers
	/**
	 * 默认的Csv序列化器。
	 */
	object BreezeCsvSerializer:CsvSerializer{
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
