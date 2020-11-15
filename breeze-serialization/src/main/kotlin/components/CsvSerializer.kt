// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.serialization.extensions.defaultCsvSerializer
import java.lang.reflect.*

/**
 * Csv序列化器。
 */
@BreezeComponent
interface CsvSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Csv

	//region Csv Serializers
	/**
	 * 默认的Csv序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: CsvSerializer by defaultCsvSerializer

	/**
	 * 框架本身实现的Csv序列化器。
	 */
	class BreezeCsvSerializer: CsvSerializer {
		override fun <T> serialize(target: T): String {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Class<T>): T {
			TODO()
		}

		override fun <T> deserialize(value: String, type: Type): T {
			TODO()
		}
	}
	//endregion
}
