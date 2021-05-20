// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import java.io.*
import java.lang.reflect.*
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
import java.util.stream.*

/**
 * 转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 *
 * 同一兼容类型的转化器可以注册多个。
 */
@Suppress("RemoveExplicitTypeArguments")
interface Converter<T> : TypedComponent {
	override val targetType: Class<T>

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
			register(IntRangeConverter)
			register(LongRangeConverter)
			register(CharRangeConverter)
			register(UIntRangeConverter)
			register(ULongRangeConverter)
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
			register(ListConverter)
			register(SetConverter)
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
			return convert(value, javaTypeOf<T>(), configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@JvmStatic
		fun <T> convert(value: Any, targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T {
			return doConvert(value, targetType, configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@JvmStatic
		fun <T> convert(value: Any, targetType: Type, configParams: Map<String, Any?> = emptyMap()): T {
			return doConvert(value, targetType, configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		inline fun <reified T> convertOrNull(value: Any, configParams: Map<String, Any?> = emptyMap()): T? {
			return convertOrNull(value, javaTypeOf<T>(), configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@JvmStatic
		fun <T> convertOrNull(value: Any, targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T? {
			return doConvertOrNull(value, targetType, configParams)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@JvmStatic
		fun <T> convertOrNull(value: Any, targetType: Type, configParams: Map<String, Any?> = emptyMap()): T? {
			return doConvertOrNull(value, targetType, configParams)
		}

		@Suppress("UNCHECKED_CAST")
		private fun <T> doConvert(value: Any, targetType: Type, configParams: Map<String, Any?>): T {
			//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
			//如果value的类型不是泛型类型，且兼容targetType，则直接返回
			val targetClass = inferClass(targetType)
			if(targetClass == targetType && targetClass.isInstance(value)) return value as T
			val key = inferKey(targetClass, configParams)
			val converter = componentMap.getOrPut(key) {
				val result = inferConverter(targetClass, configParams)
				if(result == null) {
					//如果目标类型是字符串，则尝试转化为字符串
					if(targetType == String::class.java) return value.toString() as T
					if(useFallbackStrategy) {
						val fallback = fallbackConvert(value, targetClass)
						if(fallback != null) return fallback as T
					}
					throw IllegalArgumentException("No matched converter found for target type '$targetType'.")
				}
				result
			}
			when(converter) {
				is GenericConverter<*> -> return converter.convert(value, targetType) as T
				else -> return converter.convert(value) as T
			}
		}

		@Suppress("UNCHECKED_CAST")
		private fun <T, V : Any> doConvertOrNull(value: V, targetType: Type, configParams: Map<String, Any?>): T? {
			//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，并加入缓存
			//如果value的类型不是泛型类型，且兼容targetType，则直接返回
			val targetClass = inferClass(targetType)
			if(targetClass == targetType && targetClass.isInstance(value)) return value as? T?
			val key = inferKey(targetClass, configParams)
			val converter = componentMap.getOrPut(key) {
				val result = inferConverter(targetClass, configParams)
				if(result == null) {
					//如果目标类型是字符串，则尝试转化为字符串
					if(targetType == String::class.java) return value.toString() as T
					if(useFallbackStrategy) {
						val fallback = fallbackConvert(value, targetClass)
						if(fallback != null) return fallback as? T?
					}
					return null
				}
				result
			}
			when(converter) {
				is GenericConverter<*> -> return converter.convertOrNull(value, targetType) as? T?
				else -> return converter.convertOrNull(value) as? T?
			}
		}

		private fun inferKey(targetType: Type, configParams: Map<String, Any?>): String {
			return if(configParams.isEmpty()) targetType.toString() else "$targetType@$configParams"
		}

		private fun <T> inferConverter(targetType: Class<T>, configParams: Map<String, Any?>): Converter<*>? {
			var result = components.find { it.targetType.isAssignableFrom(targetType) }
			if(result is ConfigurableConverter<*> && configParams.isNotEmpty()) {
				result = result.configure(configParams)
			}
			if(result is BoundConverter<*> && targetType != result.targetType) {
				result = result.bindingActualTargetType(targetType)
			}
			return result
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

	@ConfigParam("raw", "Boolean", "false")
	@ConfigParam("format", "String", "yyyy-MM-dd")
	@ConfigParam("locale", "String | Locale", "<default>")
	@ConfigParam("timeZone", "String | TimeZone", "<utc>")
	open class StringConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<String>(), ConfigurableConverter<String> {
		companion object Default : StringConverter()

		val raw: Boolean = configParams.get("raw").convertOrNull() ?: false
		val format: String = configParams.get("format").convertOrNull() ?: defaultDateFormat
		val locale: Locale = configParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = configParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val threadLocalDateFormat =
			ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): StringConverter {
			return StringConverter(configParams)
		}

		override fun convert(value: Any): String {
			return when {
				raw -> value.toString()
				value is Date -> threadLocalDateFormat.get().format(value)
				value is TemporalAccessor -> formatter.format(value)
				else -> value.smartToString()
			}
		}
	}

	@ConfigParam("regexOptions", "Array<RegexOption> | Iterable<RegexOption> | Sequence<RegexOption>", "<empty>")
	open class RegexConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Regex>(), ConfigurableConverter<Regex> {
		companion object Default : RegexConverter()

		val regexOptions: Set<RegexOption> = configParams.get("regexOptions")?.let {
			when {
				it is Array<*> -> it.filterIsInstanceTo(mutableSetOf())
				it is Iterable<*> -> it.filterIsInstanceTo(mutableSetOf())
				it is Sequence<*> -> it.filterIsInstanceTo(mutableSetOf())
				else -> emptySet()
			}
		} ?: emptySet()

		override fun configure(configParams: Map<String, Any?>): RegexConverter {
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

	@ConfigParam("format", "String", "yyyy-MM-dd")
	@ConfigParam("locale", "String | Locale", "<default>")
	@ConfigParam("timeZone", "String | TimeZone", "<utc>")
	open class DateConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Date>(), ConfigurableConverter<Date> {
		companion object Default : DateConverter()

		val format: String = configParams.get("format").convertOrNull() ?: defaultDateFormat
		val locale: Locale = configParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = configParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val threadLocalDateFormat =
			ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }

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

	@ConfigParam("format", "String", "yyyy-MM-dd")
	@ConfigParam("locale", "String | Locale", "<default>")
	@ConfigParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalDateConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDate>(), ConfigurableConverter<LocalDate> {
		companion object Default : LocalDateConverter()

		val format: String = configParams.get("format").convertOrNull() ?: defaultDateFormat
		val locale: Locale = configParams.get("locale").convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = configParams.get("timeZone").convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalDateConverter {
			return LocalDateConverter(configParams)
		}

		override fun convert(value: Any): LocalDate {
			return when {
				value is LocalDate -> value
				value is TemporalAccessor -> LocalDate.from(value)
				value is Date -> LocalDate.from(value.toInstant())
				value is String -> LocalDate.parse(value, formatter)
				else -> LocalDate.parse(value.toString(), formatter)
			}
		}
	}

	@ConfigParam("format", "String", "HH:mm:ss")
	@ConfigParam("locale", "String | Locale", "<default>")
	@ConfigParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalTimeConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalTime>(), ConfigurableConverter<LocalTime> {
		companion object Default : LocalTimeConverter()

		val format: String = configParams.get("format")?.convertOrNull() ?: defaultTimeFormat
		val locale: Locale = configParams.get("locale")?.convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = configParams.get("timeZone")?.convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalTimeConverter {
			return LocalTimeConverter(configParams)
		}

		override fun convert(value: Any): LocalTime {
			return when {
				value is LocalTime -> value
				value is TemporalAccessor -> LocalTime.from(value)
				value is Date -> LocalTime.from(value.toInstant())
				value is String -> LocalTime.parse(value, formatter)
				else -> LocalTime.parse(value.toString(), formatter)
			}
		}
	}

	@ConfigParam("format", "String", "yyyy-MM-dd HH:mm:ss")
	@ConfigParam("locale", "String | Locale", "<default>")
	@ConfigParam("timeZone", "String | TimeZone", "<utc>")
	open class LocalDateTimeConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LocalDateTime>(), ConfigurableConverter<LocalDateTime> {
		companion object Default : LocalDateTimeConverter()

		val format: String = configParams.get("format")?.convertOrNull() ?: defaultDateTimeFormat
		val locale: Locale = configParams.get("locale")?.convertOrNull() ?: defaultLocale
		val timeZone: TimeZone = configParams.get("timeZone")?.convertOrNull() ?: defaultTimeZone

		private val formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())

		override fun configure(configParams: Map<String, Any?>): LocalDateTimeConverter {
			return LocalDateTimeConverter(configParams)
		}

		override fun convert(value: Any): LocalDateTime {
			return when {
				value is LocalDateTime -> value
				value is TemporalAccessor -> LocalDateTime.from(value)
				value is Date -> LocalDateTime.from(value.toInstant())
				value is String -> LocalDateTime.parse(value, formatter)
				else -> LocalDateTime.parse(value.toString(), formatter)
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
	open class DurationConverter(
		override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Duration>(), ConfigurableConverter<Duration> {
		companion object Default : DurationConverter()

		override fun configure(configParams: Map<String, Any?>): DurationConverter {
			return DurationConverter(configParams)
		}

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

	@ExperimentalUnsignedTypes
	object IntRangeConverter : AbstractConverter<IntRange>() {
		override fun convert(value: Any): IntRange {
			return when {
				value is IntRange -> value
				value is IntProgression -> value.first..value.last
				value is LongProgression -> value.first.toInt()..value.last.toInt()
				value is CharProgression -> value.first.toInt()..value.last.toInt()
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

	@ExperimentalUnsignedTypes
	object LongRangeConverter : AbstractConverter<LongRange>() {
		override fun convert(value: Any): LongRange {
			return when {
				value is LongRange -> value
				value is IntProgression -> value.first.toLong()..value.last.toLong()
				value is LongProgression -> value.first..value.last
				value is CharProgression -> value.first.toLong()..value.last.toLong()
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

	@ExperimentalUnsignedTypes
	object CharRangeConverter : AbstractConverter<CharRange>() {
		override fun convert(value: Any): CharRange {
			return when {
				value is CharRange -> value
				value is IntProgression -> value.first.toChar()..value.last.toChar()
				value is LongProgression -> value.first.toChar()..value.last.toChar()
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

	open class EnumConverter(
		override val actualTargetType: Class<out Enum<*>> = Enum::class.java
	) : AbstractConverter<Enum<*>>(), BoundConverter<Enum<*>> {
		companion object Default : EnumConverter()

		private val enumClass: Class<out Enum<*>> by lazy { actualTargetType }
		private val enumValues: List<Enum<*>> by lazy { if(enumClass == Enum::class.java) emptyList() else enumClass.enumConstants.toList() }
		private val enumValueMap: Map<String, Enum<*>> by lazy { enumValues.associateBy { it.name.toLowerCase() } }

		@Suppress("UNCHECKED_CAST")
		override fun bindingActualTargetType(actualTargetType: Type): BoundConverter<Enum<*>> {
			if(actualTargetType !is Class<*>) throw IllegalArgumentException("Actual target type should be an enum type.")
			return EnumConverter(actualTargetType as Class<out Enum<*>>)
		}

		override fun convert(value: Any): Enum<*> {
			if(enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
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
			if(enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
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

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class ArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Array<*>>(), ConfigurableConverter<Array<*>>, GenericConverter<Array<*>> {
		companion object Default : ArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): ArrayConverter {
			return ArrayConverter(configParams)
		}

		override fun convert(value: Any, targetType: Type): Array<*> {
			val elementType = inferTypeArgument(targetType, this.targetType)
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

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class ByteArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ByteArray>(), ConfigurableConverter<ByteArray> {
		companion object Default : ByteArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): ByteArrayConverter {
			return ByteArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ByteArray(v.size) { convertElement(v[it]) } }
				else -> byteArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Byte>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class ShortArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ShortArray>(), ConfigurableConverter<ShortArray> {
		companion object Default : ShortArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): ShortArrayConverter {
			return ShortArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ShortArray(v.size) { convertElement(v[it]) } }
				else -> shortArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Short>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class IntArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<IntArray>(), ConfigurableConverter<IntArray> {
		companion object Default : IntArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): IntArrayConverter {
			return IntArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> IntArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> IntArray(v.size) { convertElement(v[it]) } }
				else -> intArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Int>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class LongArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<LongArray>(), ConfigurableConverter<LongArray> {
		companion object Default : LongArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): LongArrayConverter {
			return LongArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> LongArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> LongArray(v.size) { convertElement(v[it]) } }
				else -> longArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Long>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class FloatArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<FloatArray>(), ConfigurableConverter<FloatArray> {
		companion object Default : FloatArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): FloatArrayConverter {
			return FloatArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> FloatArray(v.size) { convertElement(v[it]) } }
				else -> floatArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Float>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class DoubleArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<DoubleArray>(), ConfigurableConverter<DoubleArray> {
		companion object Default : DoubleArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): DoubleArrayConverter {
			return DoubleArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> DoubleArray(v.size) { convertElement(v[it]) } }
				else -> doubleArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Double>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class BooleanArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<BooleanArray>(), ConfigurableConverter<BooleanArray> {
		companion object Default : BooleanArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): BooleanArrayConverter {
			return BooleanArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> BooleanArray(v.size) { convertElement(v[it]) } }
				else -> booleanArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Boolean>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class CharArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<CharArray>(), ConfigurableConverter<CharArray> {
		companion object Default : CharArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): CharArrayConverter {
			return CharArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> CharArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> CharArray(v.size) { convertElement(v[it]) } }
				else -> charArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<Char>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class UByteArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UByteArray>(), ConfigurableConverter<UByteArray> {
		companion object Default : UByteArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): UByteArrayConverter {
			return UByteArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UByteArray(v.size) { convertElement(v[it]) } }
				else -> ubyteArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UByte>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class UShortArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UShortArray>(), ConfigurableConverter<UShortArray> {
		companion object Default : UShortArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): UShortArrayConverter {
			return UShortArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UShortArray(v.size) { convertElement(v[it]) } }
				else -> ushortArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UShort>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class UIntArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<UIntArray>(), ConfigurableConverter<UIntArray> {
		companion object Default : UIntArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): UIntArrayConverter {
			return UIntArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> UIntArray(v.size) { convertElement(v[it]) } }
				else -> uintArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<UInt>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为数组时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为数组时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为数组时使用的后缀。
	 */
	@ExperimentalUnsignedTypes
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class ULongArrayConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<ULongArray>(), ConfigurableConverter<ULongArray> {
		companion object Default : ULongArrayConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): ULongArrayConverter {
			return ULongArrayConverter(configParams)
		}

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
				value is Iterator<*> -> Iterable { value }.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Iterable<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Sequence<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is Stream<*> -> value.toList().let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is String -> splitValue(value).let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				value is CharSequence -> splitValue(value.toString()).let { v -> ULongArray(v.size) { convertElement(v[it]) } }
				else -> ulongArrayOf(convertElement(value))
			}
		}

		private fun convertElement(element: Any?) = element.convert<ULong>(passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为列表时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为列表时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为列表时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class ListConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<List<*>>(), ConfigurableConverter<List<*>>, GenericConverter<List<*>> {
		companion object Default : ListConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): ListConverter {
			return ListConverter(configParams)
		}

		override fun convert(value: Any, targetType: Type): List<*> {
			val elementType = inferTypeArgument(targetType, this.targetType)
			return when {
				value is Array<*> -> value.map { convertElement(it, elementType) }
				value is ByteArray -> value.map { convertElement(it, elementType) }
				value is ShortArray -> value.map { convertElement(it, elementType) }
				value is IntArray -> value.map { convertElement(it, elementType) }
				value is LongArray -> value.map { convertElement(it, elementType) }
				value is FloatArray -> value.map { convertElement(it, elementType) }
				value is DoubleArray -> value.map { convertElement(it, elementType) }
				value is BooleanArray -> value.map { convertElement(it, elementType) }
				value is CharArray -> value.map { convertElement(it, elementType) }
				value is UByteArray -> value.map { convertElement(it, elementType) }
				value is UShortArray -> value.map { convertElement(it, elementType) }
				value is UIntArray -> value.map { convertElement(it, elementType) }
				value is ULongArray -> value.map { convertElement(it, elementType) }
				value is Iterator<*> -> Iterable { value }.map { convertElement(it, elementType) }
				value is Iterable<*> -> value.map { convertElement(it, elementType) }
				value is Sequence<*> -> value.mapTo(mutableListOf()) { convertElement(it, elementType) }
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toList()
				value is String -> splitValue(value).map { convertElement(it, elementType) }
				value is CharSequence -> splitValue(value.toString()).map { convertElement(it, elementType) }
				else -> listOf(value.convert(elementType, passingConfigParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}

	/**
	 * 参数说明：
	 * * separator - 从字符串（字符序列）转化为集时使用的分隔符。
	 * * prefix - 从字符串（字符序列）转化为集时使用的前缀。
	 * * suffix - 从字符串（字符序列）转化为集时使用的后缀。
	 */
	@OptIn(ExperimentalUnsignedTypes::class)
	@ConfigParam("separator", "String", ",")
	@ConfigParam("prefix", "String", "")
	@ConfigParam("suffix", "String", "")
	@ConfigParamsPassing(ConfigurableConverter::class, "!separator,!prefix,!suffix")
	open class SetConverter(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractConverter<Set<*>>(), ConfigurableConverter<Set<*>>, GenericConverter<Set<*>> {
		companion object Default : SetConverter()

		private val passingConfigParams = filterNotConfigParams(configParams, "separator", "prefix", "suffix")

		val separator = configParams.get("separator")?.toString() ?: ","
		val prefix = configParams.get("prefix")?.toString() ?: ""
		val suffix = configParams.get("suffix")?.toString() ?: ""

		override fun configure(configParams: Map<String, Any?>): SetConverter {
			return SetConverter(configParams)
		}

		override fun convert(value: Any, targetType: Type): Set<*> {
			val elementType = inferTypeArgument(targetType, this.targetType)
			return when {
				value is Array<*> -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is ByteArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is ShortArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is IntArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is LongArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is FloatArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is DoubleArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is BooleanArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is CharArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is UByteArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is UShortArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is UIntArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is ULongArray -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is Iterator<*> -> Iterable { value }.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is Iterable<*> -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is Sequence<*> -> value.mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is Stream<*> -> value.map { convertElement(it, elementType) }.toLinkedSet()
				value is String -> splitValue(value).mapTo(mutableSetOf()) { convertElement(it, elementType) }
				value is CharSequence -> splitValue(value.toString()).mapTo(mutableSetOf()) { convertElement(it, elementType) }
				else -> setOf(value.convert(elementType, passingConfigParams))
			}
		}

		private fun convertElement(element: Any?, elementType: Type) = element.convert(elementType, passingConfigParams)

		private fun splitValue(value: String) = value.splitToStrings(separator, prefix, suffix)
	}
	//endregion
}
