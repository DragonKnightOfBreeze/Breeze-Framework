// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import java.lang.reflect.*
import java.math.*
import java.time.*
import java.util.*
import kotlin.random.*
import kotlin.random.Random

@Suppress("UNCHECKED_CAST")
object RandomGenerators : ComponentRegistry<RandomGenerator<*>>() {
	//region Implementations
	@ComponentParam("min", "Byte")
	@ComponentParam("max", "Byte")
	open class RandomByteGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Byte>() {
		companion object Default : RandomByteGenerator()

		val min: Byte = componentParams.get("min").convertOrNull() ?: 0
		val max: Byte = componentParams.get("max").convertOrNull() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomByteGenerator {
			return RandomByteGenerator(componentParams)
		}

		override fun generate(): Byte {
			return when {
				min != 0.toByte() && max != 0.toByte() -> Random.nextByte(min, max)
				min == 0.toByte() && max == 0.toByte() -> Random.nextByte()
				max != 0.toByte() -> Random.nextByte(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ComponentParam("min", "Short")
	@ComponentParam("max", "Short")
	open class RandomShortGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Short>() {
		companion object Default : RandomShortGenerator()

		val min: Short = componentParams.get("min").convertOrNull() ?: 0
		val max: Short = componentParams.get("max").convertOrNull() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomShortGenerator {
			return RandomShortGenerator(componentParams)
		}

		override fun generate(): Short {
			return when {
				min != 0.toShort() && max != 0.toShort() -> Random.nextShort(min, max)
				min == 0.toShort() && max == 0.toShort() -> Random.nextShort()
				max != 0.toShort() -> Random.nextShort(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ComponentParam("min", "Int")
	@ComponentParam("max", "Int")
	open class RandomIntGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Int>() {
		companion object Default : RandomIntGenerator()

		val min: Int = componentParams.get("min").convertOrNull() ?: 0
		val max: Int = componentParams.get("max").convertOrNull() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomIntGenerator {
			return RandomIntGenerator(componentParams)
		}

		override fun generate(): Int {
			return when {
				min != 0 && max != 0 -> Random.nextInt(min, max)
				min == 0 && max == 0 -> Random.nextInt()
				max != 0 -> Random.nextInt(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ComponentParam("min", "Long")
	@ComponentParam("max", "Long")
	open class RandomLongGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Long>() {
		companion object Default : RandomLongGenerator()

		val min: Long = componentParams.get("min").convertOrNull() ?: 0
		val max: Long = componentParams.get("max").convertOrNull() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomLongGenerator {
			return RandomLongGenerator(componentParams)
		}

		override fun generate(): Long {
			return when {
				min != 0L && max != 0L -> Random.nextLong(min, max)
				min == 0L && max == 0L -> Random.nextLong()
				max != 0L -> Random.nextLong(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ComponentParam("min", "Float")
	@ComponentParam("max", "Float")
	open class RandomFloatGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Float>() {
		companion object Default : RandomFloatGenerator()

		val min: Float = componentParams.get("min").convertOrNull() ?: 0F
		val max: Float = componentParams.get("max").convertOrNull() ?: 0F

		override fun copy(componentParams: Map<String, Any?>): RandomFloatGenerator {
			return RandomFloatGenerator(componentParams)
		}

		override fun generate(): Float {
			return when {
				min != 0F && max != 0F -> Random.nextFloat(min, max)
				min == 0F && max == 0F -> Random.nextFloat()
				max != 0F -> Random.nextFloat(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ComponentParam("min", "Double")
	@ComponentParam("max", "Double")
	open class RandomDoubleGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Double>() {
		companion object Default : RandomDoubleGenerator()

		val min: Double = componentParams.get("min").convertOrNull() ?: 0.0
		val max: Double = componentParams.get("max").convertOrNull() ?: 0.0

		override fun copy(componentParams: Map<String, Any?>): RandomDoubleGenerator {
			return RandomDoubleGenerator(componentParams)
		}

		override fun generate(): Double {
			return when {
				min != 0.0 && max != 0.0 -> Random.nextDouble(min, max)
				min == 0.0 && max == 0.0 -> Random.nextDouble()
				max != 0.0 -> Random.nextDouble(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@UnstableApi
	@ComponentParam("min", "BigInteger")
	@ComponentParam("max", "BigInteger")
	open class RandomBigIntegerGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<BigInteger>() {
		companion object Default : RandomBigIntegerGenerator()

		val min: BigInteger = componentParams.get("min").convertOrNull() ?: BigInteger.ZERO
		val max: BigInteger = componentParams.get("max").convertOrNull() ?: BigInteger.ZERO

		override fun copy(componentParams: Map<String, Any?>): RandomBigIntegerGenerator {
			return RandomBigIntegerGenerator(componentParams)
		}

		override fun generate(): BigInteger {
			return when {
				min != BigInteger.ZERO && max != BigInteger.ZERO -> Random.nextBigInteger(min, max)
				min == BigInteger.ZERO && max == BigInteger.ZERO -> Random.nextBigInteger()
				max != BigInteger.ZERO -> Random.nextBigInteger(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@UnstableApi
	@ComponentParam("min", "BigDecimal")
	@ComponentParam("max", "BigDecimal")
	open class RandomBigDecimalGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<BigDecimal>() {
		companion object Default : RandomBigDecimalGenerator()

		val min: BigDecimal = componentParams.get("min").convertOrNull() ?: BigDecimal.ZERO
		val max: BigDecimal = componentParams.get("max").convertOrNull() ?: BigDecimal.ZERO

		override fun copy(componentParams: Map<String, Any?>): RandomBigDecimalGenerator {
			return RandomBigDecimalGenerator(componentParams)
		}

		override fun generate(): BigDecimal {
			return when {
				min != BigDecimal.ZERO && max != BigDecimal.ZERO -> Random.nextBigDecimal(min, max)
				min == BigDecimal.ZERO && max == BigDecimal.ZERO -> Random.nextBigDecimal()
				max != BigDecimal.ZERO -> Random.nextBigDecimal(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ExperimentalUnsignedTypes
	@ComponentParam("min", "UByte")
	@ComponentParam("max", "UByte")
	open class RandomUByteGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UByte>() {
		companion object Default : RandomUByteGenerator()

		val min: UByte = componentParams.get("min").convertOrNull() ?: 0.toUByte()
		val max: UByte = componentParams.get("max").convertOrNull() ?: 0.toUByte()

		override fun copy(componentParams: Map<String, Any?>): RandomUByteGenerator {
			return RandomUByteGenerator(componentParams)
		}

		override fun generate(): UByte {
			return when {
				min != 0.toUByte() && max != 0.toUByte() -> Random.nextUByte(min, max)
				min == 0.toUByte() && max == 0.toUByte() -> Random.nextUByte()
				max != 0.toUByte() -> Random.nextUByte(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ExperimentalUnsignedTypes
	@ComponentParam("min", "UShort")
	@ComponentParam("max", "UShort")
	open class RandomUShortGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UShort>() {
		companion object Default : RandomUShortGenerator()

		val min: UShort = componentParams.get("min").convertOrNull() ?: 0.toUShort()
		val max: UShort = componentParams.get("max").convertOrNull() ?: 0.toUShort()

		override fun copy(componentParams: Map<String, Any?>): RandomUShortGenerator {
			return RandomUShortGenerator(componentParams)
		}

		override fun generate(): UShort {
			return when {
				min != 0.toUShort() && max != 0.toUShort() -> Random.nextUShort(min, max)
				min == 0.toUShort() && max == 0.toUShort() -> Random.nextUShort()
				max != 0.toUShort() -> Random.nextUShort(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ExperimentalUnsignedTypes
	@ComponentParam("min", "UInt")
	@ComponentParam("max", "UInt")
	open class RandomUIntGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UInt>() {
		companion object Default : RandomUIntGenerator()

		val min: UInt = componentParams.get("min").convertOrNull() ?: 0.toUInt()
		val max: UInt = componentParams.get("max").convertOrNull() ?: 0.toUInt()

		override fun copy(componentParams: Map<String, Any?>): RandomUIntGenerator {
			return RandomUIntGenerator(componentParams)
		}

		override fun generate(): UInt {
			return when {
				min != 0.toUInt() && max != 0.toUInt() -> Random.nextUInt(min, max)
				min == 0.toUInt() && max == 0.toUInt() -> Random.nextUInt()
				max != 0.toUInt() -> Random.nextUInt(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	@ExperimentalUnsignedTypes
	@ComponentParam("min", "ULong")
	@ComponentParam("max", "ULong")
	open class RandomULongGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<ULong>() {
		companion object Default : RandomULongGenerator()

		val min: ULong = componentParams.get("min").convertOrNull() ?: 0.toULong()
		val max: ULong = componentParams.get("max").convertOrNull() ?: 0.toULong()

		override fun copy(componentParams: Map<String, Any?>): RandomULongGenerator {
			return RandomULongGenerator(componentParams)
		}

		override fun generate(): ULong {
			return when {
				min != 0.toULong() && max != 0.toULong() -> Random.nextULong(min, max)
				min == 0.toULong() && max == 0.toULong() -> Random.nextULong()
				max != 0.toULong() -> Random.nextULong(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}


	@ComponentParam("min", "Char")
	@ComponentParam("max", "Char")
	open class RandomCharGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Char>() {
		companion object Default : RandomCharGenerator()

		val min: Char = componentParams.get("min").convertOrNull() ?: Char.MIN_VALUE
		val max: Char = componentParams.get("max").convertOrNull() ?: Char.MIN_VALUE

		override fun copy(componentParams: Map<String, Any?>): RandomCharGenerator {
			return RandomCharGenerator(componentParams)
		}

		override fun generate(): Char {
			return when {
				min != Char.MIN_VALUE && max != Char.MIN_VALUE -> Random.nextChar(min, max)
				min == Char.MIN_VALUE && max == Char.MIN_VALUE -> Random.nextChar()
				max != Char.MIN_VALUE -> Random.nextChar(max)
				else -> throw IllegalArgumentException("Config param 'max' cannot be null or zero where 'min' is not null or zero.")
			}
		}
	}

	object RandomBooleanGenerator : AbstractRandomGenerator<Boolean>() {
		override fun generate(): Boolean = Random.nextBoolean()
	}

	/**
	 * 组件参数说明：
	 * * length - 长度。（覆盖最小长度和最大长度）
	 * * minLength - 最小长度。
	 * * maxLength - 最大长度。
	 * * source - 源字符串，生成的字符串的字符会从中随机选取。
	 */
	@ComponentParam("length", "Int", "0", override = "minLength, maxLength")
	@ComponentParam("minLength", "Int", "0")
	@ComponentParam("maxLength", "Int")
	@ComponentParam("source", "String", "")
	open class RandomStringGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<String>() {
		companion object Default : RandomStringGenerator()

		val length: Int = componentParams.get("length")?.convertOrNull() ?: 0
		val minLength: Int = componentParams.get("minLength")?.convertOrNull() ?: 0
		val maxLength: Int = componentParams.get("maxLength")?.convertOrNull() ?: 0
		val source: String = componentParams.get("source")?.convertOrNull() ?: ""

		/**
		 * 根据可选的配置参数，生成指定类型的随机值。
		 */
		override fun copy(componentParams: Map<String, Any?>): RandomStringGenerator {
			return RandomStringGenerator(componentParams)
		}

		/**
		 * 根据可选的配置参数，生成指定类型的随机值。
		 */
		override fun generate(): String {
			if(length < 0) throw IllegalArgumentException("Config param 'length' cannot be negative.")
			if(length == 0) {
				if(minLength < 0) throw IllegalArgumentException("Config param 'minLength' cannot be negative.")
				if(maxLength < 0) throw IllegalArgumentException("Config param 'maxLength' cannot be negative.")
				if(maxLength == 0) throw java.lang.IllegalArgumentException("Config param 'maxLength cannot be null.'")
			}
			if(source.isEmpty()) throw IllegalArgumentException("Config param 'source' cannot be null or empty.")
			val length = if(length != 0) length else Random.nextInt(minLength, maxLength)
			return buildString {
				repeat(length) {
					append(Random.nextElement(source))
				}
			}
		}
	}

	object RandomUuidGenerator : AbstractRandomGenerator<UUID>() {
		override fun generate(): UUID {
			return Random.nextUuid()
		}
	}

	@ComponentParam("min", "Date", "<null>")
	@ComponentParam("max", "Date", "<null>")
	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class RandomDateGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Date>() {
		companion object Default : RandomDateGenerator()

		private val passingComponentParams = filterComponentParams(componentParams, "format", "locale", "timeZone")

		val min: Date? = componentParams.get("min").convertOrNull(passingComponentParams)
		val max: Date? = componentParams.get("max").convertOrNull(passingComponentParams)

		private val minEpochSecond = min?.toInstant()?.epochSecond ?: 0
		private val maxEpochSecond = max?.toInstant()?.epochSecond ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomDateGenerator {
			return RandomDateGenerator(componentParams)
		}

		override fun generate(): Date {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return Date.from(Instant.ofEpochSecond(epochSecond))
		}
	}

	@ComponentParam("min", "LocalDate")
	@ComponentParam("max", "LocalDate")
	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class RandomLocalDateGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalDate>() {
		companion object Default : RandomLocalDateGenerator()

		private val passingComponentParams = filterComponentParams(componentParams, "format", "locale", "timeZone")

		val min: LocalDate? = componentParams.get("min").convertOrNull(passingComponentParams)
		val max: LocalDate? = componentParams.get("max").convertOrNull(passingComponentParams)

		private val minEpochDay = min?.toEpochDay() ?: 0
		private val maxEpochDay = max?.toEpochDay() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomLocalDateGenerator {
			return RandomLocalDateGenerator(componentParams)
		}

		override fun generate(): LocalDate {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochDay = Random.nextLong(minEpochDay, maxEpochDay)
			return LocalDate.ofEpochDay(epochDay)
		}
	}

	@ComponentParam("min", "LocalTime")
	@ComponentParam("max", "LocalTime")
	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class RandomLocalTimeGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalTime>() {
		companion object Default : RandomLocalTimeGenerator()

		private val passingComponentParams = filterComponentParams(componentParams, "format", "locale", "timeZone")

		val min: LocalTime? = componentParams.get("min").convertOrNull(passingComponentParams)
		val max: LocalTime? = componentParams.get("max").convertOrNull(passingComponentParams)

		private val minNanoOfDay = min?.toNanoOfDay() ?: 0
		private val maxNanoOfDay = max?.toNanoOfDay() ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomLocalTimeGenerator {
			return RandomLocalTimeGenerator(componentParams)
		}

		override fun generate(): LocalTime {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val nanoOfDay = Random.nextLong(minNanoOfDay, maxNanoOfDay)
			return LocalTime.ofNanoOfDay(nanoOfDay)
		}
	}

	@ComponentParam("min", "LocalDateTime")
	@ComponentParam("max", "LocalDateTime")
	@ComponentParam("format", "String", "yyyy-MM-dd")
	@ComponentParam("locale", "String | Locale", "<default>")
	@ComponentParam("timeZone", "String | TimeZone", "<utc>")
	open class RandomLocalDateTimeGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalDateTime>() {
		companion object Default : RandomLocalDateTimeGenerator()

		private val passingComponentParams = filterComponentParams(componentParams, "format", "locale", "timeZone")

		val min: LocalDateTime? = componentParams.get("min").convertOrNull(passingComponentParams)
		val max: LocalDateTime? = componentParams.get("max").convertOrNull(passingComponentParams)

		private val minEpochSecond = min?.toEpochSecond(ZoneOffset.UTC) ?: 0
		private val maxEpochSecond = max?.toEpochSecond(ZoneOffset.UTC) ?: 0

		override fun copy(componentParams: Map<String, Any?>): RandomLocalDateTimeGenerator {
			return RandomLocalDateTimeGenerator(componentParams)
		}

		override fun generate(): LocalDateTime {
			//忽略nanoSecond
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC)
		}
	}

	@ComponentParam("min", "LocalDateTime")
	@ComponentParam("max", "LocalDateTime")
	open class RandomInstantGenerator(
		final override val componentParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Instant>() {
		companion object Default : RandomInstantGenerator()

		val min: Instant? = componentParams.get("min").convertOrNull()
		val max: Instant? = componentParams.get("max").convertOrNull()

		private val minEpochSecond = min?.epochSecond ?: 0
		private val maxEpochSecond = max?.epochSecond ?: 0

		override fun generate(): Instant {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return Instant.ofEpochSecond(epochSecond)
		}
	}

	object RandomEnumGenerator : AbstractRandomGenerator<Enum<*>>(), GenericRandomGenerator<Enum<*>> {
		override fun generate(targetType: Type): Enum<*> {
			val enumClass = inferEnumClass(targetType)
			if(enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
			val enumValues = enumValuesCache.getOrPut(enumClass) {
				enumClass.enumConstants?.toList() ?: emptyList()
			}
			return Random.nextElement(enumValues)
		}
	}
	//endregion

	/**
	 * 是否使用回退策略。默认不使用。
	 *
	 * 如果使用回退策略且找不到匹配的随机值生成器，则尝试调用目标类型的无参构造方法生成默认值。
	 */
	var useFallbackStrategy = false

	@OptIn(ExperimentalUnsignedTypes::class)
	override fun registerDefault() {
		register(RandomByteGenerator)
		register(RandomShortGenerator)
		register(RandomIntGenerator)
		register(RandomLongGenerator)
		register(RandomFloatGenerator)
		register(RandomDoubleGenerator)
		register(RandomBooleanGenerator)
		register(RandomCharGenerator)
		register(RandomBigIntegerGenerator)
		register(RandomBigDecimalGenerator)
		register(RandomUByteGenerator)
		register(RandomUShortGenerator)
		register(RandomUIntGenerator)
		register(RandomULongGenerator)
		register(RandomStringGenerator)
		register(RandomUuidGenerator)
		register(RandomDateGenerator)
		register(RandomLocalDateGenerator)
		register(RandomLocalTimeGenerator)
		register(RandomLocalDateTimeGenerator)
		register(RandomInstantGenerator)
		register(RandomEnumGenerator)
	}

	/**
	 * 根据指定的目标类型和配置参数，从缓存中得到随机值生成器。如果没有，则创建并放入。
	 */
	fun <T, C : RandomGenerator<T>> get(targetType: Class<T>, componentParams: Map<String, Any?>, defaultValue: () -> C): C {
		return components.getOrPut(inferKey(targetType, componentParams), defaultValue) as C
	}

	/**
	 * 生成指定类型的随机值。
	 */
	inline fun <reified T> generate(componentParams: Map<String, Any?> = emptyMap()): T {
		return generate(javaTypeOf<T>(), componentParams)
	}

	/**
	 * 生成指定类型的随机值。
	 */
	fun <T> generate(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
		return doGenerate(componentParams, targetType)
	}

	/**
	 * 生成指定类型的随机值。
	 */
	fun <T> generate(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
		return doGenerate(componentParams, targetType)
	}

	private fun <T> doGenerate(componentParams: Map<String, Any?>, targetType: Type): T {
		val targetClass = inferClass(targetType)
		//遍历已注册的默认值生成器，如果匹配目标类型，则尝试用它随机值，并加入缓存
		val key = inferKey(targetClass, componentParams)
		val randomGenerator = components.getOrPut(key) {
			val result = infer(targetClass, componentParams)
			if(result == null) {
				if(useFallbackStrategy) {
					val fallback = fallbackGenerate(targetClass)
					if(fallback != null) return fallback as T
				}
				throw IllegalArgumentException("No suitable random generator found for target type '$targetType'.")
			}
			result
		}
		return when(randomGenerator) {
			is GenericRandomGenerator<*> -> randomGenerator.generate(targetType) as T
			else -> randomGenerator.generate() as T
		}
	}

	private fun inferKey(targetType: Class<*>, componentParams: Map<String, Any?>): String {
		return if(componentParams.isEmpty()) targetType.name else "${targetType.name}@$componentParams"
	}

	private fun infer(targetType: Class<*>, componentParams: Map<String, Any?>): RandomGenerator<*>? {
		var result = components.values.findLast { it.targetType.isAssignableFrom(targetType) } ?: return null
		if(result.componentParams.toString() != componentParams.toString()) {
			result = result.copy(componentParams)
		}
		return result
	}

	private fun <T> fallbackGenerate(targetType: Class<T>): T? {
		try {
			//尝试调用目标类型的无参构造方法生成默认值
			val constructor = targetType.getDeclaredConstructor()
			constructor.isAccessible = true
			return constructor.newInstance()
		} catch(e: Exception) {
			return null
		}
	}
}
