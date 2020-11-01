// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotations.*
import com.windea.breezeframework.core.extensions.*
import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.util.*

/**
 * 转化器。
 *
 * 转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 */
@BreezeComponent
interface Converter<T, R> {
	/**
	 * 源类型和目标类型的类型对。
	 */
	val typePair: Pair<Class<T>, Class<R>>

	/**
	 * 转化指定的对象。如果转化失败，则抛出异常。
	 */
	fun convert(value: T): R

	/**
	 * 转化指定的对象。如果转化失败，则返回null。
	 */
	fun convertOrNull(value: T): R? {
		return runCatching { convert(value) }.getOrNull()
	}

	companion object {
		private val converterRegistry = mutableListOf<Converter<*, *>>()

		/**
		 * 注册转化器。
		 */
		@JvmStatic fun <T, R> register(converter: Converter<T, R>) {
			converterRegistry += converter
		}

		/**
		 * 将指定的对象转化为指定的类型。如果转化失败，这抛出异常。
		 */
		inline fun <reified T> convert(value: Any?): T {
			return convert(value, T::class.java)
		}

		/**
		 * 将指定的对象转化为指定的类型。如果转化失败，这抛出异常。
		 */
		@JvmStatic fun <T> convert(value: Any?, targetType: Class<T>): T {
			return try {
				when {
					value == null -> null as T
					targetType.isAssignableFrom(value.javaClass) -> value as T
					else -> {
						for(converter in converterRegistry) {
							val (sType, tType) = converter.typePair
							if(tType == targetType && sType.isAssignableFrom(value.javaClass)) {
								return (converter as Converter<Any?, Any?>).convert(value) as T
							}
						}
						throwException(value, targetType)
					}
				}
			} catch(e: Exception) {
				throwException(value, targetType, e)
			}
		}

		/**
		 * 将指定的对象转化为指定的类型。如果转化失败，这返回null。
		 */
		inline fun <reified T> convertOrNull(value: Any?): T? {
			return convertOrNull(value, T::class.java)
		}

		/**
		 * 将指定的对象转化为指定的类型。如果转化失败，这返回null。
		 */
		@JvmStatic fun <T> convertOrNull(value: Any?, targetType: Class<T>): T? {
			return when {
				value == null -> null
				targetType.isAssignableFrom(value.javaClass) -> value as T
				else -> {
					for(converter in converterRegistry) {
						val (sType, tType) = converter.typePair
						if(tType == targetType && sType.isAssignableFrom(value.javaClass)) {
							return (converter as Converter<Any?, Any?>).convertOrNull(value) as T?
						}
					}
					null
				}
			}
		}

		private fun <T> throwException(value: Any?, targetType: Class<T>, cause: Throwable? = null): Nothing {
			throw IllegalArgumentException("Cannot convert '${value}' to type '${targetType.name}'.", cause)
		}

		init {
			registerDefaultConverters()
			registerExtendedStringConverters()
			registerIoConverters()
		}

		private fun registerDefaultConverters() {
			register(AnyToStringConverter)

			register(StringToIntConverter)
			register(StringToLongConverter)
			register(StringToFloatConverter)
			register(StringToDoubleConverter)
			register(StringToByteConverter)
			register(StringToShortConverter)
			register(StringToCharConverter)
			register(StringToBooleanConverter)

			register(IntToLongConverter)
			register(IntToFloatConverter)
			register(IntToDoubleConverter)
			register(IntToByteConverter)
			register(IntToShortConverter)
			register(IntToCharConverter)

			register(LongToIntConverter)
			register(LongToFloatConverter)
			register(LongToDoubleConverter)
			register(LongToByteConverter)
			register(LongToShortConverter)
			register(LongToCharConverter)

			register(FloatToIntConverter)
			register(FloatToLongConverter)
			register(FloatToDoubleConverter)
			register(FloatToByteConverter)
			register(FloatToShortConverter)
			register(FloatToCharConverter)

			register(DoubleToIntConverter)
			register(DoubleToLongConverter)
			register(DoubleToFloatConverter)
			register(DoubleToByteConverter)
			register(DoubleToShortConverter)
			register(DoubleToCharConverter)

			register(ByteToIntConverter)
			register(ByteToLongConverter)
			register(ByteToFloatConverter)
			register(ByteToDoubleConverter)
			register(ByteToShortConverter)
			register(ByteToCharConverter)

			register(ShortToIntConverter)
			register(ShortToLongConverter)
			register(ShortToFloatConverter)
			register(ShortToDoubleConverter)
			register(ShortToByteConverter)
			register(ShortToCharConverter)

			register(CharToIntConverter)
			register(CharToLongConverter)
			register(CharToFloatConverter)
			register(CharToDoubleConverter)
			register(CharToByteConverter)
			register(CharToShortConverter)
		}

		private fun registerExtendedStringConverters() {
			register(StringToRegexConverter)
			register(StringToFileConverter)
			register(StringToPathConverter)
			register(StringToUrlConverter)
			register(StringToUriConverter)
			register(StringToCharsetConverter)
			register(StringToTimeZoneConverter)
			register(StringToClassConverter)
		}

		private fun registerIoConverters() {
			register(FileToPathConverter)
			register(FileToUriConverter)
			register(FileToUrlConverter)

			register(PathToFileConverter)
			register(PathToUriConverter)
			register(PathToUrlConverter)

			register(UriToFileConverter)
			register(UriToPathConverter)
			register(UriToUrlConverter)

			register(UrlToPathConverter)
			register(UrlToFileConverter)
			register(UrlToUriConverter)
		}
	}

	//不公开默认注册的转换器：数量过多

	//region Default Converters
	private object AnyToStringConverter : Converter<Any, String> {
		override val typePair = Any::class.java to String::class.java
		override fun convert(value: Any) = value.toString()
		override fun convertOrNull(value: Any) = value.toString()
	}

	private object StringToIntConverter : Converter<String, Int> {
		override val typePair = String::class.java to Int::class.javaObjectType
		override fun convert(value: String) = value.toInt()
		override fun convertOrNull(value: String) = value.toIntOrNull()
	}

	private object StringToLongConverter : Converter<String, Long> {
		override val typePair = String::class.java to Long::class.javaObjectType
		override fun convert(value: String) = value.toLong()
		override fun convertOrNull(value: String) = value.toLongOrNull()
	}

	private object StringToFloatConverter : Converter<String, Float> {
		override val typePair = String::class.java to Float::class.javaObjectType
		override fun convert(value: String) = value.toFloat()
		override fun convertOrNull(value: String) = value.toFloatOrNull()
	}

	private object StringToDoubleConverter : Converter<String, Double> {
		override val typePair = String::class.java to Double::class.javaObjectType
		override fun convert(value: String) = value.toDouble()
		override fun convertOrNull(value: String) = value.toDoubleOrNull()
	}

	private object StringToByteConverter : Converter<String, Byte> {
		override val typePair = String::class.java to Byte::class.javaObjectType
		override fun convert(value: String) = value.toByte()
		override fun convertOrNull(value: String) = value.toByteOrNull()
	}

	private object StringToShortConverter : Converter<String, Short> {
		override val typePair = String::class.java to Short::class.javaObjectType
		override fun convert(value: String) = value.toShort()
		override fun convertOrNull(value: String) = value.toShortOrNull()
	}

	private object StringToCharConverter : Converter<String, Char> {
		override val typePair = String::class.java to Char::class.javaObjectType
		override fun convert(value: String) = value.toChar()
		override fun convertOrNull(value: String) = value.toCharOrNull()
	}

	private object StringToBooleanConverter : Converter<String, Boolean> {
		override val typePair = String::class.java to Boolean::class.javaObjectType
		override fun convert(value: String) = value.toBoolean()
		override fun convertOrNull(value: String) = value.toBooleanOrNull()
	}

	private object IntToLongConverter : Converter<Int, Long> {
		override val typePair = Int::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Int) = value.toLong()
	}

	private object IntToFloatConverter : Converter<Int, Float> {
		override val typePair = Int::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Int) = value.toFloat()
	}

	private object IntToDoubleConverter : Converter<Int, Double> {
		override val typePair = Int::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Int) = value.toDouble()
	}

	private object IntToByteConverter : Converter<Int, Byte> {
		override val typePair = Int::class.javaObjectType to Byte::class.javaObjectType
		override fun convert(value: Int) = value.toByte()
	}

	private object IntToShortConverter : Converter<Int, Short> {
		override val typePair = Int::class.javaObjectType to Short::class.javaObjectType
		override fun convert(value: Int) = value.toShort()
	}

	private object IntToCharConverter : Converter<Int, Char> {
		override val typePair = Int::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Int) = value.toChar()
	}

	private object LongToIntConverter : Converter<Long, Int> {
		override val typePair = Long::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Long) = value.toInt()
	}

	private object LongToFloatConverter : Converter<Long, Float> {
		override val typePair = Long::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Long) = value.toFloat()
	}

	private object LongToDoubleConverter : Converter<Long, Double> {
		override val typePair = Long::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Long) = value.toDouble()
	}

	private object LongToByteConverter : Converter<Long, Byte> {
		override val typePair = Long::class.javaObjectType to Byte::class.javaObjectType
		override fun convert(value: Long) = value.toByte()
	}

	private object LongToShortConverter : Converter<Long, Short> {
		override val typePair = Long::class.javaObjectType to Short::class.javaObjectType
		override fun convert(value: Long) = value.toShort()
	}

	private object LongToCharConverter : Converter<Long, Char> {
		override val typePair = Long::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Long) = value.toChar()
	}

	private object FloatToIntConverter : Converter<Float, Int> {
		override val typePair = Float::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Float) = value.toInt()
	}

	private object FloatToLongConverter : Converter<Float, Long> {
		override val typePair = Float::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Float) = value.toLong()
	}

	private object FloatToDoubleConverter : Converter<Float, Double> {
		override val typePair = Float::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Float) = value.toDouble()
	}

	private object FloatToByteConverter : Converter<Float, Byte> {
		override val typePair = Float::class.javaObjectType to Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toByte()
	}

	private object FloatToShortConverter : Converter<Float, Short> {
		override val typePair = Float::class.javaObjectType to Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toShort()
	}

	private object FloatToCharConverter : Converter<Float, Char> {
		override val typePair = Float::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Float) = value.toChar()
	}

	private object DoubleToIntConverter : Converter<Double, Int> {
		override val typePair = Double::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Double) = value.toInt()
	}

	private object DoubleToLongConverter : Converter<Double, Long> {
		override val typePair = Double::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Double) = value.toLong()
	}

	private object DoubleToFloatConverter : Converter<Double, Float> {
		override val typePair = Double::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Double) = value.toFloat()
	}

	private object DoubleToByteConverter : Converter<Double, Byte> {
		override val typePair = Double::class.javaObjectType to Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toByte()
	}

	private object DoubleToShortConverter : Converter<Double, Short> {
		override val typePair = Double::class.javaObjectType to Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toShort()
	}

	private object DoubleToCharConverter : Converter<Double, Char> {
		override val typePair = Double::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Double) = value.toChar()
	}

	private object ByteToIntConverter : Converter<Byte, Int> {
		override val typePair = Byte::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Byte) = value.toInt()
	}

	private object ByteToLongConverter : Converter<Byte, Long> {
		override val typePair = Byte::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Byte) = value.toLong()
	}

	private object ByteToFloatConverter : Converter<Byte, Float> {
		override val typePair = Byte::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Byte) = value.toFloat()
	}

	private object ByteToDoubleConverter : Converter<Byte, Double> {
		override val typePair = Byte::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Byte) = value.toDouble()
	}

	private object ByteToShortConverter : Converter<Byte, Short> {
		override val typePair = Byte::class.javaObjectType to Short::class.javaObjectType
		override fun convert(value: Byte) = value.toShort()
	}

	private object ByteToCharConverter : Converter<Byte, Char> {
		override val typePair = Byte::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Byte) = value.toChar()
	}

	private object ShortToIntConverter : Converter<Short, Int> {
		override val typePair = Short::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Short) = value.toInt()
	}

	private object ShortToLongConverter : Converter<Short, Long> {
		override val typePair = Short::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Short) = value.toLong()
	}

	private object ShortToFloatConverter : Converter<Short, Float> {
		override val typePair = Short::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Short) = value.toFloat()
	}

	private object ShortToDoubleConverter : Converter<Short, Double> {
		override val typePair = Short::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Short) = value.toDouble()
	}

	private object ShortToByteConverter : Converter<Short, Byte> {
		override val typePair = Short::class.javaObjectType to Byte::class.javaObjectType
		override fun convert(value: Short) = value.toByte()
	}

	private object ShortToCharConverter : Converter<Short, Char> {
		override val typePair = Short::class.javaObjectType to Char::class.javaObjectType
		override fun convert(value: Short) = value.toChar()
	}

	private object CharToIntConverter : Converter<Char, Int> {
		override val typePair = Char::class.javaObjectType to Int::class.javaObjectType
		override fun convert(value: Char) = value.toInt()
	}

	private object CharToLongConverter : Converter<Char, Long> {
		override val typePair = Char::class.javaObjectType to Long::class.javaObjectType
		override fun convert(value: Char) = value.toLong()
	}

	private object CharToFloatConverter : Converter<Char, Float> {
		override val typePair = Char::class.javaObjectType to Float::class.javaObjectType
		override fun convert(value: Char) = value.toFloat()
	}

	private object CharToDoubleConverter : Converter<Char, Double> {
		override val typePair = Char::class.javaObjectType to Double::class.javaObjectType
		override fun convert(value: Char) = value.toDouble()
	}

	private object CharToByteConverter : Converter<Char, Byte> {
		override val typePair = Char::class.javaObjectType to Byte::class.javaObjectType
		override fun convert(value: Char) = value.toByte()
	}

	private object CharToShortConverter : Converter<Char, Short> {
		override val typePair = Char::class.javaObjectType to Short::class.javaObjectType
		override fun convert(value: Char) = value.toShort()
	}
	//endregion

	//region Extended String Converters
	private object StringToRegexConverter : Converter<String, Regex> {
		override val typePair = String::class.java to Regex::class.java
		override fun convert(value: String) = value.toRegex()
	}

	private object StringToFileConverter : Converter<String, File> {
		override val typePair = String::class.java to File::class.java
		override fun convert(value: String) = value.toFile()
	}

	private object StringToPathConverter : Converter<String, Path> {
		override val typePair = String::class.java to Path::class.java
		override fun convert(value: String) = value.toPath()
	}

	private object StringToUrlConverter : Converter<String, URL> {
		override val typePair = String::class.java to URL::class.java
		override fun convert(value: String) = value.toUrl()
	}

	private object StringToUriConverter : Converter<String, URI> {
		override val typePair = String::class.java to URI::class.java
		override fun convert(value: String) = value.toUri()
	}

	private object StringToCharsetConverter : Converter<String, Charset> {
		override val typePair = String::class.java to Charset::class.java
		override fun convert(value: String) = value.toCharset()
		override fun convertOrNull(value: String) = value.toCharsetOrNull()
	}

	private object StringToTimeZoneConverter : Converter<String, TimeZone> {
		override val typePair = String::class.java to TimeZone::class.java
		override fun convert(value: String) = value.toTimeZone()
		override fun convertOrNull(value: String) = value.toTimeZoneOrNull()
	}

	private object StringToClassConverter : Converter<String, Class<*>> {
		override val typePair = String::class.java to Class::class.java
		override fun convert(value: String) = value.toClass()
		override fun convertOrNull(value: String) = value.toClassOrNull()
	}
	//endregion

	//region IO Converters
	private object FileToPathConverter : Converter<File, Path> {
		override val typePair = File::class.java to Path::class.java
		override fun convert(value: File) = value.toPath()
	}

	private object FileToUriConverter : Converter<File, URI> {
		override val typePair = File::class.java to URI::class.java
		override fun convert(value: File) = value.toUri()
	}

	private object FileToUrlConverter : Converter<File, URL> {
		override val typePair = File::class.java to URL::class.java
		override fun convert(value: File) = value.toUrl()
	}

	private object PathToFileConverter : Converter<Path, File> {
		override val typePair = Path::class.java to File::class.java
		override fun convert(value: Path) = value.toFile()
	}

	private object PathToUriConverter : Converter<Path, URI> {
		override val typePair = Path::class.java to URI::class.java
		override fun convert(value: Path) = value.toUri()
	}

	private object PathToUrlConverter : Converter<Path, URL> {
		override val typePair = Path::class.java to URL::class.java
		override fun convert(value: Path) = value.toUrl()
	}

	private object UriToFileConverter : Converter<URI, File> {
		override val typePair = URI::class.java to File::class.java
		override fun convert(value: URI) = value.toFile()
	}

	private object UriToPathConverter : Converter<URI, Path> {
		override val typePair = URI::class.java to Path::class.java
		override fun convert(value: URI): Path = value.toPath()
	}

	private object UriToUrlConverter : Converter<URI, URL> {
		override val typePair = URI::class.java to URL::class.java
		override fun convert(value: URI) = value.toUrl()
	}

	private object UrlToFileConverter : Converter<URL, File> {
		override val typePair = URL::class.java to File::class.java
		override fun convert(value: URL) = value.toFile()
	}

	private object UrlToPathConverter : Converter<URL, Path> {
		override val typePair = URL::class.java to Path::class.java
		override fun convert(value: URL) = value.toPath()
	}

	private object UrlToUriConverter : Converter<URL, URI> {
		override val typePair = URL::class.java to URI::class.java
		override fun convert(value: URL) = value.toUri()
	}
	//endregion
}
