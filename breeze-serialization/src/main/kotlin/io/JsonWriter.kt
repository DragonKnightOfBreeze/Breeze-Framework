// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.io

import com.windea.breezeframework.serialization.extension.*
import com.windea.breezeframework.serialization.serializer.*
import java.time.temporal.*
import java.util.*

/**
 * Json数据的写入器。
 */
class JsonWriter @PublishedApi internal constructor(
	private val config: BreezeJsonSerializer.Config = BreezeJsonSerializer.Config()
): DataWriter {
	private val buffer:StringBuffer = StringBuffer(2048)

	override fun <T> write(target: T): String {
		doWrite(target)
		return buffer.toString()
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

	private val quote = if(config.doubleQuoted) doubleQuote else singleQuote
	private val escapedQuote = "\\" + quote

	private fun <T> doWrite(target: T, depth: Int = 1) {
		when {
			target == null -> doWriteNull()
			target is Boolean -> doWriteBoolean(target)
			target is Number -> doWriteNumber(target)
			target is String -> doWriteString(target)
			target.isStringLike() -> doWriteString(target.toString())
			target is Array<*> -> doWriteArray(target, depth)
			target is Iterable<*> -> doWriteIterable(target, depth)
			target is Sequence<*> -> doWriteSequence(target, depth)
			target is Map<*, *> -> doWriteMap(target, depth)
			target.isMapLike() -> doWriteMap(target.serializeBy(MapLikeSerializer))
			else -> throw UnsupportedOperationException("Unsupported value type '${target.javaClass.name}'.")
		}
	}

	private fun doWriteKey(target: String) {
		if(config.unquoteKey) {
			target.appendTo(buffer)
		} else {
			quote.appendTo(buffer)
			for(c in target) {
				if(c == quote) escapedQuote.appendTo(buffer) else c.appendTo(buffer)
			}
			//target.appendTo(buffer)
			quote.appendTo(buffer)
		}
	}

	private fun doWriteNull() {
		"null".appendTo(buffer)
	}

	private fun doWriteBoolean(target: Boolean) {
		target.toString().appendTo(buffer)
	}

	private fun doWriteNumber(target: Number) {
		target.toString().appendTo(buffer)
	}

	private fun doWriteString(target: String) {
		if(config.unquoteValue) {
			target.appendTo(buffer)
		} else {
			quote.appendTo(buffer)
			for(c in target) {
				if(c == quote) escapedQuote.appendTo(buffer) else c.appendTo(buffer)
			}
			//target.appendTo(buffer)
			quote.appendTo(buffer)
		}
	}

	private fun doWriteArray(target: Array<*>, depth: Int = 1) {
		arrayPrefix.appendTo(buffer)
		var appendSeparator = false
		for(e in target) {
			if(appendSeparator) separator.appendTo(buffer) else appendSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
	}

	private fun doWriteIterable(target: Iterable<*>, depth: Int = 1) {
		arrayPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
	}

	private fun doWriteSequence(target: Sequence<*>, depth: Int = 1) {
		arrayPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		arraySuffix.appendTo(buffer)
	}

	private fun doWriteMap(target: Map<*, *>, depth: Int = 1) {
		objectPrefix.appendTo(buffer)
		var shouldWriteSeparator = false
		for((k, v) in target) {
			if(shouldWriteSeparator) separator.appendTo(buffer) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				config.lineSeparator.appendTo(buffer)
				repeat(depth) { config.indent.appendTo(buffer) }
			}
			doWriteKey(k.toString())
			keyValueSeparator.appendTo(buffer)
			if(config.prettyPrint) space.appendTo(buffer)
			doWrite(v, depth + 1)
		}
		if(config.prettyPrint) {
			config.lineSeparator.appendTo(buffer)
			if(depth != 1) repeat(depth - 1) { config.indent.appendTo(buffer) }
		}
		objectSuffix.appendTo(buffer)
	}

	private fun Any?.isStringLike(): Boolean {
		return this is CharSequence || this is Char || this is Temporal || this is Date
	}

	private fun Any?.isMapLike(): Boolean {
		return true
	}
}
