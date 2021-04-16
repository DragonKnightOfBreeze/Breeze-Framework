// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.extension.*
import java.math.*
import java.nio.charset.*
import java.text.*
import java.time.*
import java.time.format.*
import java.time.temporal.*
import java.util.*
import java.util.concurrent.atomic.*

/**
 * 类型转化器。
 *
 * 类型转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 */
interface TypeConverter<T> : ConfigurableComponent {
	/**
	 * 目标类型。
	 */
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

	/**
	 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
	 */
	fun convertOrElse(value: Any, defaultValue: T): T {
		return convertOrNull(value) ?: defaultValue
	}

	/**
	 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
	 */
	fun convertOrDefault(value: Any, defaultValue: () -> T): T {
		return convertOrNull(value) ?: defaultValue()
	}

	companion object Registry : AbstractComponentRegistry<TypeConverter<*>>() {
		init {
			registerDefaultConverters()
		}

		private fun registerDefaultConverters() {

		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convert(value: Any?): T {
			return convert(value, T::class.java)
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> convert(value: Any?, targetType: Class<T>): T {
			return try {
				when {
					//value为null时，如果可以，转化为字符串，否则尝试转化为T，可能会出错
					value == null -> (if(targetType == String::class.java) "null" else null) as T
					//如果value的类型兼容targetType，则直接返回
					targetType.isInstance(value) -> value as T
					else -> {
						for(converter in values()) {
							if(converter.targetType.isAssignableFrom(targetType)) {
								return (converter as TypeConverter<Any?>).convert(value) as T
							}
						}
						//如果没有匹配的转化器，先判断targetType是否是String，如果是，则直接调用toString()
						if(targetType == String::class.java) {
							value.toString() as T
						} else {
							throw IllegalArgumentException("No suitable converter found for target type '$targetType'.")
						}
					}
				}
			} catch(e: Exception) {
				throw IllegalArgumentException("Cannot convert '${value}' to type '${targetType}'.", e)
			}
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convertOrNull(value: Any?): T? {
			return convertOrNull(value, T::class.java)
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> convertOrNull(value: Any?, targetType: Class<T>): T? {
			return when {
				//value为null时，如果可以，转化为字符串，否则尝试转化为T，可能会出错
				value == null -> (if(targetType == String::class.java) "null" else null) as T?
				//如果value的类型兼容targetType，则直接返回
				targetType.isInstance(value) -> value as T?
				else -> {
					for(converter in values()) {
						if(converter.targetType.isAssignableFrom(targetType)) {
							return (converter as TypeConverter<Any?>).convert(value) as T
						}
					}
					//如果没有匹配的转化器，先判断targetType是否是String，如果是，则直接调用toString()
					if(targetType == String::class.java) {
						value.toString() as T?
					} else {
						null
					}
				}
			}
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		inline fun <reified T> convertOrElse(value: Any?, defaultValue: T): T {
			return convertOrElse(value, T::class.java, defaultValue)
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		fun <T> convertOrElse(value: Any?, targetType: Class<T>, defaultValue: T): T {
			return convertOrNull(value, targetType) ?: defaultValue
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		inline fun <reified T> convertOrDefault(value: Any?, noinline defaultValue: () -> T): T {
			return convertOrDefault(value, T::class.java, defaultValue)
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		fun <T> convertOrDefault(value: Any?, targetType: Class<T>, defaultValue: () -> T): T {
			return convertOrNull(value, targetType) ?: defaultValue()
		}
	}

	//region Default Converters
	class ByteConverter : TypeConverter<Byte> {
		override val targetType: Class<Byte> = Byte::class.javaObjectType

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

	class ShortConverter : TypeConverter<Short> {
		override val targetType: Class<Short> = Short::class.javaObjectType

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

	class IntConverter : TypeConverter<Int> {
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

	class LongConverter : TypeConverter<Long> {
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

	class FloatConverter : TypeConverter<Float> {
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

	class DoubleConverter : TypeConverter<Double> {
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

	class BigIntegerConverter : TypeConverter<BigInteger> {
		override val targetType: Class<BigInteger> = BigInteger::class.java

		override fun convert(value: Any): BigInteger {
			return when {
				value is BigInteger -> value
				value is Long -> value.let { BigInteger.valueOf(it) }
				value is Number -> value.toLong().let { BigInteger.valueOf(it) }
				else -> value.toString().toLong().let { BigInteger.valueOf(it) }
			}
		}

		override fun convertOrNull(value: Any): BigInteger? {
			return when {
				value is BigInteger -> value
				value is Long -> value.let { BigInteger.valueOf(it) }
				value is Number -> value.toLong().let { BigInteger.valueOf(it) }
				else -> value.toString().toLongOrNull()?.let { BigInteger.valueOf(it) }
			}
		}
	}

	class BigDecimalConverter:TypeConverter<BigDecimal>{
		override val targetType: Class<BigDecimal> = BigDecimal::class.java

		override fun convert(value: Any): BigDecimal {
			return when{
				value is BigDecimal -> value
				value is Double -> value.let { BigDecimal.valueOf(it) }
				value is Number -> value.toDouble().let { BigDecimal.valueOf(it) }
				else -> value.toString().toDouble().let { BigDecimal.valueOf(it) }
			}
		}

		override fun convertOrNull(value: Any): BigDecimal? {
			return when{
				value is BigDecimal -> value
				value is Double -> value.let { BigDecimal.valueOf(it) }
				value is Number -> value.toDouble().let { BigDecimal.valueOf(it) }
				else -> value.toString().toDoubleOrNull()?.let { BigDecimal.valueOf(it) }
			}
		}
	}

	class AtomicIntegerConverter:TypeConverter<AtomicInteger>{
		override val targetType: Class<AtomicInteger> = AtomicInteger::class.java

		override fun convert(value: Any): AtomicInteger {
			return when{
				value is AtomicInteger -> value
				value is Int -> value.let{ AtomicInteger(it) }
				value is Number -> value.toInt().let { AtomicInteger(it) }
				else -> value.toString().toInt().let { AtomicInteger(it) }
			}
		}

		override fun convertOrNull(value: Any): AtomicInteger? {
			return when{
				value is AtomicInteger -> value
				value is Int -> value.let{ AtomicInteger(it) }
				value is Number -> value.toInt().let { AtomicInteger(it) }
				else -> value.toString().toIntOrNull()?.let { AtomicInteger(it) }
			}
		}
	}

	class AtomicLongConverter:TypeConverter<AtomicLong>{
		override val targetType: Class<AtomicLong> = AtomicLong::class.java

		override fun convert(value: Any): AtomicLong {
			return when{
				value is AtomicLong -> value
				value is Long -> value.let{ AtomicLong(it) }
				value is Number -> value.toLong().let { AtomicLong(it) }
				else -> value.toString().toLong().let { AtomicLong(it) }
			}
		}

		override fun convertOrNull(value: Any): AtomicLong? {
			return when{
				value is AtomicLong -> value
				value is Long -> value.let{ AtomicLong(it) }
				value is Number -> value.toLong().let { AtomicLong(it) }
				else -> value.toString().toLongOrNull()?.let { AtomicLong(it) }
			}
		}
	}

	class BooleanConverter:TypeConverter<Boolean>{
		override val targetType: Class<Boolean> = Boolean::class.javaObjectType

		override fun convert(value: Any): Boolean {
			return when{
				value is Boolean -> value
				value is Number -> value.toString().let{ it!="0" || it!="0.0" }
				value is CharSequence -> value.isNotEmpty()
				value is Array<*> -> value.isNotEmpty()
				value is Collection<*> -> value.isNotEmpty()
				value is Iterable<*> -> value.any()
				value is Sequence<*> -> value.any()
				value is Map<*,*> -> value.isNotEmpty()
				else -> false
			}
		}
	}

	class CharConverter:TypeConverter<Char>{
		override val targetType: Class<Char> = Char::class.javaObjectType

		override fun convert(value: Any): Char {
			return when{
				value is Char -> value
				value is Number -> value.toChar()
				value is CharSequence -> value.single()
				else -> throw IllegalArgumentException("Cannot convert '$value' to Char.")
			}
		}

		override fun convertOrNull(value: Any): Char? {
			return when{
				value is Char -> value
				value is Number -> value.toChar()
				value is CharSequence -> value.singleOrNull()
				else -> null
			}
		}
	}

	class StringConverter:TypeConverter<String>{
		override val targetType: Class<String> = String::class.java

		override fun convert(value: Any): String {
			return value.toString()
		}
	}

	class CharsetConverter:TypeConverter<Charset>{
		override val targetType: Class<Charset> = Charset::class.java

		override fun convert(value: Any): Charset {
			return when{
				value is Charset -> value
				else -> value.toString().toCharset()
			}
		}

		override fun convertOrNull(value: Any): Charset? {
			return when{
				value is Charset -> value
				else -> value.toString().toCharsetOrNull()
			}
		}
	}

	class ClassConverter:TypeConverter<Class<*>>{
		override val targetType: Class<Class<*>> = Class::class.java

		override fun convert(value: Any): Class<*>{
			return when{
				value is Class<*> -> value
				else -> value.toString().toClass()
			}
		}

		override fun convertOrNull(value: Any): Class<*>? {
			return when{
				value is Class<*> -> value
				else -> value.toString().toClassOrNull()
			}
		}
	}

	class LocaleConverter:TypeConverter<Locale>{
		override val targetType: Class<Locale> = Locale::class.java

		override fun convert(value: Any): Locale {
			return when{
				value is Locale -> value
				else -> value.toString().toLocale()
			}
		}
	}

	class TimeZoneConverter:TypeConverter<TimeZone>{
		override val targetType: Class<TimeZone> = TimeZone::class.java

		override fun convert(value: Any): TimeZone {
			return when{
				value is TimeZone -> value
				value is ZoneId -> TimeZone.getTimeZone(value)
				else -> value.toString().toTimeZone()
			}
		}

		override fun convertOrNull(value: Any): TimeZone? {
			return when{
				value is TimeZone -> value
				value is ZoneId -> TimeZone.getTimeZone(value)
				else -> value.toString().toTimeZoneOrNull()
			}
		}
	}

	class ZoneIdConverter:TypeConverter<ZoneId>{
		override val targetType: Class<ZoneId> = ZoneId::class.java

		override fun convert(value: Any): ZoneId {
			return when{
				value is ZoneId -> value
				value is TimeZone -> value.toZoneId()
				value is TemporalAccessor -> ZoneId.from(value)
				else -> ZoneId.of(value.toString())
			}
		}
	}

	class DateConverter:TypeConverter<Date>{
		override val targetType: Class<Date> = Date::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val threadLocalDateFormat by lazy {
			ThreadLocal.withInitial { SimpleDateFormat(format, locale).also{ it.timeZone = timeZone } }
		}

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let { locale=it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
		}

		override fun convert(value: Any): Date {
			return when{
				value is Date -> value
				value is Instant -> Date.from(value)
				else -> threadLocalDateFormat.get().parse(value.toString())
			}
		}
	}

	class LocalDateConverter:TypeConverter<LocalDate>{
		override val targetType: Class<LocalDate> = LocalDate::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val formatter by lazy { DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId()) }

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let { locale=it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
		}

		override fun convert(value: Any): LocalDate {
			return when{
				value is LocalDate -> value
				value is TemporalAccessor -> LocalDate.from(value)
				value is Date ->  LocalDate.from(value.toInstant())
				else -> LocalDate.parse(value.toString())
			}
		}
	}

	class LocalTimeConverter:TypeConverter<LocalTime>{
		override val targetType: Class<LocalTime> = LocalTime::class.java

		var format = defaultTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val formatter by lazy { DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId()) }

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let { locale=it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
		}

		override fun convert(value: Any): LocalTime {
			return when{
				value is LocalTime-> value
				value is TemporalAccessor -> LocalTime.from(value)
				value is Date ->  LocalTime.from(value.toInstant())
				else -> LocalTime.parse(value.toString())
			}
		}
	}

	class LocalDateTimeConverter:TypeConverter<LocalDateTime>{
		override val targetType: Class<LocalDateTime> = LocalDateTime::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val formatter by lazy { DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId()) }

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let { locale=it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
		}

		override fun convert(value: Any): LocalDateTime {
			return when{
				value is LocalDateTime-> value
				value is TemporalAccessor -> LocalDateTime.from(value)
				value is Date ->  LocalDateTime.from(value.toInstant())
				else -> LocalDateTime.parse(value.toString())
			}
		}
	}
	//endregion
}
