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
			registerDefaultConverters()
		}

		private fun registerDefaultConverters(){

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
	private object StringToIntConverter : Converter<String, Int> {
		override val sourceType = String::class.java
		override val targetType = Int::class.javaObjectType
		override fun convert(value: String) = value.toInt()
		override fun convertOrNull(value: String) = value.toIntOrNull()
	}

	private object StringToLongConverter : Converter<String, Long> {
		override val sourceType = String::class.java
		override val targetType = Long::class.javaObjectType
		override fun convert(value: String) = value.toLong()
		override fun convertOrNull(value: String) = value.toLongOrNull()
	}

	private object StringToFloatConverter : Converter<String, Float> {
		override val sourceType = String::class.java
		override val targetType = Float::class.javaObjectType
		override fun convert(value: String) = value.toFloat()
		override fun convertOrNull(value: String) = value.toFloatOrNull()
	}

	private object StringToDoubleConverter : Converter<String, Double> {
		override val sourceType = String::class.java
		override val targetType = Double::class.javaObjectType
		override fun convert(value: String) = value.toDouble()
		override fun convertOrNull(value: String) = value.toDoubleOrNull()
	}

	private object StringToByteConverter : Converter<String, Byte> {
		override val sourceType = String::class.java
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: String) = value.toByte()
		override fun convertOrNull(value: String) = value.toByteOrNull()
	}

	private object StringToShortConverter : Converter<String, Short> {
		override val sourceType = String::class.java
		override val targetType = Short::class.javaObjectType
		override fun convert(value: String) = value.toShort()
		override fun convertOrNull(value: String) = value.toShortOrNull()
	}

	private object StringToCharConverter : Converter<String, Char> {
		override val sourceType = String::class.java
		override val targetType = Char::class.javaObjectType
		override fun convert(value: String) = value.toChar()
		override fun convertOrNull(value: String) = value.toCharOrNull()
	}

	private object StringToBooleanConverter : Converter<String, Boolean> {
		override val sourceType = String::class.java
		override val targetType = Boolean::class.javaObjectType
		override fun convert(value: String) = value.toBoolean()
		override fun convertOrNull(value: String) = value.toBooleanOrNull()
	}

	private object StringToRegexConverter : Converter<String, Regex> {
		override val sourceType = String::class.java
		override val targetType = Regex::class.java
		override fun convert(value: String) = value.toRegex()
	}

	private object StringToFileConverter : Converter<String, File> {
		override val sourceType = String::class.java
		override val targetType = File::class.java
		override fun convert(value: String) = value.toFile()
	}

	private object StringToPathConverter : Converter<String, Path> {
		override val sourceType = String::class.java
		override val targetType = Path::class.java
		override fun convert(value: String) = value.toPath()
	}

	private object StringToUrlConverter : Converter<String, URL> {
		override val sourceType = String::class.java
		override val targetType = URL::class.java
		override fun convert(value: String) = value.toUrl()
	}

	private object StringToUriConverter : Converter<String, URI> {
		override val sourceType = String::class.java
		override val targetType = URI::class.java
		override fun convert(value: String) = value.toUri()
	}

	private object StringToCharsetConverter : Converter<String, Charset> {
		override val sourceType = String::class.java
		override val targetType = Charset::class.java
		override fun convert(value: String) = value.toCharset()
		override fun convertOrNull(value: String) = value.toCharsetOrNull()
	}

	private object StringToTimeZoneConverter : Converter<String, TimeZone> {
		override val sourceType = String::class.java
		override val targetType = TimeZone::class.java
		override fun convert(value: String) = value.toTimeZone()
		override fun convertOrNull(value: String) = value.toTimeZoneOrNull()
	}

	private object StringToLocaleConverter:Converter<String,Locale>{
		override val sourceType = String::class.java
		override val targetType = Locale::class.java
		override fun convert(value: String) = value.toLocale()
	}

	private object StringToClassConverter : Converter<String, Class<*>> {
		override val sourceType = String::class.java
		override val targetType = Class::class.java
		override fun convert(value: String) = value.toClass()
		override fun convertOrNull(value: String) = value.toClassOrNull()
	}

	private object StringToDateConverter:Converter<String,Date>{
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

	private object StringToLocalDateConverter:Converter<String, LocalDate>{
		override val sourceType = String::class.java
		override val targetType = LocalDate::class.java

		var format = defaultDateFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ StringToDateConverter.format = it.toString() }
			params["locale"]?.let{ StringToDateConverter.locale = it.toString().toLocale() }
			params["timeZone"]?.let{ StringToDateConverter.timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalDate {
			return LocalDate.parse(value,formatter)
		}
	}

	private object StringToLocalTimeConverter:Converter<String, LocalTime>{
		override val sourceType = String::class.java
		override val targetType = LocalTime::class.java

		var format = defaultTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ StringToDateConverter.format = it.toString() }
			params["locale"]?.let{ StringToDateConverter.locale = it.toString().toLocale() }
			params["timeZone"]?.let{ StringToDateConverter.timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalTime {
			return LocalTime.parse(value,formatter)
		}
	}

	private object StringToLocalDateTimeConverter:Converter<String, LocalDateTime>{
		override val sourceType = String::class.java
		override val targetType = LocalDateTime::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ StringToDateConverter.format = it.toString() }
			params["locale"]?.let{ StringToDateConverter.locale = it.toString().toLocale() }
			params["timeZone"]?.let{ StringToDateConverter.timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: String): LocalDateTime {
			return LocalDateTime.parse(value,formatter)
		}
	}
	//endregion

	//region To String Generators
	private object DateToStringConverter : Converter<Date, String> {
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

	private object LocalDateToStringConverter : Converter<LocalDate, String> {
		override val sourceType = LocalDate::class.java
		override val targetType = String::class.java

		var format = defaultDateTimeFormat
		var locale = defaultLocale
		var timeZone = defaultTimeZone
		var formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
			private set

		override fun configure(params: Map<String, Any?>) {
			params["format"]?.let{ StringToDateConverter.format = it.toString() }
			params["locale"]?.let{ StringToDateConverter.locale = it.toString().toLocale() }
			params["timeZone"]?.let{ StringToDateConverter.timeZone = it.toString().toTimeZone() }
			formatter = DateTimeFormatter.ofPattern(format,locale).withZone(timeZone.toZoneId())
		}

		override fun convert(value: LocalDate): String {
			return formatter.format(value)
		}
	}
	//endregion

	//region Number And Primitive Converters
	private object IntToLongConverter : Converter<Int, Long> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Int) = value.toLong()
	}

	private object IntToFloatConverter : Converter<Int, Float> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Int) = value.toFloat()
	}

	private object IntToDoubleConverter : Converter<Int, Double> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Int) = value.toDouble()
	}

	private object IntToByteConverter : Converter<Int, Byte> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Int) = value.toByte()
	}

	private object IntToShortConverter : Converter<Int, Short> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Int) = value.toShort()
	}

	private object IntToCharConverter : Converter<Int, Char> {
		override val sourceType = Int::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Int) = value.toChar()
	}

	private object LongToIntConverter : Converter<Long, Int> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Long) = value.toInt()
	}

	private object LongToFloatConverter : Converter<Long, Float> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Long) = value.toFloat()
	}

	private object LongToDoubleConverter : Converter<Long, Double> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Long) = value.toDouble()
	}

	private object LongToByteConverter : Converter<Long, Byte> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Long) = value.toByte()
	}

	private object LongToShortConverter : Converter<Long, Short> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Long) = value.toShort()
	}

	private object LongToCharConverter : Converter<Long, Char> {
		override val sourceType = Long::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Long) = value.toChar()
	}

	private object FloatToIntConverter : Converter<Float, Int> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Float) = value.toInt()
	}

	private object FloatToLongConverter : Converter<Float, Long> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Float) = value.toLong()
	}

	private object FloatToDoubleConverter : Converter<Float, Double> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Float) = value.toDouble()
	}

	private object FloatToByteConverter : Converter<Float, Byte> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toByte()
	}

	private object FloatToShortConverter : Converter<Float, Short> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Float) = value.toShort()
	}

	private object FloatToCharConverter : Converter<Float, Char> {
		override val sourceType = Float::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Float) = value.toChar()
	}

	private object DoubleToIntConverter : Converter<Double, Int> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Double) = value.toInt()
	}

	private object DoubleToLongConverter : Converter<Double, Long> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Double) = value.toLong()
	}

	private object DoubleToFloatConverter : Converter<Double, Float> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Double) = value.toFloat()
	}

	private object DoubleToByteConverter : Converter<Double, Byte> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Byte::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toByte()
	}

	private object DoubleToShortConverter : Converter<Double, Short> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Short::class.javaObjectType

		@Suppress("DEPRECATION")
		override fun convert(value: Double) = value.toShort()
	}

	private object DoubleToCharConverter : Converter<Double, Char> {
		override val sourceType = Double::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Double) = value.toChar()
	}

	private object ByteToIntConverter : Converter<Byte, Int> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Byte) = value.toInt()
	}

	private object ByteToLongConverter : Converter<Byte, Long> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Byte) = value.toLong()
	}

	private object ByteToFloatConverter : Converter<Byte, Float> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Byte) = value.toFloat()
	}

	private object ByteToDoubleConverter : Converter<Byte, Double> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Byte) = value.toDouble()
	}

	private object ByteToShortConverter : Converter<Byte, Short> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Byte) = value.toShort()
	}

	private object ByteToCharConverter : Converter<Byte, Char> {
		override val sourceType = Byte::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Byte) = value.toChar()
	}

	private object ShortToIntConverter : Converter<Short, Int> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Short) = value.toInt()
	}

	private object ShortToLongConverter : Converter<Short, Long> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Short) = value.toLong()
	}

	private object ShortToFloatConverter : Converter<Short, Float> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Short) = value.toFloat()
	}

	private object ShortToDoubleConverter : Converter<Short, Double> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Short) = value.toDouble()
	}

	private object ShortToByteConverter : Converter<Short, Byte> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Short) = value.toByte()
	}

	private object ShortToCharConverter : Converter<Short, Char> {
		override val sourceType = Short::class.javaObjectType
		override val targetType = Char::class.javaObjectType
		override fun convert(value: Short) = value.toChar()
	}

	private object CharToIntConverter : Converter<Char, Int> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Int::class.javaObjectType
		override fun convert(value: Char) = value.toInt()
	}

	private object CharToLongConverter : Converter<Char, Long> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Long::class.javaObjectType
		override fun convert(value: Char) = value.toLong()
	}

	private object CharToFloatConverter : Converter<Char, Float> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Float::class.javaObjectType
		override fun convert(value: Char) = value.toFloat()
	}

	private object CharToDoubleConverter : Converter<Char, Double> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Double::class.javaObjectType
		override fun convert(value: Char) = value.toDouble()
	}

	private object CharToByteConverter : Converter<Char, Byte> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Byte::class.javaObjectType
		override fun convert(value: Char) = value.toByte()
	}

	private object CharToShortConverter : Converter<Char, Short> {
		override val sourceType = Char::class.javaObjectType
		override val targetType = Short::class.javaObjectType
		override fun convert(value: Char) = value.toShort()
	}
	//endregion

	//region IO Converters
	private object FileToPathConverter : Converter<File, Path> {
		override val sourceType = File::class.java
		override val targetType = Path::class.java
		override fun convert(value: File) = value.toPath()
	}

	private object FileToUriConverter : Converter<File, URI> {
		override val sourceType = File::class.java
		override val targetType = URI::class.java
		override fun convert(value: File) = value.toUri()
	}

	private object FileToUrlConverter : Converter<File, URL> {
		override val sourceType = File::class.java
		override val targetType = URL::class.java
		override fun convert(value: File) = value.toUrl()
	}

	private object PathToFileConverter : Converter<Path, File> {
		override val sourceType = Path::class.java
		override val targetType = File::class.java
		override fun convert(value: Path) = value.toFile()
	}

	private object PathToUriConverter : Converter<Path, URI> {
		override val sourceType = Path::class.java
		override val targetType = URI::class.java
		override fun convert(value: Path) = value.toUri()
	}

	private object PathToUrlConverter : Converter<Path, URL> {
		override val sourceType = Path::class.java
		override val targetType = URL::class.java
		override fun convert(value: Path) = value.toUrl()
	}

	private object UriToFileConverter : Converter<URI, File> {
		override val sourceType = URI::class.java
		override val targetType = File::class.java
		override fun convert(value: URI) = value.toFile()
	}

	private object UriToPathConverter : Converter<URI, Path> {
		override val sourceType = URI::class.java
		override val targetType = Path::class.java
		override fun convert(value: URI): Path = value.toPath()
	}

	private object UriToUrlConverter : Converter<URI, URL> {
		override val sourceType = URI::class.java
		override val targetType = URL::class.java
		override fun convert(value: URI) = value.toUrl()
	}

	private object UrlToFileConverter : Converter<URL, File> {
		override val sourceType = URL::class.java
		override val targetType = File::class.java
		override fun convert(value: URL) = value.toFile()
	}

	private object UrlToPathConverter : Converter<URL, Path> {
		override val sourceType = URL::class.java
		override val targetType = Path::class.java
		override fun convert(value: URL) = value.toPath()
	}

	private object UrlToUriConverter : Converter<URL, URI> {
		override val sourceType = URL::class.java
		override val targetType = URI::class.java
		override fun convert(value: URL) = value.toUri()
	}
	//endregion

	//region Temporal Converters

	//endregion
}
