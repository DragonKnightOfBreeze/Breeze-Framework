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
import java.util.concurrent.atomic.*
import java.util.regex.*

/**
 * 转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 *
 * 同一兼容类型的转化器可以注册多个。
 */
interface Converter<T> : Component {
	/**
	 * 目标类型。
	 */
	//val targetType: Class<T>
	val targetType: Class<T> get() = inferGenericType(this,Converter::class.java)

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
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convert(value: Any?, params: Map<String, Any?> = emptyMap()): T {
			return convert(value, T::class.java, params)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> convert(value: Any?, targetType: Class<T>, params: Map<String, Any?> = emptyMap()): T {
			when {
				//value为null时，如果可以，转化为字符串，否则尝试转化为T，可能会出错
				value == null -> return (if(targetType == String::class.java) "null" else null) as T
				//如果value的类型兼容targetType，则直接返回
				targetType.isInstance(value) -> return value as T
				//否则，尝试使用第一个匹配且可用的转化器进行转化
				//如果没有匹配且可用的转化器，先判断targetType是否是String，如果是，则直接调用toString()，否则抛出异常
				else -> {
					//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，如果转化失败，则继续遍历，不会因此报错
					for((index, converter) in components.withIndex()) {
						try {
							if(converter.targetType.isAssignableFrom(targetType)) {
								//如果是可配置的转化器，需要确认参数是否一致，如果不一致，则要新注册转化器
								if(converter is Configurable<*> && converter.configurableInfo.params != params) {
									val newConverter = converter.copy(params) as Converter<*>
									components.add(index, newConverter)
									return (newConverter as Converter<Any?>).convert(value) as T
								} else {
									return (converter as Converter<Any?>).convert(value) as T
								}
							}
						} catch(e: Exception) {
							e.printStackTrace()
						}
					}
					return if(targetType == String::class.java) {
						value.toString() as T
					} else {
						throw IllegalArgumentException("No suitable converter found for target type '$targetType'.")
					}
				}
			}
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convertOrNull(value: Any?, params: Map<String, Any?> = emptyMap()): T? {
			return convertOrNull(value, T::class.java, params)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> convertOrNull(value: Any?, targetType: Class<T>, params: Map<String, Any?> = emptyMap()): T? {
			when {
				//value为null时，如果可以，转化为字符串，否则尝试转化为T，可能会出错
				value == null -> return (if(targetType == String::class.java) "null" else null) as T?
				//如果value的类型兼容targetType，则直接返回
				targetType.isInstance(value) -> return value as T?
				//否则，尝试使用第一个匹配且可用的转化器进行转化
				//如果没有匹配且可用的转化器，先判断targetType是否是String，如果是，则直接调用toString()，否则返回null
				else -> {
					//遍历已注册的转化器，如果匹配目标类型，则尝试用它转化，如果转化失败，则继续遍历，不会因此报错
					for((index, converter) in components.withIndex()) {
						try {
							//如果是可配置的转化器，需要确认参数是否一致，如果不一致，则要新注册转化器
							if(converter is Configurable<*> && converter.configurableInfo.params != params) {
								val newConverter = converter.copy(params) as Converter<*>
								components.add(index, newConverter)
								return (newConverter as Converter<Any?>).convert(value) as T
							} else {
								return (converter as Converter<Any?>).convert(value) as T
							}
						} catch(e: Exception) {
							e.printStackTrace()
						}
					}
					return if(targetType == String::class.java) {
						value.toString() as T?
					} else {
						null
					}
				}
			}
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		inline fun <reified T> convertOrElse(value: Any?, defaultValue: T): T {
			return convertOrElse(value, T::class.java, defaultValue)
		}

		/**
		 * 根据可选的配置参数，将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		@JvmStatic
		fun <T> convertOrElse(value: Any?, targetType: Class<T>, defaultValue: T): T {
			return convertOrNull(value, targetType) ?: defaultValue
		}
	}

	//region Converters
	object ByteConverter : Converter<Byte> {
		//override val targetType: Class<Byte> = Byte::class.javaObjectType

		override fun convert(value: Any): Byte {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.toByte()
				else -> value.toString().toByte()
			}
		}

		override fun convertOrNull(value: Any): Byte? {
			return when {
				value is Byte -> value
				value is Number -> value.toByte()
				value is Char -> value.toByte()
				else -> value.toString().toByteOrNull()
			}
		}
	}

	object ShortConverter : Converter<Short> {
		//override val targetType: Class<Short> = Short::class.javaObjectType

		override fun convert(value: Any): Short {
			return when {
				value is Short -> value
				value is Number -> value.toShort()
				value is Char -> value.toShort()
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

	object IntConverter : Converter<Int> {
		override val targetType: Class<Int> = Int::class.javaObjectType

		override fun convert(value: Any): Int {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.toInt()
				else -> value.toString().toInt()
			}
		}

		override fun convertOrNull(value: Any): Int? {
			return when {
				value is Int -> value
				value is Number -> value.toInt()
				value is Char -> value.toInt()
				else -> value.toString().toIntOrNull()
			}
		}
	}

	object LongConverter : Converter<Long> {
		override val targetType: Class<Long> = Long::class.javaObjectType

		override fun convert(value: Any): Long {
			return when {
				value is Long -> value
				value is Number -> value.toLong()
				value is Char -> value.toLong()
				else -> value.toString().toLong()
			}
		}

		override fun convertOrNull(value: Any): Long? {
			return when {
				value is Long -> value
				value is Number -> value.toLong()
				value is Char -> value.toLong()
				else -> value.toString().toLongOrNull()
			}
		}
	}

	object FloatConverter : Converter<Float> {
		override val targetType: Class<Float> = Float::class.javaObjectType

		override fun convert(value: Any): Float {
			return when {
				value is Float -> value
				value is Number -> value.toFloat()
				value is Char -> value.toFloat()
				else -> value.toString().toFloat()
			}
		}

		override fun convertOrNull(value: Any): Float? {
			return when {
				value is Float -> value
				value is Number -> value.toFloat()
				value is Char -> value.toFloat()
				else -> value.toString().toFloatOrNull()
			}
		}
	}

	object DoubleConverter : Converter<Double> {
		override val targetType: Class<Double> = Double::class.javaObjectType

		override fun convert(value: Any): Double {
			return when {
				value is Double -> value
				value is Number -> value.toDouble()
				value is Char -> value.toDouble()
				else -> value.toString().toDouble()
			}
		}

		override fun convertOrNull(value: Any): Double? {
			return when {
				value is Double -> value
				value is Number -> value.toDouble()
				value is Char -> value.toDouble()
				else -> value.toString().toDoubleOrNull()
			}
		}
	}

	object BigIntegerConverter : Converter<BigInteger> {
		override val targetType: Class<BigInteger> = BigInteger::class.java

		override fun convert(value: Any): BigInteger {
			return when {
				value is BigInteger -> value
				value is Long -> BigInteger.valueOf(value)
				else ->  BigInteger.valueOf(value.convert<Long>())
			}
		}

		override fun convertOrNull(value: Any): BigInteger? {
			return when {
				value is BigInteger -> value
				value is Long -> BigInteger.valueOf(value)
				else -> BigInteger.valueOf(value.convertOrNull<Long>()?:return null)
			}
		}
	}

	object BigDecimalConverter : Converter<BigDecimal> {
		override val targetType: Class<BigDecimal> = BigDecimal::class.java

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
	object UByteConverter: Converter<UByte>{
		override val targetType: Class<UByte> = UByte::class.java

		override fun convert(value: Any): UByte {
			return when{
				value is UByte -> value
				value is Byte -> value.toUByte()
				else -> value.convert<Byte>().toUByte()
			}
		}

		override fun convertOrNull(value: Any): UByte? {
			return when{
				value is UByte -> value
				value is Byte -> value.toUByte()
				else -> value.convertOrNull<Byte>()?.toUByte()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object UShortConverter: Converter<UShort>{
		override val targetType: Class<UShort> = UShort::class.java

		override fun convert(value: Any): UShort {
			return when{
				value is UShort -> value
				value is Short -> value.toUShort()
				else -> value.convert<Short>().toUShort()
			}
		}

		override fun convertOrNull(value: Any): UShort? {
			return when{
				value is UShort -> value
				value is Short -> value.toUShort()
				else -> value.convertOrNull<Short>()?.toUShort()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object UIntConverter: Converter<UInt>{
		override val targetType: Class<UInt> = UInt::class.java

		override fun convert(value: Any): UInt {
			return when{
				value is UInt -> value
				value is Int -> value.toUInt()
				else -> value.convert<Int>().toUInt()
			}
		}

		override fun convertOrNull(value: Any): UInt? {
			return when{
				value is UInt -> value
				value is Int -> value.toUInt()
				else -> value.convertOrNull<Int>()?.toUInt()
			}
		}
	}

	@ExperimentalUnsignedTypes
	object ULongConverter: Converter<ULong>{
		override val targetType: Class<ULong> = ULong::class.java

		override fun convert(value: Any): ULong {
			return when{
				value is ULong -> value
				value is Long -> value.toULong()
				else -> value.convert<Long>().toULong()
			}
		}

		override fun convertOrNull(value: Any): ULong? {
			return when{
				value is ULong -> value
				value is Long -> value.toULong()
				else -> value.convertOrNull<Long>()?.toULong()
			}
		}
	}

	object AtomicIntegerConverter : Converter<AtomicInteger> {
		override val targetType: Class<AtomicInteger> = AtomicInteger::class.java

		override fun convert(value: Any): AtomicInteger {
			return when {
				value is AtomicInteger -> value
				value is Int -> AtomicInteger(value)
				else -> AtomicInteger(value.convert<Int>())
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

	object AtomicLongConverter : Converter<AtomicLong> {
		override val targetType: Class<AtomicLong> = AtomicLong::class.java

		override fun convert(value: Any): AtomicLong {
			return when {
				value is AtomicLong -> value
				value is Long -> AtomicLong(value)
				else -> AtomicLong(value.convert<Long>())
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

	object BooleanConverter : Converter<Boolean> {
		override val targetType: Class<Boolean> = Boolean::class.javaObjectType

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

	object CharConverter : Converter<Char> {
		override val targetType: Class<Char> = Char::class.javaObjectType

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

	@ConfigurableParams(
		ConfigurableParam("raw", "Boolean", "false", comment = "Convert value to string by 'toString()' method"),
		ConfigurableParam("format", "String", "'yyyy-MM-dd'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class StringConverter : Converter<String>, Configurable<StringConverter> {
		companion object Default : StringConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<String> = String::class.java

		var raw = false
		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val threadLocalDateFormat = ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }
		var formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
			protected set

		fun setThreadLocalDateFormat() {
			threadLocalDateFormat.set(SimpleDateFormat(format, locale).also { it.timeZone = timeZone })
		}

		fun setFormatter() {
			formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["raw"]?.convertOrNull<Boolean>()?.let { raw = it }
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setThreadLocalDateFormat()
			setFormatter()
		}

		override fun copy(params: Map<String, Any?>): StringConverter {
			return StringConverter().apply { configure(params) }
		}

		override fun convert(value: Any): String {
			if(raw) return value.toString()
			return when {
				value is Date -> threadLocalDateFormat.get().format(value)
				value is TemporalAccessor -> formatter.format(value)
				else -> value.toString()
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("regexOptions", "Array<RegexOption> | Iterable<RegexOption> | Sequence<RegexOption>", comment = "Regex options")
	)
	open class RegexConverter : Converter<Regex>, Configurable<RegexConverter> {
		companion object Default : RegexConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<Regex> = Regex::class.java

		val regexOptions = mutableSetOf<RegexOption>()

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["regexOptions"]?.let {
				when {
					it is Array<*> -> it.filterIsInstanceTo(regexOptions)
					it is Iterable<*> -> it.filterIsInstanceTo(regexOptions)
					it is Sequence<*> -> it.filterIsInstanceTo(regexOptions)
					else -> {
					}
				}
			}
		}

		override fun copy(params: Map<String, Any?>): RegexConverter {
			return RegexConverter().apply { configure(params) }
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

	object PatternConverter : Converter<Pattern> {
		override val targetType: Class<Pattern> = Pattern::class.java

		override fun convert(value: Any): Pattern {
			return when {
				value is Regex -> value.toPattern()
				value is Pattern -> value
				else -> Pattern.compile(value.toString())
			}
		}
	}

	object CharsetConverter : Converter<Charset> {
		override val targetType: Class<Charset> = Charset::class.java

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

	object ClassConverter : Converter<Class<*>> {
		override val targetType: Class<Class<*>> = Class::class.java

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

	object LocaleConverter : Converter<Locale> {
		override val targetType: Class<Locale> = Locale::class.java

		override fun convert(value: Any): Locale {
			return when {
				value is Locale -> value
				else -> value.toString().toLocale()
			}
		}
	}

	object TimeZoneConverter : Converter<TimeZone> {
		override val targetType: Class<TimeZone> = TimeZone::class.java

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

	object ZoneIdConverter : Converter<ZoneId> {
		override val targetType: Class<ZoneId> = ZoneId::class.java

		override fun convert(value: Any): ZoneId {
			return when {
				value is ZoneId -> value
				value is TimeZone -> value.toZoneId()
				value is TemporalAccessor -> ZoneId.from(value)
				else -> ZoneId.of(value.toString())
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("format", "String", "'yyyy-MM-dd'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class DateConverter : Converter<Date>, Configurable<DateConverter> {
		companion object Default : DateConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<Date> = Date::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val threadLocalDateFormat = ThreadLocal.withInitial { SimpleDateFormat(format, locale).also { it.timeZone = timeZone } }

		fun setThreadLocalDateFormat() {
			threadLocalDateFormat.set(SimpleDateFormat(format, locale).also { it.timeZone = timeZone })
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setThreadLocalDateFormat()
		}

		override fun copy(params: Map<String, Any?>): DateConverter {
			return DateConverter().apply { configure(params) }
		}

		override fun convert(value: Any): Date {
			return when {
				value is Date -> value
				value is Instant -> Date.from(value)
				else -> threadLocalDateFormat.get().parse(value.toString())
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("format", "String", "'yyyy-MM-dd'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class LocalDateConverter : Converter<LocalDate>, Configurable<LocalDateConverter> {
		companion object Default : LocalDateConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<LocalDate> = LocalDate::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
			protected set

		fun setFormatter() {
			formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setFormatter()
		}

		override fun copy(params: Map<String, Any?>): LocalDateConverter {
			return LocalDateConverter().apply { configure(params) }
		}

		override fun convert(value: Any): LocalDate {
			return when {
				value is LocalDate -> value
				value is TemporalAccessor -> LocalDate.from(value)
				value is Date -> LocalDate.from(value.toInstant())
				else -> LocalDate.parse(value.toString())
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("format", "String", "'HH:mm:ss'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class LocalTimeConverter : Converter<LocalTime>, Configurable<LocalTimeConverter> {
		companion object Default : LocalTimeConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<LocalTime> = LocalTime::class.java

		var format = defaultTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
			protected set

		fun setFormatter() {
			formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setFormatter()
		}

		override fun copy(params: Map<String, Any?>): LocalTimeConverter {
			return LocalTimeConverter().apply { configure(params) }
		}

		override fun convert(value: Any): LocalTime {
			return when {
				value is LocalTime -> value
				value is TemporalAccessor -> LocalTime.from(value)
				value is Date -> LocalTime.from(value.toInstant())
				else -> LocalTime.parse(value.toString())
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("format", "String", "'yyyy-MM-dd HH:mm:ss'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class LocalDateTimeConverter : Converter<LocalDateTime>, Configurable<LocalDateTimeConverter> {
		companion object Default : LocalDateTimeConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<LocalDateTime> = LocalDateTime::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
			protected set

		fun setFormatter() {
			formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setFormatter()
		}

		override fun copy(params: Map<String, Any?>): LocalDateTimeConverter {
			return LocalDateTimeConverter().apply { configure(params) }
		}

		override fun convert(value: Any): LocalDateTime {
			return when {
				value is LocalDateTime -> value
				value is TemporalAccessor -> LocalDateTime.from(value)
				value is Date -> LocalDateTime.from(value.toInstant())
				else -> LocalDateTime.parse(value.toString())
			}
		}
	}

	object InstantConverter : Converter<Instant> {
		override val targetType: Class<Instant> = Instant::class.java

		override fun convert(value: Any): Instant {
			return when {
				value is Instant -> value
				value is TemporalAccessor -> Instant.from(value)
				else -> Instant.parse(value.toString())
			}
		}
	}

	object DurationConverter : Converter<Duration> {
		override val targetType: Class<Duration> = Duration::class.java

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

	@ConfigurableParams(
		ConfigurableParam("format", "String", "'yyyy-MM-dd'", comment = "Format pattern"),
		ConfigurableParam("locale", "String | Locale", "<default>", comment = "Format locale"),
		ConfigurableParam("timeZone", "String | TimeZone", "<utc>", comment = "Format time zone")
	)
	open class PeriodConverter : Converter<Period>, Configurable<PeriodConverter> {
		companion object Default : PeriodConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<Period> = Period::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
			protected set

		fun setFormatter() {
			formatter = DateTimeFormatter.ofPattern(format, locale).withZone(timeZone.toZoneId())
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["format"]?.convertOrNull<String>()?.let { format = it }
			params["locale"]?.convertOrNull<Locale>()?.let { locale = it }
			params["timeZone"]?.convertOrNull<TimeZone>()?.let { timeZone = it }
			setFormatter()
		}

		override fun copy(params: Map<String, Any?>): PeriodConverter {
			return PeriodConverter().apply { configure(params) }
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
				else -> Period.parse(value.toString())
			}
		}
	}

	object FileConverter : Converter<File> {
		override val targetType: Class<File> = File::class.java

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

	object PathConverter : Converter<Path> {
		override val targetType: Class<Path> = Path::class.java

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

	object UrlConverter : Converter<URL> {
		override val targetType: Class<URL> = URL::class.java

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

	object UriConverter : Converter<URI> {
		override val targetType: Class<URI> = URI::class.java

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

	@ConfigurableParams(
		ConfigurableParam("className", "String", comment = "Full class name of enum")
	)
	open class EnumConverter : Converter<Enum<*>>, Configurable<EnumConverter> {
		companion object Default : EnumConverter()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<Enum<*>> = Enum::class.java

		var className: String? = null
			protected set
		var enumClass: Class<*>? = null
			protected set
		val enumValues :MutableList<Enum<*>> = mutableListOf()
		val enumValueMap: MutableMap<String, Enum<*>> = mutableMapOf()

		fun setEnumClassAndValues() {
			if(className == null) throw IllegalArgumentException("Param 'className' must not be null.")
			val enumClass = className?.toClassOrNull()
			if(enumClass == null ||!enumClass.isEnum) throw IllegalArgumentException("Param 'className' does not represent an enum class.")
			this.enumClass = enumClass
			val enumConstants = enumClass.enumConstants
			for(enumConstant in enumConstants) {
				enumConstant as Enum<*>
				enumValues += enumConstant
				enumValueMap[enumConstant.name.toLowerCase()] = enumConstant
			}
		}

		override fun configure(params: Map<String, Any?>) {
			super.configure(params)
			params["className"]?.toString()?.let { className = it }
			setEnumClassAndValues()
		}

		override fun copy(params: Map<String, Any?>): EnumConverter {
			return EnumConverter().apply { configure(params) }
		}

		override fun convert(value: Any): Enum<*> {
			if(className == null || enumClass == null){
				throw IllegalArgumentException("Cannot get enum class. Param 'className' is not valid.")
			}
			return when{
				value is Number -> {
					val index = value.toInt()
					enumValues.getOrNull(index) ?: throw IllegalArgumentException("Cannot find enum constant by index '$index'.")
				}
				else -> {
					val name = value.toString().toLowerCase()
					enumValueMap[name]?: throw IllegalArgumentException("Cannot find enum constant by name '$name'.")
				}
			}
		}

		override fun convertOrNull(value: Any): Enum<*>? {
			if(className == null || enumClass == null){
				throw IllegalArgumentException("Cannot get enum class. Param 'className' is not valid.")
			}
			return when{
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


	//open class StringArrayConverter : Converter<Array<String>>, Configurable<StringArrayConverter> {
	//	companion object Default : StringArrayConverter()
	//
	//	override val configurableInfo: ConfigurableInfo = ConfigurableInfo()
	//
	//	override val targetType: Class<Array<String>> = Array<String>::class.java
	//
	//	override fun configure(params: Map<String, Any?>) {
	//		super.configure(params)
	//	}
	//
	//	override fun copy(params: Map<String, Any?>): StringArrayConverter {
	//		TODO("Not yet implemented")
	//	}
	//
	//	override fun convert(value: Any): Array<String> {
	//		TODO("Not yet implemented")
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
	//	override fun configure(params: Map<String, Any?>) {
	//		super.configure(params)
	//	}
	//
	//	override fun copy(params: Map<String, Any?>): StringListConverter {
	//		TODO("Not yet implemented")
	//	}
	//
	//	override fun convert(value: Any): List<String> {
	//		TODO("Not yet implemented")
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
	//	override fun configure(params: Map<String, Any?>) {
	//		super.configure(params)
	//	}
	//
	//	override fun copy(params: Map<String, Any?>): StringSetConverter {
	//		TODO("Not yet implemented")
	//	}
	//
	//	override fun convert(value: Any): Set<String> {
	//		TODO("Not yet implemented")
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
	//	override fun configure(params: Map<String, Any?>) {
	//		super.configure(params)
	//	}
	//
	//	override fun copy(params: Map<String, Any?>): StringSequenceConverter {
	//		TODO("Not yet implemented")
	//	}
	//
	//	override fun convert(value: Any): Sequence<String> {
	//		TODO("Not yet implemented")
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
	//	override fun configure(params: Map<String, Any?>) {
	//		super.configure(params)
	//	}
	//
	//	override fun copy(params: Map<String, Any?>): StringMapConverter {
	//		TODO("Not yet implemented")
	//	}
	//
	//	override fun convert(value: Any): Map<String, String> {
	//		TODO("Not yet implemented")
	//	}
	//}
	//endregion
}
