// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.component

import com.windea.breezeframework.serialization.*
import java.io.*

/**
 * 数据的读取器。
 */
class DataWriter(
	private val writer: Writer? = null,
	private val initialSize: Int = 2048
) : Writer() {
	private val bufferLocal = ThreadLocal.withInitial { CharArray(initialSize) }
	private var bufferThreshold = 1024 * 128
	private var buffer = bufferLocal.get()
	private var count = 0
	private var maxBufferSize = -1
		set(value) {
			field = value.coerceAtLeast(buffer.size)
		}

	fun expandCapacity(minCapacity: Int) {
		buffer ?: return
		if(maxBufferSize != -1 && minCapacity >= maxBufferSize) {
			throw SerializationException("Min capacity '$minCapacity' is greater than max buffer size '$maxBufferSize'")
		}
		var newCapacity = buffer.size + (buffer.size shr 1) + 1
		if(newCapacity < minCapacity) newCapacity = minCapacity
		val newValue = CharArray(newCapacity)
		System.arraycopy(buffer, 0, newValue, 0, count)
		if(buffer.size < bufferThreshold) {
			val charsLocal = bufferLocal.get()
			if(charsLocal.size < buffer.size) {
				bufferLocal.set(buffer)
			}
		}
		buffer = newValue
	}

	override fun write(c: Int) {
		buffer ?: return
		var newCount = count + 1
		if(newCount > buffer.size) {
			if(writer == null) {
				expandCapacity(newCount)
			} else {
				flush()
				newCount = 1
			}
		}
		buffer[count] = c.toChar()
		count = newCount
	}

	@Suppress("NAME_SHADOWING")
	override fun write(cbuf: CharArray, off: Int, len: Int) {
		buffer ?: return
		if(len == 0) return
		var off = off
		var len = len
		if(off < 0 || off > cbuf.size || len < 0 || off + len > cbuf.size || off + len < 0) throw IndexOutOfBoundsException()
		var newCount = count + len
		if(newCount > buffer.size) {
			if(writer == null) {
				expandCapacity(newCount)
			} else {
				do {
					val rest = buffer.size - count
					System.arraycopy(cbuf, off, buffer, count, rest)
					count = buffer.size
					flush()
					len -= rest
					off += rest
				} while(len > buffer.size)
				newCount = len
			}
		}
		System.arraycopy(cbuf, off, buffer, count, len)
		count = newCount
	}

	@Suppress("NAME_SHADOWING")
	override fun write(str: String, off: Int, len: Int) {
		buffer ?: return
		if(len == 0) return
		var off = off
		var len = len
		var newCount = count + len
		if(newCount > buffer.size) {
			if(writer == null) {
				expandCapacity(newCount)
			} else {
				do {
					val rest = buffer.size - count
					str.toCharArray(buffer, count, off, off + rest)
					count = buffer.size
					flush()
					len -= rest
					off += rest
				} while(len > (buffer).size)
				newCount = len
			}
		}
		str.toCharArray(buffer, count, off, off + len)
		count = newCount
	}

	override fun append(csq: CharSequence): DataWriter {
		write(csq.toString(), 0, csq.length)
		return this
	}

	override fun append(csq: CharSequence, start: Int, end: Int): DataWriter {
		write(csq.subSequence(start, end).toString(), 0, csq.subSequence(start, end).toString().length)
		return this
	}

	override fun append(c: Char): DataWriter {
		write(c.toInt())
		return this
	}

	override fun flush() {
		writer ?: return
		buffer ?: return
		try {
			writer.write(buffer, 0, count)
			writer.flush()
		} catch(e: IOException) {
			throw SerializationException(e.message, e)
		}
		count = 0
	}

	override fun close() {
		buffer ?: return
		if(writer != null && count > 0) flush()
		if((buffer).size <= bufferThreshold) bufferLocal.set(buffer)
		this.buffer = null
	}

	override fun toString(): String {
		buffer ?: return ""
		return String(buffer, 0, count)
	}
}
