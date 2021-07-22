// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.csv

import icu.windea.breezeframework.serialization.*

/**
 * Csv数据的序列化器。
 *
 * @see BreezeCsvSerializer
 */
interface CsvSerializer : DataSerializer {
	override val dataFormat: DataFormat get() = DataFormat.Csv

	/**
	 * 默认的Csv数据的序列化器。
	 *
	 * 可以由第三方库委托实现，基于classpath进行推断，或者使用框架本身实现的序列化器。
	 */
	companion object Default: CsvSerializer by defaultCsvSerializer
}
