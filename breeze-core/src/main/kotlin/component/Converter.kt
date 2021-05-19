// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.model.*
import java.io.*
import java.math.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.time.temporal.*
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.*
import java.util.regex.*

/**
 * 转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 *
 * 同一兼容类型的转化器可以注册多个。
 */
@Suppress("RemoveExplicitTypeArguments")
interface Converter<T> : Component {
	/**目标类型。*/
	val targetType: Class<T>

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
	 */
	@Suppress("UNCHECKED_CAST")
	fun convert(value: Any): T

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
	 */
	fun convertOrNull(value: Any): T? {
		return runCatching { convert(value) }.getOrNull()
	}

	companion object Registry : AbstractComponentRegistry<Converter<*>>() {
		@OptIn(ExperimentalUnsignedTypes::class)
		override fun registerDefault() {
			register(ByteConverter)
			register(ShortConverter)
			register(IntConverter)
			register(LongConverter)
			register(FloatConverter)
			register(DoubleConverter)
			register(BigIntegerConverter)
			register(BigDecimalConverter)
			register(UByteConverter)
			register(UShortConverter)
			register(UIntConverter)
			register(ULongConverter)
			register(AtomicIntegerConverter)
			register(AtomicLongConverter)
			register(BooleanConverter)
			register(CharConverter)
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
		}

		private val componentMap: MutableMap<String, Converter<*>> = ConcurrentHashMap()

		/**
		 * 是否使用回退策略。默认不使用。
		 * 如果使用回退策略且找不到匹配的转化器，则尝试调用目标类型的无参构造方法生成默认值。
		 */
		var useFallbackStrategy = false

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convert(value: Any, configParams: Map<String, Any?> = emptyMap()): T {
			return convert(value, T::class.java, configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> convert(value: Any, targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T {
			//如果value的类型兼容targetType，则直接返回
			if(targetType.isInstance(value)) return value as T
			//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
			val key = if(configParams.isEmpty()) targetType.name else targetType.name + '@' + configParams.toString()
			val converter = componentMap.getOrPut(key){
				var result = components.find { it.targetType.isAssignableFrom(targetType) }
				if(result is ConfigurableConverter<*> && configParams.isNotEmpty()) {
					result = result.configure(configParams)
				}else if(result is BindingTargetTypeConverter<*> && targetType != result.targetType){
					result = result.bindingTargetType(targetType)
				}
				if(result == null) {
					//如果目标类型是字符串，则尝试转化为字符串
					if(targetType == String::class.java) return value.toString() as T
					if(useFallbackStrategy) {
						val fallback = fallbackConvert(value, targetType)
						if(fallback != null) return fallback
					}
					throw IllegalArgumentException("No matched converter found for target type '$targetType'.")
				}
				result
			}
			return converter.convert(value) as T
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convertOrNull(value: Any, configParams: Map<String, Any?> = emptyMap()): T? {
			return convertOrNull(value, T::class.java, configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> convertOrNull(value: Any, targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T? {
			//如果value的类型兼容targetType，则直接返回
			if(targetType.isInstance(value)) return value as T?
			//否则，尝试使用第一个匹配且可用的转化器进行转化
			//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
			val key = if(configParams.isEmpty()) targetType.name else targetType.name + '@' + configParams.toString()
			val converter = componentMap.getOrPut(key){
				var result = components.find { it.targetType.isAssignableFrom(targetType) }
				if(result is ConfigurableConverter<*> && configParams.isNotEmpty()){
					result = result.configure(configParams)
				}
				if(result == null) {
					//如果目标类型是字符串，则尝试转化为字符串
					if(targetType == String::class.java) return value.toString() as T
					if(useFallbackStrategy) {
						val fallback = fallbackConvert(value, targetType)
						if(fallback != null) return fallback
					}
					return null
				}
				result
			}
			return converter.convertOrNull(value) as T?
		}

		@Suppress("UNCHECKED_CAST")
		private fun <T> fallbackConvert(value: Any, targetType: Class<T>): T? {
			try {
				//尝试调用目标类型的第一个拥有匹配的唯一参数的构造方法生成转化后的值
				for(constructor in targetType.declaredConstructors) {
					if(constructor.parameterCount == 1) {
						try {
							constructor.isAccessible = true
							return constructor.newInstance(value) as T
						} catch(e: Exception) {
						}
					}
				}
				//尝试调用目标类型的第一个拥有匹配的唯一参数的方法生成转化后的值
				for(method in targetType.declaredMethods) {
					if(method.parameterCount == 1) {
						try {
							method.isAccessible = true
							return method.invoke(value) as T
						} catch(e: Exception) {
						}
					}
				}
				return null
			} catch(e: Exception) {
				return null
			}
		}
	}

	//region Converters
	object ByteConverter : AbstractConverter<Byte>() {
		override fun convert(value: Any): Byte {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.toByte()
				value is Boolean -> value.toByte()
				else -> value.toString().toByte()
			}
		}

		override fun convertOrNull(value: Any): Byte? {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.toByte()
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
				value is Char -> value.toShort()
				value is Boolean -> value.toShort()
				else -> value.toString().toShort()
			}
		}

		override fun convertOrNull(value: Any): Short? {
			return when {
				value is Short -> value
				value is Number -> value.toShort()
				value is Char -> value.toShort()
				else -> value.toString().toShortOrNull()
			}
		}
	}

	object IntConverter : AbstractConverter<Int>() {
		override fun convert(value: Any): Int {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.toInt()
				value is Boolean -> value.toInt()
				else -> value.toString().toInt()
			}
		}

		override fun convertOrNull(value: Any): Int? {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.toInt()
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
				value is Char -> value.toLong()
				value is Boolean -> value.toLong()
				else -> value.toString().toLong()
			}
		}

		override fun convertOrNull(value: Any): Long? {
			return when {
				value is Long -> value
				value is Number -> value.toLong()
				value is Char -> value.toLong()
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
				value is Char -> value.toFloat()
				value is Boolean -> value.toFloat()
				else -> value.toString().toFloat()
			}
		}

		override fun convertOrNull(value: Any): Float? {
			return when {
				value is Float -> value
				value is Number -> value.toFloat()
				value is Char -> value.toFloat()
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
				value is Char -> value.toDouble()
				value is Boolean -> value.toDouble()
				else -> value.toString().toDouble()
			}
		}

		override fun convertOrNull(value: Any): Double? {
			return when {
				value is Double -> value
				value is Number -> value.toDouble()
				value is Char -> value.toDouble()
				value is Boolean -> value.toDouble()
				else -> value.toString().toDoubleOrNull()
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

	object BooleanConverter : AbstractConverter<Boolean>() {
		override fun convert(value: Any): Boolean {
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

	@ConfigParams(
		ConfigParam("raw", "Boolean", "false"),
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class StringConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<String>(), ConfigurableConverter<String> {
		companion object Default : StringConverter()

		val raw:Boolean = configParams.get("raw").convertOrNull()?: false
		val format:String = configParams.get("format").convertOrNull() ?:defaultDateFormat
		val locale:Locale = configParams.get("locale").convertOrNull()?: defaultLocale
		val timeZone:TimeZone =configParams.get("timeZone").convertOrNull()?: defaultTimeZone

		private val threadLocalDateFormat = ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): StringConverter {
			return StringConverter(configParams)
		}

		override fun convert(value: Any): String {
			return when {
				raw -> value.toString()
				value is Date -> threadLocalDateFormat.get().format(value)
				value is TemporalAccessor -> formatter.format(value)
				else -> value.toString()
			}
		}
	}

	@ConfigParams(
		ConfigParam("regexOptions", "Array<RegexOption> | Iterable<RegexOption> | Sequence<RegexOption>","<empty>")
	)
	open class RegexConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Regex>(), ConfigurableConverter<Regex> {
		companion object Default : RegexConverter()

		val regexOptions:Set<RegexOption> = configParams.get("regexOptions")?.let {
			when {
				it is Array<*> -> it.filterIsInstanceTo(mutableSetOf())
				it is Iterable<*> -> it.filterIsInstanceTo(mutableSetOf())
				it is Sequence<*> -> it.filterIsInstanceTo(mutableSetOf())
				else -> emptySet()
			}
		}?: emptySet()

		override fun configure(configParams: Map<String, Any?>):RegexConverter {
			return RegexConverter(configParams)
		}

		override fun convert(value: Any): Regex {
			return when {
				value is Regex -> value
				value is Pattern -> value.toRegex()
				else -> {
					when(regexOptions.size) {
						0 -> value.toString().toRegex()
						1 -> value.toString().toRegex(regexOptions.first())
						else -> value.toString().toRegex(regexOptions)
					}
				}
			}
		}
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

	@ConfigParams(
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class DateConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Date>(), ConfigurableConverter<Date> {
		companion object Default : DateConverter()

		val format:String = configParams.get("format").convertOrNull() ?:defaultDateFormat
		val locale:Locale = configParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone:TimeZone = configParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val threadLocalDateFormat = ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }

		override fun configure(configParams: Map<String, Any?>): DateConverter {
			return DateConverter(configParams)
		}

		override fun convert(value: Any): Date {
			return when {
				value is Date -> value
				value is Instant -> Date.from(value)
				value is String -> threadLocalDateFormat.get().parse(value)
				else -> threadLocalDateFormat.get().parse(value.toString())
			}
		}
	}

	@ConfigParams(
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class LocalDateConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDate>(), ConfigurableConverter<LocalDate> {
		companion object Default : LocalDateConverter()

		val format:String = configParams.get("format").convertOrNull() ?:defaultDateFormat
		val locale:Locale = configParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone:TimeZone =configParams.get("timeZone").convertOrNull()?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalDateConverter {
			return LocalDateConverter(configParams)
		}

		override fun convert(value: Any): LocalDate {
			return when {
				value is LocalDate -> value
				value is TemporalAccessor -> LocalDate.from(value)
				value is Date -> LocalDate.from(value.toInstant())
				value is String -> LocalDate.parse(value,formatter)
				else -> LocalDate.parse(value.toString(),formatter)
			}
		}
	}

	@ConfigParams(
		ConfigParam("format", "String", "HH:mm:ss"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class LocalTimeConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalTime>(), ConfigurableConverter<LocalTime> {
		companion object Default : LocalTimeConverter()

		val format:String = configParams.get("format")?.convertOrNull() ?:defaultTimeFormat
		val locale:Locale = configParams.get("locale")?.convertOrNull()?: defaultLocale
		val timeZone:TimeZone =configParams.get("timeZone")?.convertOrNull()?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalTimeConverter {
			return LocalTimeConverter(configParams)
		}

		override fun convert(value: Any): LocalTime {
			return when {
				value is LocalTime -> value
				value is TemporalAccessor -> LocalTime.from(value)
				value is Date -> LocalTime.from(value.toInstant())
				value is String -> LocalTime.parse(value,formatter)
				else -> LocalTime.parse(value.toString(),formatter)
			}
		}
	}

	@ConfigParams(
		ConfigParam("format", "String", "'yyyy-MM-dd HH:mm:ss'"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class LocalDateTimeConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDateTime>(), ConfigurableConverter<LocalDateTime> {
		companion object Default : LocalDateTimeConverter()

		val format:String = configParams.get("format")?.convertOrNull() ?:defaultDateTimeFormat
		val locale:Locale = configParams.get("locale")?.convertOrNull()?: defaultLocale
		val timeZone:TimeZone =configParams.get("timeZone")?.convertOrNull()?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalDateTimeConverter {
			return LocalDateTimeConverter(configParams)
		}

		override fun convert(value: Any): LocalDateTime {
			return when {
				value is LocalDateTime -> value
				value is TemporalAccessor -> LocalDateTime.from(value)
				value is Date -> LocalDateTime.from(value.toInstant())
				value is String -> LocalDateTime.parse(value,formatter)
				else -> LocalDateTime.parse(value.toString(),formatter)
			}
		}
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
	object DurationConverter : AbstractConverter<Duration>() {
		override fun convert(value: Any): Duration {
			return when {
				value is Duration -> value
				value is TemporalAmount -> Duration.from(value)
				value is Pair<*, *> -> {
					val (start, end) = value
					if(start is Temporal && end is Temporal) {
						Duration.between(start, end)
					} else {
						throw IllegalArgumentException("Cannot convert '$value' to Duration.")
					}
				}
				else -> Duration.parse(value.toString())
			}
		}
	}

	//TODO
	open class PeriodConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Period>(), ConfigurableConverter<Period> {
		companion object Default : PeriodConverter()

		override fun configure(configParams: Map<String, Any?>): PeriodConverter {
			return PeriodConverter(configParams)
		}

		override fun convert(value: Any): Period {
			return when {
				value is Period -> value
				value is TemporalAmount -> Period.from(value)
				value is Pair<*, *> -> {
					val (start, end) = value
					if(start is LocalDate && end is LocalDate) {
						Period.between(start, end)
					} else {
						throw IllegalArgumentException("Cannot convert '$value' to Period.")
					}
				}
				value is String -> Period.parse(value)
				else -> Period.parse(value.toString())
			}
		}
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

	open class EnumConverter(
		override val actualTargetType: Class<out Enum<*>> = Enum::class.java
	): AbstractConverter<Enum<*>>(), BindingTargetTypeConverter<Enum<*>> {
		companion object Default : EnumConverter()

		private val enumClass: Class<out Enum<*>> by lazy { actualTargetType }
		private val enumValues: List<Enum<*>> by lazy { enumClass.enumConstants?.toList()?: emptyList() }
		private val enumValueMap: Map<String, Enum<*>> by lazy { enumValues.associateBy { it.name.toLowerCase() } }

		@Suppress("UNCHECKED_CAST")
		override fun bindingTargetType(actualTargetType: Class<*>): BindingTargetTypeConverter<Enum<*>> {
			return EnumConverter(actualTargetType as Class<out Enum<*>>)
		}

		override fun convert(value: Any): Enum<*> {
			if(enumClass == targetType) throw IllegalArgumentException("Cannot get actual enum class.")
			return when {
				value is Number -> {
					val index = value.toInt()
					enumValues.getOrNull(index) ?: throw IllegalArgumentException("Cannot find enum constant by index '$index'.")
				}
				else -> {
					val name = value.toString().toLowerCase()
					enumValueMap[name] ?: throw IllegalArgumentException("Cannot find enum constant by name '$name'.")
				}
			}
		}

		override fun convertOrNull(value: Any): Enum<*>? {
			if(enumClass == targetType) throw IllegalArgumentException("Cannot get actual enum class.")
			return when {
				value is Number -> {
					val index = value.toInt()
					enumValues.getOrNull(index)
				}
				else -> {
					val name = value.toString()
					enumValueMap[name]
				}
			}
		}
	}

	class ArrayConverter:AbstractConverter<Array<*>>(){
		override fun convert(value: Any): Array<*> {
			TODO("Not yet implemented")
		}
	}

	//open class StringArrayConverter : Converter<Array<String>>, Configurable<StringArrayConverter> {
	//	companion object Default : StringArrayConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<Array<String>> = Array<String>::class.java
	//
	//	override fun configure(configParams: Map<String, Any?>) {
	//		super.configure(configParams)
	//	}
	//
	//	override fun configurable(configParams: Map<String, Any?>): StringArrayConverter {
	//
	//	}
	//
	//	override fun convert(value: Any,configParams:Map<String,Any?>): Array<String> {
	//
	//	}
	//}
	//
	//open class StringListConverter : Converter<List<*>>, Configurable<StringListConverter> {
	//	companion object Default : StringListConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<List<*>> = List::class.java
	//
	//	override fun configure(configParams: Map<String, Any?>) {
	//		super.configure(configParams)
	//	}
	//
	//	override fun configurable(configParams: Map<String, Any?>): StringListConverter {
	//
	//	}
	//
	//	override fun convert(value: Any,configParams:Map<String,Any?>): List<String> {
	//
	//	}
	//}
	//
	//open class StringSetConverter : Converter<Set<*>>, Configurable<StringSetConverter> {
	//	companion object Default : StringSetConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<Set<*>> = Set::class.java
	//
	//	override fun configure(configParams: Map<String, Any?>) {
	//		super.configure(configParams)
	//	}
	//
	//	override fun configurable(configParams: Map<String, Any?>): StringSetConverter {
	//
	//	}
	//
	//	override fun convert(value: Any,configParams:Map<String,Any?>): Set<String> {
	//
	//	}
	//}
	//
	//open class StringSequenceConverter : Converter<Sequence<*>>, Configurable<StringSequenceConverter> {
	//	companion object Default : StringSequenceConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<Sequence<*>> = Sequence::class.java
	//
	//	override fun configure(configParams: Map<String, Any?>) {
	//		super.configure(configParams)
	//	}
	//
	//	override fun configurable(configParams: Map<String, Any?>): StringSequenceConverter {
	//
	//	}
	//
	//	override fun convert(value: Any,configParams:Map<String,Any?>): Sequence<String> {
	//
	//	}
	//}
	//
	//open class StringMapConverter : Converter<Map<*, *>>, Configurable<StringMapConverter> {
	//	companion object Default : StringMapConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<Map<*, *>> = Map::class.java
	//
	//	override fun configure(configParams: Map<String, Any?>) {
	//		super.configure(configParams)
	//	}
	//
	//	override fun configurable(configParams: Map<String, Any?>): StringMapConverter {
	//
	//	}
	//
	//	override fun convert(value: Any,configParams:Map<String,Any?>): Map<String, String> {
	//
	//	}
	//}
	//endregion
}
