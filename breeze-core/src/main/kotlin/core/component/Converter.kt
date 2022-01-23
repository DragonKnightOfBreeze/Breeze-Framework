// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.component.extension.convert
import icu.windea.breezeframework.core.component.extension.convertOrNull
import icu.windea.breezeframework.core.extension.*
import java.io.File
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI
import java.net.URL
import java.nio.charset.Charset
import java.nio.file.Path
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.*
import java.util.*
import java.util.concurrent.atomic.*
import java.util.regex.Pattern
import java.util.stream.Stream

/**
 * 转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 *
 * 同一兼容类型的转化器可以注册多个。
 *
 * @see Converters
 */
interface Converter<T> : Component {
	val targetType: Class<T>

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
	 */
	fun convert(value: Any): T

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
	 */
	fun convertOrNull(value: Any): T? {
		return runCatching { convert(value) }.getOrNull()
	}

	override fun componentCopy(componentParams: Map<String, Any?>): Converter<T> {
		throw UnsupportedOperationException("Cannot copy component of type: ${javaClass.name}.")
	}
}

interface GenericConverter<T> : Converter<T> {
	override fun convert(value: Any): T {
		throw UnsupportedOperationException("Redirect to: fun convert(value: Any, sourceType: Type, targetType: Type): T.")
	}

	override fun convertOrNull(value: Any): T? {
		throw UnsupportedOperationException("Redirect to: fun convertOrNull(value: Any, sourceType: Type, targetType: Type): T.")
	}

	fun convert(value: Any, targetType: Type): T

	fun convertOrNull(value: Any, targetType: Type): T? {
		return runCatching { convert(value, targetType) }.getOrNull()
	}
}

abstract class AbstractConverter<T> : Converter<T> {
	override val targetType: Class<T> = inferComponentTargetClass(this::class.javaObjectType, Converter::class.java)

	override fun equals(other: Any?) = componentEquals(other)

	override fun hashCode() = componentHashcode()

	override fun toString() = componentToString()
}

@Suppress("UNCHECKED_CAST", "RemoveExplicitTypeArguments")
object Converters : ComponentRegistry<Converter<*>>() {
	//region implementations
	object ByteConverter : AbstractConverter<Byte>() {
		override fun convert(value: Any): Byte {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.code.toByte()
				value is Boolean -> value.toByte()
				else -> value.toString().toByte()
			}
		}

		override fun convertOrNull(value: Any): Byte? {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.code.toByte()
				value is Boolean -> value.toByte()
				else -> value.toString().toByteOrNull()
			}
		}
	}

	object ShortConverter : AbstractConverter<Short>() {
		override fun convert(value: Any): Short {
			return when {
				value is Short -> value
				value is Number -> value.toShort()
				value is Char -> value.code.toShort()
				value is Boolean -> value.toShort()
				else -> value.toString().toShort()
			}
		}

		override fun convertOrNull(value: Any): Short? {
			return when {
				value is Short -> value
				value is Number -> value.toShort()
				value is Char -> value.code.toShort()
				else -> value.toString().toShortOrNull()
			}
		}
	}

	object IntConverter : AbstractConverter<Int>() {
		override fun convert(value: Any): Int {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.code
				value is Boolean -> value.toInt()
				else -> value.toString().toInt()
			}
		}

		override fun convertOrNull(value: Any): Int? {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.code
				value is Boolean -> value.toInt()
				else -> value.toString().toIntOrNull()
			}
		}
	}

	object LongConverter : AbstractConverter<Long>() {
		override fun convert(value: Any): Long {
			return when {
				value is Long -> value
				value is Number -> value.toLong()
				value is Char -> value.code.toLong()
				value is Boolean -> value.toLong()
				else -> value.toString().toLong()
			}
		}

		override fun convertOrNull(value: Any): Long? {
			return when {
				value is Long -> value
				value is Number -> value.toLong()
				value is Char -> value.code.toLong()
				value is Boolean -> value.toLong()
				else -> value.toString().toLongOrNull()
			}
		}
	}

	object FloatConverter : AbstractConverter<Float>() {
		override fun convert(value: Any): Float {
			return when {
				value is Float -> value
				value is Number -> value.toFloat()
				value is Char -> value.code.toFloat()
				value is Boolean -> value.toFloat()
				else -> value.toString().toFloat()
			}
		}

		override fun convertOrNull(value: Any): Float? {
			return when {
				value is Float -> value
				value is Number -> value.toFloat()
				value is Char -> value.code.toFloat()
				value is Boolean -> value.toFloat()
				else -> value.toString().toFloatOrNull()
			}
		}
	}

	object DoubleConverter : AbstractConverter<Double>() {
		override fun convert(value: Any): Double {
			return when {
				value is Double -> value
				value is Number -> value.toDouble()
				value is Char -> value.code.toDouble()
				value is Boolean -> value.toDouble()
				else -> value.toString().toDouble()
			}
		}

		override fun convertOrNull(value: Any): Double? {
			return when {
				value is Double -> value
				value is Number -> value.toDouble()
				value is Char -> value.code.toDouble()
				value is Boolean -> value.toDouble()
				else -> value.toString().toDoubleOrNull()
			}
		}
	}

	/**
	 * 组件参数说明：
	 * * relaxConvert - 是否使用条件更为宽松的转化。如果是，则0、空数组、空集合等将会被转化为`false`。
	 */
	@ComponentParam("relaxConvert", "Boolean", "false")
	open class BooleanConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Boolean>() {
		companion object Default : BooleanConverter()

		val relaxConvert: Boolean = componentParams.get("relaxConvert").convertToBooleanOrFalse()

		override fun convert(value: Any): Boolean {
			return if (relaxConvert) doRelaxConvert(value) else doConvert(value)
		}

		private fun doConvert(value: Any): Boolean {
			return value == true || value.toString() == "true"
		}

		private fun doRelaxConvert(value: Any): Boolean {
			return when {
				value is Boolean -> value
				value is Number -> value.toString().let { it != "0" || it != "0.0" }
				value is CharSequence -> value.isNotEmpty()
				value is Array<*> -> value.isNotEmpty()
				value is Collection<*> -> value.isNotEmpty()
				value is Iterable<*> -> value.any()
				value is Sequence<*> -> value.any()
				value is Map<*, *> -> value.isNotEmpty()
				else -> false
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = BooleanConverter(componentParams)
	}

	object CharConverter : AbstractConverter<Char>() {
		override fun convert(value: Any): Char {
			return when {
				value is Char -> value
				value is Number -> value.toChar()
				value is CharSequence -> value.single()
				else -> throw IllegalArgumentException("Cannot convert '$value' to Char.")
			}
		}

		override fun convertOrNull(value: Any): Char? {
			return when {
				value is Char -> value
				value is Number -> value.toChar()
				value is CharSequence -> value.singleOrNull()
				else -> null
			}
		}
	}

	object BigIntegerConverter : AbstractConverter<BigInteger>() {
		override fun convert(value: Any): BigInteger {
			return when {
				value is BigInteger -> value
				value is Long -> BigInteger.valueOf(value)
				else -> BigInteger.valueOf(value.convert<Long>())
			}
		}

		override fun convertOrNull(value: Any): BigInteger? {
			return when {
				value is BigInteger -> value
				value is Long -> BigInteger.valueOf(value)
				else -> BigInteger.valueOf(value.convertOrNull<Long>() ?: return null)
			}
		}
	}

	object BigDecimalConverter : AbstractConverter<BigDecimal>() {
		override fun convert(value: Any): BigDecimal {
			return when {
				value is BigDecimal -> value
				value is Double -> BigDecimal.valueOf(value)
				else -> BigDecimal.valueOf(value.convert<Double>())
			}
		}

		override fun convertOrNull(value: Any): BigDecimal? {
			return when {
				value is BigDecimal -> value
				value is Double -> BigDecimal.valueOf(value)
				else -> BigDecimal.valueOf(value.convertOrNull<Double>() ?: return null)
			}
		}
	}

	@ExperimentalUnsignedTypes
	object UByteConverter : AbstractConverter<UByte>() {
		override fun convert(value: Any): UByte {
			return when {
				value is UByte -> value
				value is Byte -> value.toUByte()
				else -> value.convert<Byte>().toUByte()
			}
		}

		override fun convertOrNull(value: Any): UByte? {
			return when {
				value is UByte -> value
				value is Byte -> value.toUByte()
				else -> value.convertOrNull<Byte>()?.toUByte()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object UShortConverter : AbstractConverter<UShort>() {
		override fun convert(value: Any): UShort {
			return when {
				value is UShort -> value
				value is Short -> value.toUShort()
				else -> value.convert<Short>().toUShort()
			}
		}

		override fun convertOrNull(value: Any): UShort? {
			return when {
				value is UShort -> value
				value is Short -> value.toUShort()
				else -> value.convertOrNull<Short>()?.toUShort()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object UIntConverter : AbstractConverter<UInt>() {
		override fun convert(value: Any): UInt {
			return when {
				value is UInt -> value
				value is Int -> value.toUInt()
				else -> value.convert<Int>().toUInt()
			}
		}

		override fun convertOrNull(value: Any): UInt? {
			return when {
				value is UInt -> value
				value is Int -> value.toUInt()
				else -> value.convertOrNull<Int>()?.toUInt()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object ULongConverter : AbstractConverter<ULong>() {
		override fun convert(value: Any): ULong {
			return when {
				value is ULong -> value
				value is Long -> value.toULong()
				else -> value.convert<Long>().toULong()
			}
		}

		override fun convertOrNull(value: Any): ULong? {
			return when {
				value is ULong -> value
				value is Long -> value.toULong()
				else -> value.convertOrNull<Long>()?.toULong()
			}
		}
	}

	object AtomicIntegerConverter : AbstractConverter<AtomicInteger>() {
		override fun convert(value: Any): AtomicInteger {
			return when {
				value is AtomicInteger -> value
				value is Int -> AtomicInteger(value)
				else -> AtomicInteger(value.convert())
			}
		}

		override fun convertOrNull(value: Any): AtomicInteger? {
			return when {
				value is AtomicInteger -> value
				value is Int -> AtomicInteger(value)
				else -> AtomicInteger(value.convertOrNull<Int>() ?: return null)
			}
		}
	}

	object AtomicLongConverter : AbstractConverter<AtomicLong>() {
		override fun convert(value: Any): AtomicLong {
			return when {
				value is AtomicLong -> value
				value is Long -> AtomicLong(value)
				else -> AtomicLong(value.convert())
			}
		}

		override fun convertOrNull(value: Any): AtomicLong? {
			return when {
				value is AtomicLong -> value
				value is Long -> AtomicLong(value)
				value is Number -> AtomicLong(value.toLong())
				else -> AtomicLong(value.convertOrNull<Long>() ?: return null)
			}
		}
	}

	object AtomicBooleanConverter : AbstractConverter<AtomicBoolean>() {
		override fun convert(value: Any): AtomicBoolean {
			return when {
				value is AtomicBoolean -> value
				value is Boolean -> AtomicBoolean(value)
				else -> AtomicBoolean(value.convert())
			}
		}
	}

	@ComponentParam("raw", "Boolean", "false")
	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("dateFormat", "String", "yyyy-MM-dd")
	@ComponentParam("timeFormat", "String", "HH:mm:ss")
	@ComponentParam("dateTimeFormat", "String", "yyyy-MM-dd HH:mm:ss")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class StringConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<String>() {
		companion object Default : StringConverter()

		val raw: Boolean = componentParams.get("raw").convertToBooleanOrFalse()
		val format: String = componentParams.get("format").convertToStringOrNull() ?: defaultDateFormat
		val dateFormat: String = componentParams.get("dateFormat").convertToStringOrNull() ?: defaultDateFormat
		val timeFormat: String = componentParams.get("timeFormat").convertToStringOrNull() ?: defaultTimeFormat
		val dateTimeFormat: String =
			componentParams.get("dateTimeFormat").convertToStringOrNull() ?: defaultDateTimeFormat
		val locale: Locale = componentParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = componentParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val threadLocalDateFormat =
			ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		private val dateFormatter = DateTimeFormatter.ofPattern(dateFormat, locale).withZone(timeZone.toZoneId())
		private val timeFormatter = DateTimeFormatter.ofPattern(timeFormat, locale).withZone(timeZone.toZoneId())
		private val dateTimeFormatter =
			DateTimeFormatter.ofPattern(dateTimeFormat, locale).withZone(timeZone.toZoneId())

		override fun convert(value: Any): String {
			return when {
				raw -> value.toString()
				value is Date -> threadLocalDateFormat.get().format(value)
				value is LocalDate -> dateFormatter.format(value)
				value is LocalTime -> timeFormatter.format(value)
				value is LocalDateTime -> dateTimeFormatter.format(value)
				value is TemporalAccessor -> formatter.format(value)
				else -> value.smartToString()
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = StringConverter(componentParams)
	}

	@ComponentParam("regexOptions", "Array<RegexOption> | Iterable<RegexOption> | Sequence<RegexOption>", "<empty>")
	open class RegexConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Regex>() {
		companion object Default : RegexConverter()

		val regexOptions: Set<RegexOption> = componentParams.get("regexOptions").convertOrNull() ?: emptySet()

		override fun convert(value: Any): Regex {
			return when {
				value is Regex -> value
				value is Pattern -> value.toRegex()
				else -> {
					when (regexOptions.size) {
						0 -> value.toString().toRegex()
						1 -> value.toString().toRegex(regexOptions.first())
						else -> value.toString().toRegex(regexOptions)
					}
				}
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = RegexConverter(componentParams)
	}

	object PatternConverter : AbstractConverter<Pattern>() {
		override fun convert(value: Any): Pattern {
			return when {
				value is Regex -> value.toPattern()
				value is Pattern -> value
				else -> Pattern.compile(value.toString())
			}
		}
	}

	object CharsetConverter : AbstractConverter<Charset>() {
		override fun convert(value: Any): Charset {
			return when {
				value is Charset -> value
				else -> value.toString().toCharset()
			}
		}

		override fun convertOrNull(value: Any): Charset? {
			return when {
				value is Charset -> value
				else -> value.toString().toCharsetOrNull()
			}
		}
	}

	object ClassConverter : AbstractConverter<Class<*>>() {
		override fun convert(value: Any): Class<*> {
			return when {
				value is Class<*> -> value
				else -> value.toString().toClass()
			}
		}

		override fun convertOrNull(value: Any): Class<*>? {
			return when {
				value is Class<*> -> value
				else -> value.toString().toClassOrNull()
			}
		}
	}

	object LocaleConverter : AbstractConverter<Locale>() {
		override fun convert(value: Any): Locale {
			return when {
				value is Locale -> value
				else -> value.toString().toLocale()
			}
		}
	}

	object TimeZoneConverter : AbstractConverter<TimeZone>() {
		override fun convert(value: Any): TimeZone {
			return when {
				value is TimeZone -> value
				value is ZoneId -> TimeZone.getTimeZone(value)
				else -> value.toString().toTimeZone()
			}
		}

		override fun convertOrNull(value: Any): TimeZone? {
			return when {
				value is TimeZone -> value
				value is ZoneId -> TimeZone.getTimeZone(value)
				else -> value.toString().toTimeZoneOrNull()
			}
		}
	}

	object ZoneIdConverter : AbstractConverter<ZoneId>() {
		override fun convert(value: Any): ZoneId {
			return when {
				value is ZoneId -> value
				value is TimeZone -> value.toZoneId()
				value is TemporalAccessor -> ZoneId.from(value)
				else -> ZoneId.of(value.toString())
			}
		}
	}

	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class DateConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Date>() {
		companion object Default : DateConverter()

		val format: String = componentParams.get("format").convertToStringOrNull() ?: defaultDateFormat
		val locale: Locale = componentParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = componentParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val threadLocalDateFormat =
			ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }

		override fun convert(value: Any): Date {
			return when {
				value is Date -> value
				value is Instant -> Date.from(value)
				value is String -> threadLocalDateFormat.get().parse(value)
				else -> threadLocalDateFormat.get().parse(value.toString())
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = DateConverter(componentParams)
	}

	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalDateConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDate>() {
		companion object Default : LocalDateConverter()

		val format: String = componentParams.get("format").convertToStringOrNull() ?: defaultDateFormat
		val locale: Locale = componentParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = componentParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun convert(value: Any): LocalDate {
			return when {
				value is LocalDate -> value
				value is TemporalAccessor -> LocalDate.from(value)
				value is Date -> LocalDate.from(value.toInstant())
				value is String -> LocalDate.parse(value, formatter)
				else -> LocalDate.parse(value.toString(), formatter)
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = LocalDateConverter(componentParams)
	}

	@ComponentParam("format", "String", "HH:mm:ss")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalTimeConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalTime>() {
		companion object Default : LocalTimeConverter()

		val format: String = componentParams.get("format")?.convertToStringOrNull() ?: defaultTimeFormat
		val locale: Locale = componentParams.get("locale")?.convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = componentParams.get("timeZone")?.convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun convert(value: Any): LocalTime {
			return when {
				value is LocalTime -> value
				value is TemporalAccessor -> LocalTime.from(value)
				value is Date -> LocalTime.from(value.toInstant())
				value is String -> LocalTime.parse(value, formatter)
				else -> LocalTime.parse(value.toString(), formatter)
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = LocalTimeConverter(componentParams)
	}

	@ComponentParam("format", "String", "yyyy-MM-dd HH:mm:ss")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalDateTimeConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDateTime>() {
		companion object Default : LocalDateTimeConverter()

		val format: String = componentParams.get("format")?.convertToStringOrNull() ?: defaultDateTimeFormat
		val locale: Locale = componentParams.get("locale")?.convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = componentParams.get("timeZone")?.convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun convert(value: Any): LocalDateTime {
			return when {
				value is LocalDateTime -> value
				value is TemporalAccessor -> LocalDateTime.from(value)
				value is Date -> LocalDateTime.from(value.toInstant())
				value is String -> LocalDateTime.parse(value, formatter)
				else -> LocalDateTime.parse(value.toString(), formatter)
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = LocalDateTimeConverter(componentParams)
	}

	object InstantConverter : AbstractConverter<Instant>() {
		override fun convert(value: Any): Instant {
			return when {
				value is Instant -> value
				value is TemporalAccessor -> Instant.from(value)
				value is String -> Instant.parse(value)
				else -> Instant.parse(value.toString())
			}
		}
	}

	//TODO
	open class DurationConverter(
		override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Duration>() {
		companion object Default : DurationConverter()

		override fun convert(value: Any): Duration {
			return when {
				value is Duration -> value
				value is TemporalAmount -> Duration.from(value)
				value is Pair<*, *> -> {
					val (start, end) = value
					if (start is Temporal && end is Temporal) {
						Duration.between(start, end)
					} else {
						throw IllegalArgumentException("Cannot convert '$value' to Duration.")
					}
				}
				else -> Duration.parse(value.toString())
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = DurationConverter(componentParams)
	}

	//TODO
	open class PeriodConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Period>() {
		companion object Default : PeriodConverter()

		override fun convert(value: Any): Period {
			return when {
				value is Period -> value
				value is TemporalAmount -> Period.from(value)
				value is Pair<*, *> -> {
					val (start, end) = value
					if (start is LocalDate && end is LocalDate) {
						Period.between(start, end)
					} else {
						throw IllegalArgumentException("Cannot convert '$value' to Period.")
					}
				}
				value is String -> Period.parse(value)
				else -> Period.parse(value.toString())
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = PeriodConverter(componentParams)
	}

	object FileConverter : AbstractConverter<File>() {
		override fun convert(value: Any): File {
			return when {
				value is File -> value
				value is Path -> value.toFile()
				value is URL -> value.toFile()
				value is URI -> value.toFile()
				else -> value.toString().toFile()
			}
		}
	}

	object PathConverter : AbstractConverter<Path>() {
		override fun convert(value: Any): Path {
			return when {
				value is File -> value.toPath()
				value is Path -> value
				value is URL -> value.toPath()
				value is URI -> value.toPath()
				else -> value.toString().toPath()
			}
		}
	}

	object UrlConverter : AbstractConverter<URL>() {
		override fun convert(value: Any): URL {
			return when {
				value is File -> value.toUrl()
				value is Path -> value.toUrl()
				value is URL -> value
				value is URI -> value.toUrl()
				else -> value.toString().toUrl()
			}
		}
	}

	object UriConverter : AbstractConverter<URI>() {
		override fun convert(value: Any): URI {
			return when {
				value is File -> value.toUri()
				value is Path -> value.toUri()
				value is URL -> value.toUri()
				value is URI -> value
				else -> value.toString().toUri()
			}
		}
	}

	object EnumConverter : AbstractConverter<Enum<*>>(), GenericConverter<Enum<*>> {
		override fun convert(value: Any, targetType: Type): Enum<*> {
			val enumClass = inferEnumClass(targetType)
			if (enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
			return when {
				value is Number -> {
					val index = value.toInt()
					val enumValues = enumValuesCache.getOrPut(enumClass) {
						enumClass.enumConstants?.toList() ?: emptyList()
					}
					enumValues.getOrNull(index)
						?: throw IllegalArgumentException("Cannot find enum constant by index '$index'.")
				}
				else -> {
					val name = value.toString()
					val enumValueMap = enumValueMapCache.getOrPut(enumClass) {
						enumClass.enumConstants?.toList()?.associateBy { it.name } ?: emptyMap()
					}
					enumValueMap[name] ?: throw IllegalArgumentException("Cannot find enum constant by name '$name'.")
				}
			}
		}

		override fun convertOrNull(value: Any, targetType: Type): Enum<*>? {
			val enumClass = inferEnumClass(targetType)
			if (enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
			return when {
				value is Number -> {
					val index = value.toInt()
					val enumValues = enumValuesCache.getOrPut(enumClass) {
						enumClass.enumConstants?.toList() ?: emptyList()
					}
					enumValues.getOrNull(index)
				}
				else -> {
					val name = value.toString().lowercase()
					val enumValueMap = enumValueMapCache.getOrPut(enumClass) {
						enumClass.enumConstants?.toList()?.associateBy { it.name } ?: emptyMap()
					}
					enumValueMap[name]
				}
			}
		}
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串（转化为数组时使用的前缀。
	 * * suffix - 从字符串（转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class ArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Array<*>>(), GenericConverter<Array<*>> {
		companion object Default : ArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any, targetType: Type): Array<*> {
			val elementType = inferTypeArgument(targetType)
			return when {
				value is Array<*> -> Array(value.size) { convertElement(value[it], elementType) }
				value is ByteArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is ShortArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is IntArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is LongArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is FloatArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is DoubleArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is BooleanArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is CharArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is UByteArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is UShortArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is UIntArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is ULongArray -> Array(value.size) { convertElement(value[it], elementType) }
				value is Iterator<*> -> Iterable { value }.toList().let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				value is Iterable<*> -> value.toList().let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				value is Sequence<*> -> value.toList().let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				value is Stream<*> -> value.toList().let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				value is String -> splitValue(value).let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> Array(v.size) { convertElement(v[it], elementType) } }
				else -> arrayOf(convertElement(value, elementType))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) =
			element.convert(elementType, passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = ArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class ByteArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ByteArray>() {
		companion object Default : ByteArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): ByteArray {
			return when {
				value is Array<*> -> ByteArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is IntArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is LongArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is CharArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> ByteArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				else -> byteArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Byte>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = ByteArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class ShortArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ShortArray>() {
		companion object Default : ShortArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): ShortArray {
			return when {
				value is Array<*> -> ShortArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is IntArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is LongArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is CharArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> ShortArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				else -> shortArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Short>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = ShortArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class IntArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<IntArray>() {
		companion object Default : IntArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): IntArray {
			return when {
				value is Array<*> -> IntArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> IntArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> IntArray(value.size) { convertElement(value[it]) }
				value is IntArray -> IntArray(value.size) { convertElement(value[it]) }
				value is LongArray -> IntArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> IntArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> IntArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> IntArray(value.size) { convertElement(value[it]) }
				value is CharArray -> IntArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> IntArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> IntArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> IntArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> IntArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> IntArray(v.size) { convertElement(v[it]) } }
				else -> intArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Int>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = IntArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class LongArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LongArray>() {
		companion object Default : LongArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): LongArray {
			return when {
				value is Array<*> -> LongArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> LongArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> LongArray(value.size) { convertElement(value[it]) }
				value is IntArray -> LongArray(value.size) { convertElement(value[it]) }
				value is LongArray -> LongArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> LongArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> LongArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> LongArray(value.size) { convertElement(value[it]) }
				value is CharArray -> LongArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> LongArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> LongArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> LongArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> LongArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> LongArray(v.size) { convertElement(v[it]) } }
				else -> longArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Long>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = LongArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class FloatArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<FloatArray>() {
		companion object Default : FloatArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): FloatArray {
			return when {
				value is Array<*> -> FloatArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is IntArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is LongArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is CharArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> FloatArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				else -> floatArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Float>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = FloatArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class DoubleArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<DoubleArray>() {
		companion object Default : DoubleArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): DoubleArray {
			return when {
				value is Array<*> -> DoubleArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is IntArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is LongArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is CharArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> DoubleArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				else -> doubleArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Double>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = DoubleArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class BooleanArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<BooleanArray>() {
		companion object Default : BooleanArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): BooleanArray {
			return when {
				value is Array<*> -> BooleanArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is IntArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is LongArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is CharArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> BooleanArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				else -> booleanArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Boolean>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = BooleanArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class CharArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<CharArray>() {
		companion object Default : CharArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): CharArray {
			return when {
				value is Array<*> -> CharArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> CharArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> CharArray(value.size) { convertElement(value[it]) }
				value is IntArray -> CharArray(value.size) { convertElement(value[it]) }
				value is LongArray -> CharArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> CharArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> CharArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> CharArray(value.size) { convertElement(value[it]) }
				value is CharArray -> CharArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> CharArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> CharArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> CharArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> CharArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> CharArray(v.size) { convertElement(v[it]) } }
				else -> charArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Char>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = CharArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class UByteArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UByteArray>() {
		companion object Default : UByteArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): UByteArray {
			return when {
				value is Array<*> -> UByteArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is IntArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is LongArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is CharArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> UByteArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				else -> ubyteArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UByte>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = UByteArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class UShortArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UShortArray>() {
		companion object Default : UShortArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): UShortArray {
			return when {
				value is Array<*> -> UShortArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is IntArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is LongArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is CharArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> UShortArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				else -> ushortArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UShort>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = UShortArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class UIntArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UIntArray>() {
		companion object Default : UIntArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): UIntArray {
			return when {
				value is Array<*> -> UIntArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is IntArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is LongArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is CharArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> UIntArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				else -> uintArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UInt>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = UIntArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为数组时使用的分隔符。
	 * * prefix - 从字符串转化为数组时使用的前缀。
	 * * suffix - 从字符串转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class ULongArrayConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ULongArray>() {
		companion object Default : ULongArrayConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any): ULongArray {
			return when {
				value is Array<*> -> ULongArray(value.size) { convertElement(value[it]) }
				value is ByteArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is ShortArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is IntArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is LongArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is FloatArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is DoubleArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is BooleanArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is CharArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is UByteArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is UShortArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is UIntArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is ULongArray -> ULongArray(value.size) { convertElement(value[it]) }
				value is Iterator<*> -> Iterable { value }.toList()
					.let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				else -> ulongArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<ULong>(passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = ULongArrayConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为迭代器时使用的分隔符。
	 * * prefix - 从字符串转化为迭代器时使用的前缀。
	 * * suffix - 从字符串转化为迭代器时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list")
	open class IteratorConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Iterator<*>>(), GenericConverter<Iterator<*>> {
		companion object Default : IteratorConverter()

		private val iterableConverter by lazy {
			get(MutableIterable::class.java, passingParams) { MutableIterableConverter(passingParams) }
		}

		private val passingParams = componentParams

		override fun convert(value: Any, targetType: Type): Iterator<*> {
			return iterableConverter.convert(value, targetType).iterator()
		}

		override fun convertOrNull(value: Any, targetType: Type): Iterator<*>? {
			return iterableConverter.convertOrNull(value, targetType)?.iterator()
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = IteratorConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为可变迭代器时使用的分隔符。
	 * * prefix - 从字符串转化为可变迭代器时使用的前缀。
	 * * suffix - 从字符串转化为可变迭代器时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list")
	open class MutableIteratorConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<MutableIterator<*>>(), GenericConverter<MutableIterator<*>> {
		companion object Default : MutableIteratorConverter()

		private val mutableIterableConverter by lazy {
			get(MutableIterable::class.java, passingParams) { MutableIterableConverter(passingParams) }
		}

		private val passingParams = componentParams

		override fun convert(value: Any, targetType: Type): MutableIterator<*> {
			return mutableIterableConverter.convert(value, targetType).iterator()
		}

		override fun convertOrNull(value: Any, targetType: Type): MutableIterator<*>? {
			return mutableIterableConverter.convertOrNull(value, targetType)?.iterator()
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = MutableIteratorConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为集合时使用的分隔符。
	 * * prefix - 从字符串转化为集合时使用的前缀。
	 * * suffix - 从字符串转化为集合时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list")
	open class IterableConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Iterable<*>>(), GenericConverter<Iterable<*>> {
		companion object Default : IterableConverter()

		private val collectionConverter by lazy {
			get(MutableCollection::class.java, passingParams) { MutableCollectionConverter(passingParams) }
		}

		private val passingParams = componentParams

		override fun convert(value: Any, targetType: Type): Iterable<*> {
			return collectionConverter.convert(value, targetType)
		}

		override fun convertOrNull(value: Any, targetType: Type): Iterable<*>? {
			return collectionConverter.convertOrNull(value, targetType)
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = IterableConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为可变集合时使用的分隔符。
	 * * prefix - 从字符串转化为可变集合时使用的前缀。
	 * * suffix - 从字符串转化为可变集合时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list")
	open class MutableIterableConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<MutableIterable<*>>(),
		GenericConverter<MutableIterable<*>> {
		companion object Default : MutableIterableConverter()

		private val mutableCollectionConverter by lazy {
			get(MutableCollection::class.java, passingParams) { MutableCollectionConverter(passingParams) }
		}

		private val passingParams = componentParams

		override fun convert(value: Any, targetType: Type): MutableIterable<*> {
			return mutableCollectionConverter.convert(value, targetType)
		}

		override fun convertOrNull(value: Any, targetType: Type): MutableIterable<*>? {
			return mutableCollectionConverter.convertOrNull(value, targetType)
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = MutableIterableConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为集合时使用的分隔符。
	 * * prefix - 从字符串转化为集合时使用的前缀。
	 * * suffix - 从字符串转化为集合时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list", passing = false)
	open class CollectionConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Collection<*>>(), GenericConverter<Collection<*>> {
		companion object Default : CollectionConverter()

		private val listConverter by lazy {
			get(MutableList::class.java, passingParams) { MutableListConverter(passingParams) }
		}
		private val setConverter by lazy {
			get(MutableSet::class.java, passingParams) { MutableSetConverter(passingParams) }
		}

		private val passingParams = filterNotComponentParams(componentParams, "delegate")

		val delegate: String = componentParams.get("delegate")?.toString() ?: "list"

		override fun convert(value: Any, targetType: Type): Collection<*> {
			return when (delegate) {
				"list" -> listConverter.convert(value, targetType)
				"set" -> setConverter.convert(value, targetType)
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun convertOrNull(value: Any, targetType: Type): Collection<*>? {
			return when (delegate) {
				"list" -> listConverter.convertOrNull(value, targetType)
				"set" -> setConverter.convertOrNull(value, targetType)
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = CollectionConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为可变集合时使用的分隔符。
	 * * prefix - 从字符串转化为可变集合时使用的前缀。
	 * * suffix - 从字符串转化为可变集合时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list", passing = false)
	open class MutableCollectionConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<MutableCollection<*>>(), GenericConverter<MutableCollection<*>> {
		companion object Default : MutableCollectionConverter()

		private val mutableListConverter by lazy {
			get(MutableList::class.java, passingParams) { MutableListConverter(passingParams) }
		}
		private val mutableSetConverter by lazy {
			get(MutableSet::class.java, passingParams) { MutableSetConverter(passingParams) }
		}

		private val passingParams = filterNotComponentParams(componentParams, "delegate")

		val delegate: String = componentParams.get("delegate")?.toString() ?: "list"

		override fun convert(value: Any, targetType: Type): MutableCollection<*> {
			return when (delegate) {
				"list" -> mutableListConverter.convert(value, targetType)
				"set" -> mutableSetConverter.convert(value, targetType)
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun convertOrNull(value: Any, targetType: Type): MutableCollection<*>? {
			return when (delegate) {
				"list" -> mutableListConverter.convertOrNull(value, targetType)
				"set" -> mutableSetConverter.convertOrNull(value, targetType)
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = MutableCollectionConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为列表时使用的分隔符。
	 * * prefix - 从字符串转化为列表时使用的前缀。
	 * * suffix - 从字符串转化为列表时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class ListConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<List<*>>(), GenericConverter<List<*>> {
		companion object Default : ListConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any, targetType: Type): List<*> {
			val elementType = inferTypeArgument(targetType)
			return when {
				value is Array<*> -> value.map { convertElement(it, elementType) }.convertToList()
				value is ByteArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is ShortArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is IntArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is LongArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is FloatArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is DoubleArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is BooleanArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is CharArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is UByteArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is UShortArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is UIntArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is ULongArray -> value.map { convertElement(it, elementType) }.convertToList()
				value is Iterator<*> -> Iterable { value }.map { convertElement(it, elementType) }.convertToList()
				value is Iterable<*> -> value.map { convertElement(it, elementType) }.convertToList()
				value is Sequence<*> -> value.map { convertElement(it, elementType) }.toList()
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toList()
				value is String -> splitValue(value).map { convertElement(it, elementType) }.convertToList()
				value is CharSequence -> splitValue(value.toString()).map { convertElement(it, elementType) }
					.convertToList()
				else -> listOf(value.convert(elementType, passingParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = ListConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为可变列表时使用的分隔符。
	 * * prefix - 从字符串转化为可变列表时使用的前缀。
	 * * suffix - 从字符串转化为可变列表时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class MutableListConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<MutableList<*>>(), GenericConverter<MutableList<*>> {
		companion object Default : MutableListConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any, targetType: Type): MutableList<*> {
			val elementType = inferTypeArgument(targetType)
			return when {
				value is Array<*> -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is ByteArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is ShortArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is IntArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is LongArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is FloatArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is DoubleArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is BooleanArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is CharArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is UByteArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is UShortArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is UIntArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is ULongArray -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is Iterator<*> -> Iterable { value }.map { convertElement(it, elementType) }
					.convertToMutableList()
				value is Iterable<*> -> value.map { convertElement(it, elementType) }.convertToMutableList()
				value is Sequence<*> -> value.map { convertElement(it, elementType) }.toMutableList()
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toMutableList()
				value is String -> splitValue(value).map { convertElement(it, elementType) }.convertToMutableList()
				value is CharSequence -> splitValue(value.toString()).map { convertElement(it, elementType) }
					.convertToMutableList()
				else -> mutableListOf(value.convert(elementType, passingParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) =
			element.convert(elementType, passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = MutableListConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为集时使用的分隔符。
	 * * prefix - 从字符串转化为集时使用的前缀。
	 * * suffix - 从字符串转化为集时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class SetConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Set<*>>(), GenericConverter<Set<*>> {
		companion object Default : SetConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any, targetType: Type): Set<*> {
			val elementType = inferTypeArgument(targetType)
			return when {
				value is Array<*> -> value.map { convertElement(it, elementType) }.convertToSet()
				value is ByteArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is ShortArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is IntArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is LongArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is FloatArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is DoubleArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is BooleanArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is CharArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is UByteArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is UShortArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is UIntArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is ULongArray -> value.map { convertElement(it, elementType) }.convertToSet()
				value is Iterator<*> -> Iterable { value }.map { convertElement(it, elementType) }.convertToSet()
				value is Iterable<*> -> value.map { convertElement(it, elementType) }.convertToSet()
				value is Sequence<*> -> value.map { convertElement(it, elementType) }.toSet()
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toSet()
				value is String -> splitValue(value).map { convertElement(it, elementType) }.convertToSet()
				value is CharSequence -> splitValue(value.toString()).map { convertElement(it, elementType) }
					.convertToSet()
				else -> setOf(value.convert(elementType, passingParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = SetConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为可变集时使用的分隔符。
	 * * prefix - 从字符串转化为可变集时使用的前缀。
	 * * suffix - 从字符串转化为可变集时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class MutableSetConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<MutableSet<*>>(), GenericConverter<MutableSet<*>> {
		companion object Default : MutableSetConverter()

		private val passingParams = filterNotComponentParams(componentParams, "separator", "prefix", "suffix")

		val separator = componentParams.get("separator")?.toString() ?: ","
		val prefix = componentParams.get("prefix")?.toString() ?: ""
		val suffix = componentParams.get("suffix")?.toString() ?: ""

		override fun convert(value: Any, targetType: Type): MutableSet<*> {
			val elementType = inferTypeArgument(targetType)
			return when {
				value is Array<*> -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is ByteArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is ShortArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is IntArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is LongArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is FloatArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is DoubleArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is BooleanArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is CharArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is UByteArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is UShortArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is UIntArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is ULongArray -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is Iterator<*> -> Iterable { value }.map { convertElement(it, elementType) }.convertToMutableSet()
				value is Iterable<*> -> value.map { convertElement(it, elementType) }.convertToMutableSet()
				value is Sequence<*> -> value.map { convertElement(it, elementType) }.toMutableSet()
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toMutableSet()
				value is String -> splitValue(value).map { convertElement(it, elementType) }.convertToMutableSet()
				value is CharSequence -> splitValue(value.toString()).map { convertElement(it, elementType) }
					.convertToMutableSet()
				else -> mutableSetOf(value.convert(elementType, passingParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)

		override fun componentCopy(componentParams: Map<String, Any?>) = MutableSetConverter(componentParams)
	}

	@OptIn(ExperimentalUnsignedTypes::class)
	object IntRangeConverter : AbstractConverter<IntRange>() {
		override fun convert(value: Any): IntRange {
			return when {
				value is IntRange -> value
				value is IntProgression -> value.first..value.last
				value is LongProgression -> value.first.toInt()..value.last.toInt()
				value is CharProgression -> value.first.code..value.last.code
				value is UIntProgression -> value.first.toInt()..value.last.toInt()
				value is ULongProgression -> value.first.toInt()..value.last.toInt()
				value is Pair<*, *> -> value.first!!.convert<Int>()..value.second!!.convert<Int>()
				value is List<*> -> value[0]!!.convert<Int>()..value[1]!!.convert<Int>()
				value is ClosedRange<*> -> value.start.convert<Int>()..value.endInclusive.convert<Int>()
				value is String -> doConvert(value)
				else -> doConvert(value.toString())
			}
		}

		private fun doConvert(value: String): IntRange {
			return value.split("..", limit = 2).let { it[0].convert<Int>()..it[1].convert<Int>() }
		}
	}

	@OptIn(ExperimentalUnsignedTypes::class)
	object LongRangeConverter : AbstractConverter<LongRange>() {
		override fun convert(value: Any): LongRange {
			return when {
				value is LongRange -> value
				value is IntProgression -> value.first.toLong()..value.last.toLong()
				value is LongProgression -> value.first..value.last
				value is CharProgression -> value.first.code.toLong()..value.last.code.toLong()
				value is UIntProgression -> value.first.toLong()..value.last.toLong()
				value is ULongProgression -> value.first.toLong()..value.last.toLong()
				value is Pair<*, *> -> value.first!!.convert<Long>()..value.second!!.convert<Long>()
				value is List<*> -> value[0]!!.convert<Long>()..value[1]!!.convert<Long>()
				value is ClosedRange<*> -> value.start.convert<Long>()..value.endInclusive.convert<Long>()
				value is String -> doConvert(value)
				else -> doConvert(value.toString())
			}
		}

		private fun doConvert(value: String): LongRange {
			return value.split("..", limit = 2).let { it[0].convert<Long>()..it[1].convert<Long>() }
		}
	}

	@OptIn(ExperimentalUnsignedTypes::class)
	object CharRangeConverter : AbstractConverter<CharRange>() {
		override fun convert(value: Any): CharRange {
			return when {
				value is CharRange -> value
				value is IntProgression -> value.first.toChar()..value.last.toChar()
				value is LongProgression -> value.first.toInt().toChar()..value.last.toInt().toChar()
				value is CharProgression -> value.first..value.last
				value is UIntProgression -> value.first.toChar()..value.last.toChar()
				value is ULongProgression -> value.first.toChar()..value.last.toChar()
				value is Pair<*, *> -> value.first!!.convert<Char>()..value.second!!.convert<Char>()
				value is List<*> -> value[0]!!.convert<Char>()..value[1]!!.convert<Char>()
				value is ClosedRange<*> -> value.start.convert<Char>()..value.endInclusive.convert<Char>()
				value is String -> doConvert(value)
				else -> doConvert(value.toString())
			}
		}

		private fun doConvert(value: String): CharRange {
			return value.split("..", limit = 2).let { it[0].convert<Char>()..it[1].convert<Char>() }
		}
	}

	@ExperimentalUnsignedTypes
	object UIntRangeConverter : AbstractConverter<UIntRange>() {
		override fun convert(value: Any): UIntRange {
			return when {
				value is UIntRange -> value
				value is IntProgression -> value.first.toUInt()..value.last.toUInt()
				value is LongProgression -> value.first.toUInt()..value.last.toUInt()
				value is CharProgression -> value.first.toUInt()..value.last.toUInt()
				value is UIntProgression -> value.first..value.last
				value is ULongProgression -> value.first.toUInt()..value.last.toUInt()
				value is Pair<*, *> -> value.first!!.convert<UInt>()..value.second!!.convert<UInt>()
				value is List<*> -> value[0]!!.convert<UInt>()..value[1]!!.convert<UInt>()
				value is ClosedRange<*> -> value.start.convert<UInt>()..value.endInclusive.convert<UInt>()
				value is String -> doConvert(value)
				else -> doConvert(value.toString())
			}
		}

		private fun doConvert(value: String): UIntRange {
			return value.split("..", limit = 2).let { it[0].convert<UInt>()..it[1].convert<UInt>() }
		}
	}

	@ExperimentalUnsignedTypes
	object ULongRangeConverter : AbstractConverter<ULongRange>() {
		override fun convert(value: Any): ULongRange {
			return when {
				value is ULongRange -> value
				value is IntProgression -> value.first.toULong()..value.last.toULong()
				value is LongProgression -> value.first.toULong()..value.last.toULong()
				value is CharProgression -> value.first.toULong()..value.last.toULong()
				value is UIntProgression -> value.first.toULong()..value.last.toULong()
				value is ULongProgression -> value.first..value.last
				value is Pair<*, *> -> value.first!!.convert<ULong>()..value.second!!.convert<ULong>()
				value is List<*> -> value[0]!!.convert<ULong>()..value[1]!!.convert<ULong>()
				value is ClosedRange<*> -> value.start.convert<ULong>()..value.endInclusive.convert<ULong>()
				value is String -> doConvert(value)
				else -> doConvert(value.toString())
			}
		}

		private fun doConvert(value: String): ULongRange {
			return value.split("..", limit = 2).let { it[0].convert<ULong>()..it[1].convert<ULong>() }
		}
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为序列时使用的分隔符。
	 * * prefix - 从字符串转化为序列时使用的前缀。
	 * * suffix - 从字符串转化为序列时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	open class SequenceConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Sequence<*>>(), GenericConverter<Sequence<*>> {
		companion object Default : SequenceConverter()

		private val iterableConverter by lazy {
			get(Iterable::class.java, passingParams) { IterableConverter(passingParams) }
		}

		private val passingParams = componentParams

		override fun convert(value: Any, targetType: Type): Sequence<*> {
			return iterableConverter.convert(value, targetType).asSequence()
		}

		override fun convertOrNull(value: Any, targetType: Type): Sequence<*>? {
			return iterableConverter.convertOrNull(value, targetType)?.asSequence()
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = SequenceConverter(componentParams)
	}

	/**
	 * 组件参数说明：
	 * * separator - 从字符串转化为流时使用的分隔符。
	 * * prefix - 从字符串转化为流时使用的前缀。
	 * * suffix - 从字符串转化为流时使用的后缀。
	 * * delegate - 委托的转化器（列表转化器/集转化器）。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ComponentParam("separator", "String", ",")
	@ComponentParam("prefix", "String", "")
	@ComponentParam("suffix", "String", "")
	@ComponentParam("delegate", "list | set", "list", passing = false)
	open class StreamConverter(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Stream<*>>(), GenericConverter<Stream<*>> {
		companion object Default : StreamConverter()

		private val listConverter by lazy {
			get(List::class.java, passingParams) { ListConverter(passingParams) }
		}
		private val setConverter by lazy {
			get(Set::class.java, passingParams) { SetConverter(passingParams) }
		}

		private val passingParams = filterNotComponentParams(componentParams, "delegate")

		val delegate: String = componentParams.get("delegate").convertToStringOrNull() ?: "list"

		override fun convert(value: Any, targetType: Type): Stream<*> {
			return when (delegate) {
				"list" -> listConverter.convert(value, targetType).stream()
				"set" -> setConverter.convert(value, targetType).stream()
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun convertOrNull(value: Any, targetType: Type): Stream<*>? {
			return when (delegate) {
				"list" -> listConverter.convertOrNull(value, targetType)?.stream()
				"set" -> setConverter.convertOrNull(value, targetType)?.stream()
				else -> throw IllegalArgumentException("Config param 'delegate' must be one of: list, set.")
			}
		}

		override fun componentCopy(componentParams: Map<String, Any?>) = StreamConverter(componentParams)
	}
	//endregion

	/**
	 * 是否使用回退策略。默认不使用。
	 *
	 * 如果使用回退策略且找不到匹配的转化器，则尝试调用目标类型的无参构造方法生成默认值。
	 */
	var useFallbackStrategy = false

	@OptIn(ExperimentalUnsignedTypes::class)
	override fun registerDefault() {
		register(ByteConverter)
		register(ShortConverter)
		register(IntConverter)
		register(LongConverter)
		register(FloatConverter)
		register(DoubleConverter)
		register(BooleanConverter)
		register(CharConverter)
		register(BigIntegerConverter)
		register(BigDecimalConverter)
		register(UByteConverter)
		register(UShortConverter)
		register(UIntConverter)
		register(ULongConverter)
		register(AtomicIntegerConverter)
		register(AtomicLongConverter)
		register(AtomicBooleanConverter)
		register(StringConverter)
		register(RegexConverter)
		register(PatternConverter)
		register(CharsetConverter)
		register(ClassConverter)
		register(LocaleConverter)
		register(TimeZoneConverter)
		register(ZoneIdConverter)
		register(DateConverter)
		register(LocalDateConverter)
		register(LocalTimeConverter)
		register(LocalDateTimeConverter)
		register(InstantConverter)
		register(DurationConverter)
		register(PeriodConverter)
		register(FileConverter)
		register(PathConverter)
		register(UrlConverter)
		register(UriConverter)
		register(EnumConverter)
		register(ArrayConverter)
		register(ByteArrayConverter)
		register(ShortArrayConverter)
		register(IntArrayConverter)
		register(LongArrayConverter)
		register(FloatArrayConverter)
		register(DoubleArrayConverter)
		register(BooleanArrayConverter)
		register(CharArrayConverter)
		register(UByteArrayConverter)
		register(UShortArrayConverter)
		register(UIntArrayConverter)
		register(ULongArrayConverter)
		register(IteratorConverter)
		register(MutableIteratorConverter)
		register(IterableConverter)
		register(MutableIterableConverter)
		register(CollectionConverter)
		register(MutableCollectionConverter)
		register(ListConverter)
		register(MutableListConverter)
		register(SetConverter)
		register(MutableSetConverter)
		register(IntRangeConverter)
		register(LongRangeConverter)
		register(CharRangeConverter)
		register(UIntRangeConverter)
		register(ULongRangeConverter)
	}

	/**
	 * 根据指定的目标类型和配置参数，从缓存中得到转化器。如果没有，则创建并放入。
	 */
	fun <T, C : Converter<T>> get(targetType: Class<T>, componentParams: Map<String, Any?>, defaultValue: () -> C): C {
		return components.getOrPut(inferKey(targetType, componentParams), defaultValue) as C
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则抛出异常。
	 */
	inline fun <reified T> convert(value: Any?, componentParams: Map<String, Any?> = emptyMap()): T {
		return convert(value, javaTypeOf<T>(), componentParams)
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则抛出异常。
	 */
	fun <T> convert(value: Any?, targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
		return doConvert(value, targetType, componentParams)
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则抛出异常。
	 */
	fun <T> convert(value: Any?, targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
		return doConvert(value, targetType, componentParams)
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则返回null。
	 */
	inline fun <reified T> convertOrNull(value: Any?, componentParams: Map<String, Any?> = emptyMap()): T? {
		return convertOrNull(value, javaTypeOf<T>(), componentParams)
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则返回null。
	 */
	fun <T> convertOrNull(value: Any?, targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T? {
		return doConvertOrNull(value, targetType, componentParams)
	}

	/**
	 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果指定的对象是null，或者转化失败，则返回null。
	 */
	fun <T> convertOrNull(value: Any?, targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T? {
		return doConvertOrNull(value, targetType, componentParams)
	}

	private fun <T> doConvert(value: Any?, targetType: Type, componentParams: Map<String, Any?>): T {
		//如果value是null，则直接抛出异常（因为无法判断targetType是否是可空类型）
		if (value == null) throw IllegalArgumentException("Cannot convert null value to a specified type.")
		val targetClass = inferClass(targetType)
		//如果value的类型不是泛型类型，且兼容targetType，则直接返回value
		if (targetClass == targetType && targetClass.isInstance(value)) return value as T
		//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
		val key = inferKey(targetClass, componentParams)
		val converter = components.getOrPut(key) {
			val result = infer(targetClass, componentParams)
			if (result == null) {
				//如果目标类型是字符串，则尝试转化为字符串
				if (targetType == String::class.java) return value.toString() as T
				if (useFallbackStrategy) {
					val fallback = fallbackConvert(value, targetClass)
					if (fallback != null) return fallback as T
				}
				throw IllegalArgumentException("No matched converter found for target type '$targetType'.")
			}
			result
		}
		when (converter) {
			is GenericConverter<*> -> return converter.convert(value, targetType) as T
			else -> return converter.convert(value) as T
		}
	}

	private fun <T> doConvertOrNull(value: Any?, targetType: Type, componentParams: Map<String, Any?>): T? {
		//如果value是null，则直接返回null
		if (value == null) return null
		val targetClass = inferClass(targetType)
		//如果value的类型不是泛型类型，且兼容targetType，则直接返回value
		if (targetClass == targetType && targetClass.isInstance(value)) return value as? T?
		//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
		val key = inferKey(targetClass, componentParams)
		val converter = components.getOrPut(key) {
			val result = infer(targetClass, componentParams)
			if (result == null) {
				//如果目标类型是字符串，则尝试转化为字符串
				if (targetType == String::class.java) return value.toString() as T
				if (useFallbackStrategy) {
					val fallback = fallbackConvert(value, targetClass)
					if (fallback != null) return fallback as? T?
				}
				return null
			}
			result
		}
		return when (converter) {
			is GenericConverter<*> -> converter.convertOrNull(value, targetType) as? T?
			else -> converter.convertOrNull(value) as? T?
		}
	}

	private fun inferKey(targetType: Class<*>, componentParams: Map<String, Any?>): String {
		return if (componentParams.isEmpty()) targetType.name else "${targetType.name}@$componentParams"
	}

	private fun infer(targetType: Class<*>, componentParams: Map<String, Any?>): Converter<*>? {
		var result = components.values.findLast { it.targetType.isAssignableFrom(targetType) } ?: return null
		if (result.componentParams.toString() != componentParams.toString()) {
			result = result.componentCopy(componentParams)
		}
		return result
	}

	private fun <T> fallbackConvert(value: Any, targetType: Class<T>): T? {
		try {
			//尝试调用目标类型的第一个拥有匹配的唯一参数的构造方法生成转化后的值
			for (constructor in targetType.declaredConstructors) {
				if (constructor.parameterCount == 1) {
					try {
						constructor.isAccessible = true
						return constructor.newInstance(value) as T
					} catch (e: Exception) {
					}
				}
			}
			//尝试调用目标类型的第一个拥有匹配的唯一参数的方法生成转化后的值
			for (method in targetType.declaredMethods) {
				if (method.parameterCount == 1) {
					try {
						method.isAccessible = true
						return method.invoke(value) as T
					} catch (e: Exception) {
					}
				}
			}
			return null
		} catch (e: Exception) {
			return null
		}
	}
}

