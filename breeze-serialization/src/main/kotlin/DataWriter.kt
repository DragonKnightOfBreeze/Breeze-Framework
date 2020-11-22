// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...
package com.windea.breezeframework.serialization

import java.io.*

/**
 * A character stream that collects its output in a string buffer, which can
 * then be used to construct a string.
 *
 *
 * Closing a `DataWriter` has no effect. The methods in this class
 * can be called after the stream has been closed without generating an
 * `IOException`.
 *
 * @author      Mark Reinhold
 * @since       1.1
 */
class DataWriter : Writer {
	/**
	 * Return the string buffer itself.
	 *
	 * @return StringBuffer holding the current buffer value.
	 */
	var buffer: StringBuffer
		private set

	/**
	 * Create a new string writer using the default initial string-buffer
	 * size.
	 */
	constructor() {
		buffer = StringBuffer()
		lock = buffer
	}

	/**
	 * Create a new string writer using the specified initial string-buffer
	 * size.
	 *
	 * @param initialSize
	 * The number of `char` values that will fit into this buffer
	 * before it is automatically expanded
	 *
	 * @throws IllegalArgumentException
	 * If `initialSize` is negative
	 */
	constructor(initialSize: Int) {
		require(initialSize >= 0) { "Negative buffer size" }
		buffer = StringBuffer(initialSize)
		lock = buffer
	}

	/**
	 * Write a single character.
	 */
	override fun write(c: Int) {
		buffer.append(c.toChar())
	}

	/**
	 * Write a portion of an array of characters.
	 *
	 * @param  cbuf  Array of characters
	 * @param  off   Offset from which to start writing characters
	 * @param  len   Number of characters to write
	 *
	 * @throws  IndexOutOfBoundsException
	 * If `off` is negative, or `len` is negative,
	 * or `off + len` is negative or greater than the length
	 * of the given array
	 */
	override fun write(cbuf: CharArray, off: Int, len: Int) {
		if(off < 0 || off > cbuf.size || len < 0 ||
		   off + len > cbuf.size || off + len < 0) {
			throw IndexOutOfBoundsException()
		} else if(len == 0) {
			return
		}
		buffer.append(cbuf, off, len)
	}

	/**
	 * Write a string.
	 */
	override fun write(str: String) {
		buffer.append(str)
	}

	/**
	 * Write a portion of a string.
	 *
	 * @param  str  String to be written
	 * @param  off  Offset from which to start writing characters
	 * @param  len  Number of characters to write
	 *
	 * @throws  IndexOutOfBoundsException
	 * If `off` is negative, or `len` is negative,
	 * or `off + len` is negative or greater than the length
	 * of the given string
	 */
	override fun write(str: String, off: Int, len: Int) {
		buffer.append(str, off, off + len)
	}

	/**
	 * Appends the specified character sequence to this writer.
	 *
	 *
	 *  An invocation of this method of the form `out.append(csq)`
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 * out.write(csq.toString()) </pre>
	 *
	 *
	 *  Depending on the specification of `toString` for the
	 * character sequence `csq`, the entire sequence may not be
	 * appended. For instance, invoking the `toString` method of a
	 * character buffer will return a subsequence whose content depends upon
	 * the buffer's position and limit.
	 *
	 * @param  csq
	 * The character sequence to append.  If `csq` is
	 * `null`, then the four characters `"null"` are
	 * appended to this writer.
	 *
	 * @return  This writer
	 *
	 * @since  1.5
	 */
	override fun append(csq: CharSequence): DataWriter {
		write(csq.toString())
		return this
	}

	/**
	 * Appends a subsequence of the specified character sequence to this writer.
	 *
	 *
	 *  An invocation of this method of the form
	 * `out.append(csq, start, end)` when `csq`
	 * is not `null`, behaves in
	 * exactly the same way as the invocation
	 *
	 * <pre>`out.write(csq.subSequence(start, end).toString())
	`</pre> *
	 *
	 * @param  csq
	 * The character sequence from which a subsequence will be
	 * appended.  If `csq` is `null`, then characters
	 * will be appended as if `csq` contained the four
	 * characters `"null"`.
	 *
	 * @param  start
	 * The index of the first character in the subsequence
	 *
	 * @param  end
	 * The index of the character following the last character in the
	 * subsequence
	 *
	 * @return  This writer
	 *
	 * @throws  IndexOutOfBoundsException
	 * If `start` or `end` are negative, `start`
	 * is greater than `end`, or `end` is greater than
	 * `csq.length()`
	 *
	 * @since  1.5
	 */
	override fun append(csq: CharSequence, start: Int, end: Int): DataWriter {
		var csq: CharSequence? = csq
		if(csq == null) csq = "null"
		return append(csq.subSequence(start, end))
	}

	/**
	 * Appends the specified character to this writer.
	 *
	 *
	 *  An invocation of this method of the form `out.append(c)`
	 * behaves in exactly the same way as the invocation
	 *
	 * <pre>
	 * out.write(c) </pre>
	 *
	 * @param  c
	 * The 16-bit character to append
	 *
	 * @return  This writer
	 *
	 * @since 1.5
	 */
	override fun append(c: Char): DataWriter {
		write(c.toInt())
		return this
	}

	/**
	 * Return the buffer's current value as a string.
	 */
	override fun toString(): String {
		return buffer.toString()
	}

	/**
	 * Flush the stream.
	 */
	override fun flush() {}

	/**
	 * Closing a `DataWriter` has no effect. The methods in this
	 * class can be called after the stream has been closed without generating
	 * an `IOException`.
	 */
	@Throws(IOException::class) override fun close() {}
}
