// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.serialization.io.*
import java.lang.reflect.*

/**
 * 由Breeze Framework实现的轻量的Json的序列化器。
 */
class BreezeJsonSerializer : JsonSerializer, BreezeSerializer, Configurable<BreezeJsonSerializer.ConfigBuilder> {
	constructor()

	constructor(configBuilder: ConfigBuilder) {
		this.configBuilder = configBuilder
	}

	constructor(configBuilder: ConfigBuilder.() -> Unit) {
		this.configBuilder = ConfigBuilder().apply(configBuilder)
	}

	private var configBuilder: ConfigBuilder = ConfigBuilder()
	val config by lazy { configBuilder.build() }

	override fun configure(block: ConfigBuilder.() -> Unit) {
		configBuilder = ConfigBuilder().apply(block)
	}

	override fun <T> serialize(target: T): String {
		return JsonWriter(config).write(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Type): T {
		TODO()
	}

	data class Config(
		val indent: String = "  ",
		val lineSeparator: String = "\n",
		val doubleQuoted: Boolean = true,
		val unquoteKey: Boolean = false,
		val unquoteValue: Boolean = false,
		val prettyPrint: Boolean = false
	)

	data class ConfigBuilder(
		var indent: String = "  ",
		var lineSeparator: String = "\n",
		var doubleQuoted: Boolean = true,
		var unquoteKey: Boolean = false,
		var unquoteValue: Boolean = false,
		var prettyPrint: Boolean = false
	) : Builder<Config> {
		override fun build() = Config(indent, lineSeparator, doubleQuoted, unquoteKey, unquoteValue, prettyPrint)
	}
}
