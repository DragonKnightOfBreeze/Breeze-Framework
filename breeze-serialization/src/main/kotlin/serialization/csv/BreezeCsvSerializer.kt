// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.csv

import icu.windea.breezeframework.serialization.*
import java.lang.reflect.*

/**
 * 框架本身实现的Csv数据的序列化器。
 */
class BreezeCsvSerializer(
	val config: CsvConfig = CsvConfig()
) : CsvSerializer, BreezeSerializer {
	private val reader = CsvReader(config)
	private val writer = CsvWriter(config)

	override fun <T> serialize(target: T): String {
		return writer.write(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		return reader.read(value, type)
	}

	override fun <T> deserialize(value: String, type: Type): T {
		return reader.read(value, type)
	}
}
