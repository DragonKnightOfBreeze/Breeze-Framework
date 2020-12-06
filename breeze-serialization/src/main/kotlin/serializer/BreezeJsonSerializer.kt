// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.*
import com.windea.breezeframework.serialization.extension.*
import java.lang.reflect.*
import java.time.temporal.*
import java.util.*

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
		return doSerialize(target)
	}

	override fun <T> deserialize(value: String, type: Class<T>): T {
		TODO()
	}

	override fun <T> deserialize(value: String, type: Type): T {
		TODO()
	}

	companion object {
		private const val space = ' '
		private const val doubleQuote = '\"'
		private const val singleQuote = '\''
		private const val separator = ','
		private const val keyValueSeparator = ':'
		private const val arrayPrefix = '['
		private const val arraySuffix = ']'
		private const val objectPrefix = '{'
		private const val objectSuffix = '}'
	}

	data class Config(
		val indent: String = "  ",
		val lineSeparator: String = "\n",
		val doubleQuoted: Boolean = true,
		val unquoteKey: Boolean = false,
		val unquoteValue: Boolean = false,
		val prettyPrint: Boolean = false,
	) {
		val quote = if(doubleQuoted) doubleQuote else singleQuote
		val escapedQuote = "\\" + quote
	}

	data class ConfigBuilder(
		var indent: String = "  ",
		var lineSeparator: String = "\n",
		var doubleQuoted: Boolean = true,
		var unquoteKey: Boolean = false,
		var unquoteValue: Boolean = false,
		var prettyPrint: Boolean = false
	) : Builder<Config> {
		override fun build(): Config {
			return Config(indent, lineSeparator, doubleQuoted, unquoteKey, unquoteValue, prettyPrint)
		}
	}

	private fun <T> doSerialize(target: T): String {
		return StringBuilder(2048).apply { doSerialize(target, this) }.toString()
	}

	private fun <T> doSerialize(target: T, buffer: Appendable, depth: Int = 1) {
		when {
			target == null -> doSerializeNull(buffer)
			target is Boolean -> doSerializeBoolean(target, buffer)
			target is Number -> doSerializeNumber(target, buffer)
			target is String -> doSerializeString(target, buffer)
			target.isStringLike() -> doSerializeString(target.toString(), buffer)
			target is Array<*> -> doSerializeArray(target, buffer, depth)
			target is Iterable<*> -> doSerializeIterable(target, buffer, depth)
			target is Sequence<*> -> doSerializeSequence(target, buffer, depth)
			target is Map<*, *> -> doSerializeMap(target, buffer, depth)
			target.isMapLike() -> doSerializeMap(target.serializeBy(MapLikeSerializer), buffer)
			else -> throw UnsupportedOperationException("Unsupported serialize value type '$target.javaClass.name'.")
		}
	}

	private fun doSerializeKey(target: String, buffer: Appendable) {
		if(config.unquoteKey) {
			target.appendTo(buffer)
		} else {
			config.quote.appendTo(buffer)
			for(c in target) {
				if(c == config.quote) config.escapedQuote.appendTo(buffer) else c.appendTo(buffer)
			}
			//target.appendTo(buffer)
			config.quote.appendTo(buffer)
		}
	}

	private fun doSerializeNull(buffer: Appendable) {
		"null".appendTo(buffer)
	}

	private fun doSerializeBoolean(target: Boolean, buffer: Appendable) {
		target.toString().appendTo(buffer)
	}

	private fun doSerializeNumber(target: Number, buffer: Appendable) {
		target.toString().appendTo(buffer)
	}

	private fun doSerializeString(target: String, buffer: Appendable) {
		//measureNanoTime {
		if(config.unquoteValue) {
			target.appendTo(buffer)
		} else {
			config.quote.appendTo(buffer)
			for(c in target) {
				if(c == config.quote) config.escapedQuote.appendTo(buffer) else c.appendTo(buffer)
			}
			//target.appendTo(buffer)
			config.quote.appendTo(buffer)
		}
		//}.also{ println("doSerializeString $target: $it")}
	}

	private fun doSerializeArray(target: Array<*>, buffer: Appendable, depth: Int = 1) {
		//measureNanoTime {
		arrayPrefix.appendTo(buffer)
		var appendSeparator = false
		for(e in target) {
			if(appendSeparator) separator.appendTo(buffer) else appendSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doSerialize(e, buffer, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
		//}.also{ println("doSerializeArray $target: $it")}
	}

	private fun doSerializeIterable(target: Iterable<*>, buffer: Appendable, depth: Int = 1) {
		//measureNanoTime {
		arrayPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doSerialize(e, buffer, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
		//}.also{ println("doSerializeIterable $target: $it")}
	}

	private fun doSerializeSequence(target: Sequence<*>, buffer: Appendable, depth: Int = 1) {
		//measureNanoTime {
		arrayPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doSerialize(e, buffer, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
		//}.also{ println("doSerializeSequence $target: $it")}
	}

	private fun doSerializeMap(target: Map<*, *>, buffer: Appendable, depth: Int = 1) {
		//measureNanoTime {
		objectPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for((k, v) in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doSerializeKey(k.toString(), buffer)
			keyValueSeparator.appendTo(buffer)
			if(config.prettyPrint) space.appendTo(buffer)
			doSerialize(v, buffer, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		objectSuffix.appendTo(buffer)
		//}.also{ println("doSerializeMap $target: $it")}
	}

	private fun Any?.isStringLike(): Boolean {
		return this is CharSequence || this is Char || this is Temporal || this is Date
	}

	private fun Any?.isMapLike(): Boolean {
		return true
	}
}
