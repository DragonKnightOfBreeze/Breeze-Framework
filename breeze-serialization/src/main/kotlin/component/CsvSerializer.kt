// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.serialization.extension.*

/**
 * Csv的序列化器。
 */
@BreezeComponent
interface CsvSerializer : DataSerializer {
	override val dataType: DataType get() = DataType.Csv

	/**
	 * 默认的Csv的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用由Breeze Framework实现的轻量的序列化器。
	 */
	companion object Default: CsvSerializer by defaultCsvSerializer

	/**
	 * 由Breeze Framework实现的轻量的Csv的序列化器。
	 */
	class BreezeCsvSerializer : BreezeSerializer.BreezeCsvSerializer()
}
