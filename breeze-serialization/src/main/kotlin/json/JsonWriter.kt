// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization.json

import icu.windea.breezeframework.serialization.*
import java.time.temporal.*
import java.util.*

/**
 * Json数据的写入器。
 */
internal class JsonWriter @PublishedApi internal constructor(
	override val config: JsonConfig = JsonConfig()
): DataWriter {
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

	private val buffer: StringBuffer = StringBuffer(2048)

	override fun <T> write(value: T): String {
		doWrite(value)
		return buffer.toString()
	}

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
			buffer.append(target)
		} else {
			buffer.append(quote)
			for(c in target) {
				if(c == quote) buffer.append(escapedQuote) else buffer.append(c)
			}
			//target.appendTo(buffer)
			buffer.append(quote)
		}
	}

	private fun doWriteNull() {
		buffer.append("null")
	}

	private fun doWriteBoolean(target: Boolean) {
		buffer.append(target.toString())
	}

	private fun doWriteNumber(target: Number) {
		buffer.append(target.toString())
	}

	private fun doWriteString(target: String) {
		if(config.unquoteValue) {
			buffer.append(target)
		} else {
			buffer.append(quote)
			for(c in target) {
				if(c == quote) buffer.append(escapedQuote) else buffer.append(c)
			}
			//target.appendTo(buffer)
			buffer.append(quote)
		}
	}

	private fun doWriteArray(target: Array<*>, depth: Int = 1) {
		buffer.append(arrayPrefix)
		var appendSeparator = false
		for(e in target) {
			if(appendSeparator) buffer.append(separator) else appendSeparator = true
			if(config.prettyPrint) {
				buffer.append(config.lineSeparator)
				repeat(depth) { buffer.append(config.indent) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			buffer.append(config.lineSeparator)
			if(depth != 1) repeat(depth - 1) { buffer.append(config.indent) }
		}
		buffer.append(arraySuffix)
	}

	private fun doWriteIterable(target: Iterable<*>, depth: Int = 1) {
		buffer.append(arrayPrefix)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) buffer.append(separator) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				buffer.append(config.lineSeparator)
				repeat(depth) { buffer.append(config.indent) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			buffer.append(config.lineSeparator)
			if(depth != 1) repeat(depth - 1) { buffer.append(config.indent) }
		}
		buffer.append(arraySuffix)
	}

	private fun doWriteSequence(target: Sequence<*>, depth: Int = 1) {
		buffer.append(arrayPrefix)
		var shouldWriteSeparator = false
		for(e in target) {
			if(shouldWriteSeparator) buffer.append(separator) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				buffer.append(config.lineSeparator)
				repeat(depth) { buffer.append(config.indent) }
			}
			doWrite(e, depth + 1)
		}
		if(config.prettyPrint) {
			buffer.append(config.lineSeparator)
			if(depth != 1) repeat(depth - 1) { buffer.append(config.indent) }
		}
		buffer.append(arraySuffix)
	}

	private fun doWriteMap(target: Map<*, *>, depth: Int = 1) {
		buffer.append(objectPrefix)
		var shouldWriteSeparator = false
		for((k, v) in target) {
			if(shouldWriteSeparator) buffer.append(separator) else shouldWriteSeparator = true
			if(config.prettyPrint) {
				buffer.append(config.lineSeparator)
				repeat(depth) { buffer.append(config.indent) }
			}
			doWriteKey(k.toString())
			buffer.append(keyValueSeparator)
			if(config.prettyPrint) buffer.append(space)
			doWrite(v, depth + 1)
		}
		if(config.prettyPrint) {
			buffer.append(config.lineSeparator)
			if(depth != 1) repeat(depth - 1) { buffer.append(config.indent) }
		}
		buffer.append(objectSuffix)
	}

	private fun Any?.isStringLike(): Boolean {
		return this is CharSequence || this is Char || this is Temporal || this is Date
	}

	private fun Any?.isMapLike(): Boolean {
		return true
	}
}
