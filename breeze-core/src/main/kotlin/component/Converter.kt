// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.extension.*
import java.io.*
import java.net.*
import java.nio.charset.*
import java.nio.file.*
import java.text.*
import java.time.*
import java.time.format.*
import java.util.*

/**
 * 转化器。
 *
 * 转化器用于根据一般规则，将指定对象从一个类型转化到另一个类型。
 */
@Deprecated
interface Converter<S,T> :ConfigurableComponent{
	/**
	 * 源类型。
	 */
	val sourceType:Class<S>

	/**
	 * 目标类型。
	 */
	val targetType:Class<T>

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
	 */
	fun convert(value: S): T

	/**
	 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
	 */
	fun convertOrNull(value: S): T? {
		return runCatching { convert(value) }.getOrNull()
	}

	/**
	 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
	 */
	fun convertOrElse(value:S,defaultValue:T):T{
		return convertOrNull(value)?:defaultValue
	}

	/**
	 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
	 */
	fun convertOrDefault(value:S,defaultValue:()->T):T{
		return convertOrNull(value)?:defaultValue()
	}

	companion object Registry:AbstractComponentRegistry<Converter<*,*>>(){
		init{
			registerStringConverters()
			registerNumberAndPrimitiveConverters()
			registerIoConverters()
			registerTimeConverters()
		}

		private fun registerStringConverters(){
			register(StringToIntConverter())
			register(StringToLongConverter())
			register(StringToFloatConverter())
			register(StringToDoubleConverter())
			register(StringToByteConverter())
			register(StringToShortConverter())
			register(StringToCharConverter())
			register(StringToBooleanConverter())
			register(StringToRegexConverter())
			register(StringToFileConverter())
			register(StringToPathConverter())
			register(StringToUrlConverter())
			register(StringToUriConverter())
			register(StringToCharsetConverter())
			register(StringToTimeZoneConverter())
			register(StringToLocaleConverter())
			register(StringToClassConverter())
			register(StringToDateConverter())
			register(StringToLocalDateConverter())
			register(StringToLocalTimeConverter())
			register(StringToLocalDateTimeConverter())
			register(DateToStringConverter())
			register(LocalDateToStringConverter())
		}

		private fun registerNumberAndPrimitiveConverters(){
			register(IntToLongConverter())
			register(IntToFloatConverter())
			register(IntToDoubleConverter())
			register(IntToByteConverter())
			register(IntToShortConverter())
			register(IntToCharConverter())
			register(LongToIntConverter())
			register(LongToFloatConverter())
			register(LongToDoubleConverter())
			register(LongToByteConverter())
			register(LongToShortConverter())
			register(LongToCharConverter())
			register(FloatToIntConverter())
			register(FloatToLongConverter())
			register(FloatToDoubleConverter())
			register(FloatToByteConverter())
			register(FloatToShortConverter())
			register(FloatToCharConverter())
			register(DoubleToIntConverter())
			register(DoubleToLongConverter())
			register(DoubleToFloatConverter())
			register(DoubleToByteConverter())
			register(DoubleToShortConverter())
			register(DoubleToCharConverter())
			register(ByteToIntConverter())
			register(ByteToLongConverter())
			register(ByteToFloatConverter())
			register(ByteToDoubleConverter())
			register(ByteToShortConverter())
			register(ByteToCharConverter())
			register(ShortToIntConverter())
			register(ShortToLongConverter())
			register(ShortToFloatConverter())
			register(ShortToDoubleConverter())
			register(ShortToByteConverter())
			register(ShortToCharConverter())
			register(CharToIntConverter())
			register(CharToLongConverter())
			register(CharToFloatConverter())
			register(CharToDoubleConverter())
			register(CharToByteConverter())
			register(CharToShortConverter())
		}

		private fun registerIoConverters(){
			register(FileToPathConverter())
			register(FileToUriConverter())
			register(FileToUrlConverter())
			register(PathToFileConverter())
			register(PathToUriConverter())
			register(PathToUrlConverter())
			register(UriToFileConverter())
			register(UriToPathConverter())
			register(UriToUrlConverter())
			register(UrlToFileConverter())
			register(UrlToPathConverter())
			register(UrlToUriConverter())
		}

		private fun registerTimeConverters(){
			register(DateToInstantConverter())
			register(InstantToDateConverter())
			register(LocalDateTimeToLocalDateConverter())
			register(LocalDateTimeToLocalTimeConverter())
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		inline fun <reified T> convert(value:Any?):T{
			return convert(value,T::class.java)
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则抛出异常。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> convert(value:Any?,targetType:Class<T>):T{
			return try {
				when {
					//value为null时，如果可以，转化为字符串，否则尝试转化为T，可能会出错
					value == null -> (if(targetType == String::class.java) "null" else null) as T
					targetType.isAssignableFrom(value.javaClass) -> value as T
					else -> {
						for(converter in values()) {
							val s = converter.sourceType
							val t = converter.targetType
							if(t.isAssignableFrom(targetType) && s.isAssignableFrom(value.javaClass)) {
								return (converter as Converter<Any?, Any?>).convert(value) as T
							}
						}
						if(targetType == String::class.java){
							value.toString() as T
						}else {
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
		inline fun <reified T> convertOrNull(value:Any?):T?{
			return convertOrNull(value,T::class.java)
		}

		/**
		 * 将指定的对象转化为另一个类型。如果转化失败，则返回null。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> convertOrNull(value:Any?,targetType:Class<T>):T?{
			return when {
				value == null -> (if(targetType == String::class.java) "null" else null) as T?
				targetType.isAssignableFrom(value.javaClass) -> value as T?
				else -> {
					for(converter in values()) {
						val s = converter.sourceType
						val t = converter.targetType
						if(t.isAssignableFrom(targetType) && s.isAssignableFrom(value.javaClass)) {
							return (converter as Converter<Any?, Any?>).convertOrNull(value) as T?
						}
					}
					if(targetType == String::class.java){
						value.toString() as T?
					}else{
						null
					}
				}
			}
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		inline fun <reified T> convertOrElse(value:Any?,defaultValue:T):T{
			return convertOrElse(value,T::class.java,defaultValue)
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		fun <T> convertOrElse(value:Any?,targetType:Class<T>,defaultValue:T):T{
			return convertOrNull(value,targetType)?:defaultValue
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		inline fun <reified T> convertOrDefault(value:Any?, noinline defaultValue:()->T):T{
			return convertOrDefault(value,T::class.java,defaultValue)
		}

		/**
		 * 将指定的对象转化为另一个类型，如果转化失败，则返回默认值。
		 */
		fun <T> convertOrDefault(value:Any?,targetType:Class<T>,defaultValue:()->T):T{
			return convertOrNull(value,targetType)?:defaultValue()
		}
	}

	//region String Converters
	class StringToIntConverter : Converter<String, Int> {
		override val sourceType = String::class.java
		override val targetType = Int::class.javaObjectType
		override fun convert(value: String) = value.toInt()
		override fun convertOrNull(value: String) = value.toIntOrNull()
	}

	class StringToLongConverter : Converter<String, Long> {
		override val sourceType = String::class.java
		override val targetType = Long::class.javaObjectType
		override fun convert(value: String) = value.toLong()
		override fun convertOrNull(value: String) = value.toLongOrNull()
	}

	class StringToFloatConverter : Converter<String, Float> {
		override val sourceType = String::class.java
		override val targetType = Float::class.javaObjectType
		override fun convert(value: String) = value.toFloat()
		override fun convertOrNull(value: String) = value.toFloatOrNull()
	}

	class StringToDoubleConverter : Converter<String, Double> {
		override val sourceType = String::class.java
		override val targetType = Double::class.javaObjectType
		override fun convert(value: String) = value.toDouble()
		override fun convertOrNull(value: String) = value.toDoubleOrNull()
	}

	class StringToByteConverter : Converter<String, Byte> {
		override val sourceType = String::class.java
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: String) = value.toByte()
		override fun convertOrNull(value: String) = value.toByteOrNull()
	}

	class StringToShortConverter : Converter<String, Short> {
		override val sourceType = String::class.java
		override val targetType = Short::class.javaObjectType
		override fun convert(value: String) = value.toShort()
		override fun convertOrNull(value: String) = value.toShortOrNull()
	}

	class StringToCharConverter : Converter<String, Char> {
		override val sourceType = String::class.java
		override val targetType = Char::class.javaObjectType
		override fun convert(value: String) = value.toChar()
		override fun convertOrNull(value: String) = value.toCharOrNull()
	}

	class StringToBooleanConverter : Converter<String, Boolean> {
		override val sourceType = String::class.java
		override val targetType = Boolean::class.javaObjectType
		override fun convert(value: String) = value.toBoolean()
		override fun convertOrNull(value: String) = value.toBooleanOrNull()
	}

	class StringToRegexConverter : Converter<String, Regex> {
		override val sourceType = String::class.java
		override val targetType = Regex::class.java
		override fun convert(value: String) = value.toRegex()
	}

	class StringToFileConverter : Converter<String, File> {
		override val sourceType = String::class.java
		override val targetType = File::class.java
		override fun convert(value: String) = value.toFile()
	}

	class StringToPathConverter : Converter<String, Path> {
		override val sourceType = String::class.java
		override val targetType = Path::class.java
		override fun convert(value: String) = value.toPath()
	}

	class StringToUrlConverter : Converter<String, URL> {
		override val sourceType = String::class.java
		override val targetType = URL::class.java
		override fun convert(value: String) = value.toUrl()
	}

	class StringToUriConverter : Converter<String, URI> {
		override val sourceType = String::class.java
		override val targetType = URI::class.java
		override fun convert(value: String) = value.toUri()
	}

	class StringToCharsetConverter : Converter<String, Charset> {
		override val sourceType = String::class.java
		override val targetType = Charset::class.java
		override fun convert(value: String) = value.toCharset()
		override fun convertOrNull(value: String) = value.toCharsetOrNull()
	}

	class StringToTimeZoneConverter : Converter<String, TimeZone> {
		override val sourceType = String::class.java
		override val targetType = TimeZone::class.java
		override fun convert(value: String) = value.toTimeZone()
		override fun convertOrNull(value: String) = value.toTimeZoneOrNull()
	}

	class StringToLocaleConverter:Converter<String,Locale>{
		override val sourceType = String::class.java
		override val targetType = Locale::class.java
		override fun convert(value: String) = value.toLocale()
	}

	class StringToClassConverter : Converter<String, Class<*>> {
		override val sourceType = String::class.java
		override val targetType = Class::class.java
		override fun convert(value: String) = value.toClass()
		override fun convertOrNull(value: String) = value.toClassOrNull()
	}

	class StringToDateConverter:Converter<String,Date>{
		override val sourceType = String::class.java
		override val targetType = Date::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val threadLocalDateFormat = ThreadLocal.withInitial{
			SimpleDateFormat(format,locale).apply{ this.timeZone = timeZone }
		}

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			threadLocalDateFormat.set(SimpleDateFormat(format,locale).apply{ this.timeZone = timeZone } )
		}

		override fun convert(value: String): Date {
			return threadLocalDateFormat.get().parse(value)
		}
	}

	class DateToStringConverter : Converter<Date, String> {
		override val sourceType = Date::class.java
		override val targetType = String::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		val threadLocalDateFormat = ThreadLocal.withInitial{
			SimpleDateFormat(format,locale).apply{ this.timeZone = timeZone }
		}

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			threadLocalDateFormat.set(SimpleDateFormat(format,locale).apply{ this.timeZone = timeZone } )
		}

		override fun convert(value: Date): String {
			return threadLocalDateFormat.get().format(value)
		}
	}

	class StringToLocalDateConverter:Converter<String, LocalDate>{
		override val sourceType = String::class.java
		override val targetType = LocalDate::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalDate {
			return LocalDate.parse(value,formatter)
		}
	}

	class LocalDateToStringConverter : Converter<LocalDate, String> {
		override val sourceType = LocalDate::class.java
		override val targetType = String::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: LocalDate): String {
			return formatter.format(value)
		}
	}

	class StringToLocalTimeConverter:Converter<String, LocalTime>{
		override val sourceType = String::class.java
		override val targetType = LocalTime::class.java

		var format = defaultTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalTime {
			return LocalTime.parse(value,formatter)
		}
	}

	class LocalTimeToStringConverter : Converter<LocalDate, String> {
		override val sourceType = LocalDate::class.java
		override val targetType = String::class.java

		var format = defaultTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: LocalDate): String {
			return formatter.format(value)
		}
	}

	class StringToLocalDateTimeConverter:Converter<String, LocalDateTime>{
		override val sourceType = String::class.java
		override val targetType = LocalDateTime::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalDateTime {
			return LocalDateTime.parse(value,formatter)
		}
	}

	class LocalDateTimeToStringConverter : Converter<LocalDate, String> {
		override val sourceType = LocalDate::class.java
		override val targetType = String::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ format = it.toString() }
			params["locale"]?.let{ locale = it.toString().toLocale() }
			params["timeZone"]?.let{ timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: LocalDate): String {
			return formatter.format(value)
		}
	}
	//endregion

	//region Number And Primitive Converters
	class IntToLongConverter : Converter<Int, Long> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Int) = value.toLong()
	}

	class IntToFloatConverter : Converter<Int, Float> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Int) = value.toFloat()
	}

	class IntToDoubleConverter : Converter<Int, Double> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Int) = value.toDouble()
	}

	class IntToByteConverter : Converter<Int, Byte> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Int) = value.toByte()
	}

	class IntToShortConverter : Converter<Int, Short> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Int) = value.toShort()
	}

	class IntToCharConverter : Converter<Int, Char> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Int) = value.toChar()
	}

	class LongToIntConverter : Converter<Long, Int> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Long) = value.toInt()
	}

	class LongToFloatConverter : Converter<Long, Float> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Long) = value.toFloat()
	}

	class LongToDoubleConverter : Converter<Long, Double> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Long) = value.toDouble()
	}

	class LongToByteConverter : Converter<Long, Byte> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Long) = value.toByte()
	}

	class LongToShortConverter : Converter<Long, Short> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Long) = value.toShort()
	}

	class LongToCharConverter : Converter<Long, Char> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Long) = value.toChar()
	}

	class FloatToIntConverter : Converter<Float, Int> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Float) = value.toInt()
	}

	class FloatToLongConverter : Converter<Float, Long> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Float) = value.toLong()
	}

	class FloatToDoubleConverter : Converter<Float, Double> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Float) = value.toDouble()
	}

	class FloatToByteConverter : Converter<Float, Byte> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toByte()
	}

	class FloatToShortConverter : Converter<Float, Short> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toShort()
	}

	class FloatToCharConverter : Converter<Float, Char> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Float) = value.toChar()
	}

	class DoubleToIntConverter : Converter<Double, Int> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Double) = value.toInt()
	}

	class DoubleToLongConverter : Converter<Double, Long> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Double) = value.toLong()
	}

	class DoubleToFloatConverter : Converter<Double, Float> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Double) = value.toFloat()
	}

	class DoubleToByteConverter : Converter<Double, Byte> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toByte()
	}

	class DoubleToShortConverter : Converter<Double, Short> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toShort()
	}

	class DoubleToCharConverter : Converter<Double, Char> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Double) = value.toChar()
	}

	class ByteToIntConverter : Converter<Byte, Int> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Byte) = value.toInt()
	}

	class ByteToLongConverter : Converter<Byte, Long> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Byte) = value.toLong()
	}

	class ByteToFloatConverter : Converter<Byte, Float> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Byte) = value.toFloat()
	}

	class ByteToDoubleConverter : Converter<Byte, Double> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Byte) = value.toDouble()
	}

	class ByteToShortConverter : Converter<Byte, Short> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Byte) = value.toShort()
	}

	class ByteToCharConverter : Converter<Byte, Char> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Byte) = value.toChar()
	}

	class ShortToIntConverter : Converter<Short, Int> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Short) = value.toInt()
	}

	class ShortToLongConverter : Converter<Short, Long> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Short) = value.toLong()
	}

	class ShortToFloatConverter : Converter<Short, Float> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Short) = value.toFloat()
	}

	class ShortToDoubleConverter : Converter<Short, Double> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Short) = value.toDouble()
	}

	class ShortToByteConverter : Converter<Short, Byte> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Short) = value.toByte()
	}

	class ShortToCharConverter : Converter<Short, Char> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Short) = value.toChar()
	}

	class CharToIntConverter : Converter<Char, Int> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Char) = value.toInt()
	}

	class CharToLongConverter : Converter<Char, Long> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Char) = value.toLong()
	}

	class CharToFloatConverter : Converter<Char, Float> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Char) = value.toFloat()
	}

	class CharToDoubleConverter : Converter<Char, Double> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Char) = value.toDouble()
	}

	class CharToByteConverter : Converter<Char, Byte> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Char) = value.toByte()
	}

	class CharToShortConverter : Converter<Char, Short> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Char) = value.toShort()
	}
	//endregion

	//region IO Converters
	class FileToPathConverter : Converter<File, Path> {
		override val sourceType = File::class.java
		override val targetType = Path::class.java
		override fun convert(value: File) = value.toPath()
	}

	class FileToUriConverter : Converter<File, URI> {
		override val sourceType = File::class.java
		override val targetType = URI::class.java
		override fun convert(value: File) = value.toUri()
	}

	class FileToUrlConverter : Converter<File, URL> {
		override val sourceType = File::class.java
		override val targetType = URL::class.java
		override fun convert(value: File) = value.toUrl()
	}

	class PathToFileConverter : Converter<Path, File> {
		override val sourceType = Path::class.java
		override val targetType = File::class.java
		override fun convert(value: Path) = value.toFile()
	}

	class PathToUriConverter : Converter<Path, URI> {
		override val sourceType = Path::class.java
		override val targetType = URI::class.java
		override fun convert(value: Path) = value.toUri()
	}

	class PathToUrlConverter : Converter<Path, URL> {
		override val sourceType = Path::class.java
		override val targetType = URL::class.java
		override fun convert(value: Path) = value.toUrl()
	}

	class UriToFileConverter : Converter<URI, File> {
		override val sourceType = URI::class.java
		override val targetType = File::class.java
		override fun convert(value: URI) = value.toFile()
	}

	class UriToPathConverter : Converter<URI, Path> {
		override val sourceType = URI::class.java
		override val targetType = Path::class.java
		override fun convert(value: URI): Path = value.toPath()
	}

	class UriToUrlConverter : Converter<URI, URL> {
		override val sourceType = URI::class.java
		override val targetType = URL::class.java
		override fun convert(value: URI) = value.toUrl()
	}

	class UrlToFileConverter : Converter<URL, File> {
		override val sourceType = URL::class.java
		override val targetType = File::class.java
		override fun convert(value: URL) = value.toFile()
	}

	class UrlToPathConverter : Converter<URL, Path> {
		override val sourceType = URL::class.java
		override val targetType = Path::class.java
		override fun convert(value: URL) = value.toPath()
	}

	class UrlToUriConverter : Converter<URL, URI> {
		override val sourceType = URL::class.java
		override val targetType = URI::class.java
		override fun convert(value: URL) = value.toUri()
	}
	//endregion

	//region Time Converters
	class DateToInstantConverter:Converter<Date,Instant>{
		override val sourceType = Date::class.java
		override val targetType = Instant::class.java
		override fun convert(value: Date) = value.toInstant()
	}

	class InstantToDateConverter:Converter<Instant,Date>{
		override val sourceType = Instant::class.java
		override val targetType = Date::class.java
		override fun convert(value: Instant) = Date.from(value)
	}

	class LocalDateTimeToLocalDateConverter:Converter<LocalDateTime,LocalDate>{
		override val sourceType = LocalDateTime::class.java
		override val targetType = LocalDate::class.java
		override fun convert(value: LocalDateTime) = value.toLocalDate()
	}

	class LocalDateTimeToLocalTimeConverter:Converter<LocalDateTime,LocalTime>{
		override val sourceType = LocalDateTime::class.java
		override val targetType = LocalTime::class.java
		override fun convert(value: LocalDateTime) = value.toLocalTime()
	}
	//endregion
}
