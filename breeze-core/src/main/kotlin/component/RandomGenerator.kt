// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import java.math.*
import java.time.*
import java.util.*
import java.util.concurrent.*
import kotlin.random.*
import kotlin.random.Random

/**
 * 随机值生成器。
 *
 * 随机值生成器用于基于给定的参数生成随机值。
 */
interface RandomGenerator<T> : Component {
	/**目标类型。*/
	val targetType: Class<T>

	/**
	 * 生成随机值。
	 */
	fun generate(): T

	companion object Registry : AbstractComponentRegistry<RandomGenerator<*>>() {
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

		private val componentMap: MutableMap<String, RandomGenerator<*>> = ConcurrentHashMap()

		/**
		 * 是否使用回退策略。默认不使用。
		 * 如果使用回退策略且找不到匹配的随机值生成器，则尝试调用目标类型的无参构造方法生成默认值。
		 */
		var useFallbackStrategy = false

		/**
		 * 生成指定类型的随机值。
		 */
		inline fun <reified T> generate(configParams: Map<String, Any?> = emptyMap()): T {
			return generate(T::class.java, configParams)
		}

		/**
		 * 生成指定类型的随机值。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> generate(targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T {
			//遍历已注册的默认值生成器，如果匹配目标类型，则尝试用它随机值，并加入缓存
			val key = if(configParams.isEmpty()) targetType.name else targetType.name + '@' + configParams.toString()
			val randomGenerator = componentMap.getOrPut(key) {
				val result = inferRandomGenerator(targetType, configParams)
				if(result == null) {
					if(useFallbackStrategy) {
						val fallback = fallbackGenerate(targetType)
						if(fallback != null) return fallback
					}
					throw IllegalArgumentException("No suitable random generator found for target type '$targetType'.")
				}
				result
			}
			return randomGenerator.generate() as T
		}

		private fun <T> inferRandomGenerator(
			targetType: Class<T>,
			configParams: Map<String, Any?>
		): RandomGenerator<*>? {
			var result = components.find { it.targetType.isAssignableFrom(targetType) }
			if(result is ConfigurableRandomGenerator<*> && configParams.isNotEmpty()) {
				result = result.configure(configParams)
			}
			if(result is BoundRandomGenerator<*> && targetType != result.targetType) {
				result = result.bindingActualTargetType(targetType)
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

	//region Random Generators
	@ConfigParams(
		ConfigParam("min", "Byte"),
		ConfigParam("max", "Byte")
	)
	open class RandomByteGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Byte>(), ConfigurableRandomGenerator<Byte> {
		companion object Default : RandomByteGenerator()

		val min: Byte = configParams.get("min").convertOrNull() ?: 0
		val max: Byte = configParams.get("max").convertOrNull() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomByteGenerator {
			return RandomByteGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Short"),
		ConfigParam("max", "Short")
	)
	open class RandomShortGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Short>(), ConfigurableRandomGenerator<Short> {
		companion object Default : RandomShortGenerator()

		val min: Short = configParams.get("min").convertOrNull() ?: 0
		val max: Short = configParams.get("max").convertOrNull() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomShortGenerator {
			return RandomShortGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Int"),
		ConfigParam("max", "Int")
	)
	open class RandomIntGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Int>(), ConfigurableRandomGenerator<Int> {
		companion object Default : RandomIntGenerator()

		val min: Int = configParams.get("min").convertOrNull() ?: 0
		val max: Int = configParams.get("max").convertOrNull() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomIntGenerator {
			return RandomIntGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Long"),
		ConfigParam("max", "Long")
	)
	open class RandomLongGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Long>(), ConfigurableRandomGenerator<Long> {
		companion object Default : RandomLongGenerator()

		val min: Long = configParams.get("min").convertOrNull() ?: 0
		val max: Long = configParams.get("max").convertOrNull() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomLongGenerator {
			return RandomLongGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Float"),
		ConfigParam("max", "Float")
	)
	open class RandomFloatGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Float>(), ConfigurableRandomGenerator<Float> {
		companion object Default : RandomFloatGenerator()

		val min: Float = configParams.get("min").convertOrNull() ?: 0F
		val max: Float = configParams.get("max").convertOrNull() ?: 0F

		override fun configure(configParams: Map<String, Any?>): RandomFloatGenerator {
			return RandomFloatGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Double"),
		ConfigParam("max", "Double")
	)
	open class RandomDoubleGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Double>(), ConfigurableRandomGenerator<Double> {
		companion object Default : RandomDoubleGenerator()

		val min: Double = configParams.get("min").convertOrNull() ?: 0.0
		val max: Double = configParams.get("max").convertOrNull() ?: 0.0

		override fun configure(configParams: Map<String, Any?>): RandomDoubleGenerator {
			return RandomDoubleGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "BigInteger"),
		ConfigParam("max", "BigInteger")
	)
	@UnstableApi
	open class RandomBigIntegerGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<BigInteger>(), ConfigurableRandomGenerator<BigInteger> {
		companion object Default : RandomBigIntegerGenerator()

		val min: BigInteger = configParams.get("min").convertOrNull() ?: BigInteger.ZERO
		val max: BigInteger = configParams.get("max").convertOrNull() ?: BigInteger.ZERO

		override fun configure(configParams: Map<String, Any?>): RandomBigIntegerGenerator {
			return RandomBigIntegerGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "BigDecimal"),
		ConfigParam("max", "BigDecimal")
	)
	@UnstableApi
	open class RandomBigDecimalGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<BigDecimal>(), ConfigurableRandomGenerator<BigDecimal> {
		companion object Default : RandomBigDecimalGenerator()

		val min: BigDecimal = configParams.get("min").convertOrNull() ?: BigDecimal.ZERO
		val max: BigDecimal = configParams.get("max").convertOrNull() ?: BigDecimal.ZERO

		override fun configure(configParams: Map<String, Any?>): RandomBigDecimalGenerator {
			return RandomBigDecimalGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "UByte"),
		ConfigParam("max", "UByte")
	)
	@ExperimentalUnsignedTypes
	open class RandomUByteGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UByte>(), ConfigurableRandomGenerator<UByte> {
		companion object Default : RandomUByteGenerator()

		val min: UByte = configParams.get("min").convertOrNull() ?: 0.toUByte()
		val max: UByte = configParams.get("max").convertOrNull() ?: 0.toUByte()

		override fun configure(configParams: Map<String, Any?>): RandomUByteGenerator {
			return RandomUByteGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "UShort"),
		ConfigParam("max", "UShort")
	)
	@ExperimentalUnsignedTypes
	open class RandomUShortGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UShort>(), ConfigurableRandomGenerator<UShort> {
		companion object Default : RandomUShortGenerator()

		val min: UShort = configParams.get("min").convertOrNull() ?: 0.toUShort()
		val max: UShort = configParams.get("max").convertOrNull() ?: 0.toUShort()

		override fun configure(configParams: Map<String, Any?>): RandomUShortGenerator {
			return RandomUShortGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "UInt"),
		ConfigParam("max", "UInt")
	)
	@ExperimentalUnsignedTypes
	open class RandomUIntGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<UInt>(), ConfigurableRandomGenerator<UInt> {
		companion object Default : RandomUIntGenerator()

		val min: UInt = configParams.get("min").convertOrNull() ?: 0.toUInt()
		val max: UInt = configParams.get("max").convertOrNull() ?: 0.toUInt()

		override fun configure(configParams: Map<String, Any?>): RandomUIntGenerator {
			return RandomUIntGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "ULong"),
		ConfigParam("max", "ULong")
	)
	@ExperimentalUnsignedTypes
	open class RandomULongGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<ULong>(), ConfigurableRandomGenerator<ULong> {
		companion object Default : RandomULongGenerator()

		val min: ULong = configParams.get("min").convertOrNull() ?: 0.toULong()
		val max: ULong = configParams.get("max").convertOrNull() ?: 0.toULong()

		override fun configure(configParams: Map<String, Any?>): RandomULongGenerator {
			return RandomULongGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Char"),
		ConfigParam("max", "Char")
	)
	open class RandomCharGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Char>(), ConfigurableRandomGenerator<Char> {
		companion object Default : RandomCharGenerator()

		val min: Char = configParams.get("min").convertOrNull() ?: Char.MIN_VALUE
		val max: Char = configParams.get("max").convertOrNull() ?: Char.MIN_VALUE

		override fun configure(configParams: Map<String, Any?>): RandomCharGenerator {
			return RandomCharGenerator(configParams)
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
	 * 参数说明：
	 * * length - 长度。（覆盖最小长度和最大长度）
	 * * minLength - 最小长度。
	 * * maxLength - 最大长度。
	 * * source - 源字符串，生成的字符串的字符会从中随机选取。
	 */
	@ConfigParams(
		ConfigParam("length", "Int", "0", override = "minLength, maxLength"),
		ConfigParam("minLength", "Int", "0"),
		ConfigParam("maxLength", "Int"),
		ConfigParam("source", "String", "")
	)
	open class RandomStringGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<String>(), ConfigurableRandomGenerator<String> {
		companion object Default : RandomStringGenerator()

		val length: Int = configParams.get("length")?.convertOrNull() ?: 0
		val minLength: Int = configParams.get("minLength")?.convertOrNull() ?: 0
		val maxLength: Int = configParams.get("maxLength")?.convertOrNull() ?: 0
		val source: String = configParams.get("source")?.convertOrNull() ?: ""

		/**
		 * 根据可选的配置参数，生成指定类型的随机值。
		 */
		override fun configure(configParams: Map<String, Any?>): RandomStringGenerator {
			return RandomStringGenerator(configParams)
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

	@ConfigParams(
		ConfigParam("min", "Date", "<null>"),
		ConfigParam("max", "Date", "<null>"),
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class RandomDateGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Date>(), ConfigurableRandomGenerator<Date> {
		companion object Default : RandomDateGenerator()

		private val temporalConfigParams = optimizeConfigParams(configParams, "format", "locale", "timeZone")

		val min: Date? = configParams.get("min").convertOrNull(temporalConfigParams)
		val max: Date? = configParams.get("max").convertOrNull(temporalConfigParams)

		private val minEpochSecond = min?.toInstant()?.epochSecond ?: 0
		private val maxEpochSecond = max?.toInstant()?.epochSecond ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomDateGenerator {
			return RandomDateGenerator(configParams)
		}

		override fun generate(): Date {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return Date.from(Instant.ofEpochSecond(epochSecond))
		}
	}

	@ConfigParams(
		ConfigParam("min", "LocalDate"),
		ConfigParam("max", "LocalDate"),
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class RandomLocalDateGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalDate>(), ConfigurableRandomGenerator<LocalDate> {
		companion object Default : RandomLocalDateGenerator()

		private val temporalConfigParams = optimizeConfigParams(configParams, "format", "locale", "timeZone")

		val min: LocalDate? = configParams.get("min").convertOrNull(temporalConfigParams)
		val max: LocalDate? = configParams.get("max").convertOrNull(temporalConfigParams)

		private val minEpochDay = min?.toEpochDay() ?: 0
		private val maxEpochDay = max?.toEpochDay() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomLocalDateGenerator {
			return RandomLocalDateGenerator(configParams)
		}

		override fun generate(): LocalDate {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochDay = Random.nextLong(minEpochDay, maxEpochDay)
			return LocalDate.ofEpochDay(epochDay)
		}
	}

	@ConfigParams(
		ConfigParam("min", "LocalTime"),
		ConfigParam("max", "LocalTime"),
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class RandomLocalTimeGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalTime>(), ConfigurableRandomGenerator<LocalTime> {
		companion object Default : RandomLocalTimeGenerator()

		private val temporalConfigParams = optimizeConfigParams(configParams, "format", "locale", "timeZone")

		val min: LocalTime? = configParams.get("min").convertOrNull(temporalConfigParams)
		val max: LocalTime? = configParams.get("max").convertOrNull(temporalConfigParams)

		private val minNanoOfDay = min?.toNanoOfDay() ?: 0
		private val maxNanoOfDay = max?.toNanoOfDay() ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomLocalTimeGenerator {
			return RandomLocalTimeGenerator(configParams)
		}

		override fun generate(): LocalTime {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val nanoOfDay = Random.nextLong(minNanoOfDay, maxNanoOfDay)
			return LocalTime.ofNanoOfDay(nanoOfDay)
		}
	}

	@ConfigParams(
		ConfigParam("min", "LocalDateTime"),
		ConfigParam("max", "LocalDateTime"),
		ConfigParam("format", "String", "yyyy-MM-dd"),
		ConfigParam("locale", "String | Locale", "<default>"),
		ConfigParam("timeZone", "String | TimeZone", "<utc>")
	)
	open class RandomLocalDateTimeGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<LocalDateTime>(), ConfigurableRandomGenerator<LocalDateTime> {
		companion object Default : RandomLocalDateTimeGenerator()

		private val temporalConfigParams = optimizeConfigParams(configParams, "format", "locale", "timeZone")

		val min: LocalDateTime? = configParams.get("min").convertOrNull(temporalConfigParams)
		val max: LocalDateTime? = configParams.get("max").convertOrNull(temporalConfigParams)

		private val minEpochSecond = min?.toEpochSecond(ZoneOffset.UTC) ?: 0
		private val maxEpochSecond = max?.toEpochSecond(ZoneOffset.UTC) ?: 0

		override fun configure(configParams: Map<String, Any?>): RandomLocalDateTimeGenerator {
			return RandomLocalDateTimeGenerator(configParams)
		}

		override fun generate(): LocalDateTime {
			//忽略nanoSecond
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC)
		}
	}

	@ConfigParams(
		ConfigParam("min", "LocalDateTime"),
		ConfigParam("max", "LocalDateTime")
	)
	open class RandomInstantGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<Instant>(), ConfigurableRandomGenerator<Instant> {
		companion object Default : RandomInstantGenerator()

		val min: Instant? = configParams.get("min").convertOrNull()
		val max: Instant? = configParams.get("max").convertOrNull()

		private val minEpochSecond = min?.epochSecond ?: 0
		private val maxEpochSecond = max?.epochSecond ?: 0

		override fun configure(configParams: Map<String, Any?>): ConfigurableRandomGenerator<Instant> {
			return RandomInstantGenerator(configParams)
		}

		override fun generate(): Instant {
			if(min == null || max == null) throw IllegalArgumentException("Config param 'min' or 'max' cannot be null.")
			val epochSecond = Random.nextLong(minEpochSecond, maxEpochSecond)
			return Instant.ofEpochSecond(epochSecond)
		}
	}

	open class RandomEnumGenerator(
		override val actualTargetType: Class<out Enum<*>> = Enum::class.java
	) : AbstractRandomGenerator<Enum<*>>(), BoundRandomGenerator<Enum<*>> {
		companion object Default : RandomEnumGenerator()

		private val enumClass: Class<out Enum<*>> by lazy { actualTargetType }
		private val enumValues: List<Enum<*>> by lazy { if(enumClass == Enum::class.java) emptyList() else enumClass.enumConstants.toList() }

		@Suppress("UNCHECKED_CAST")
		override fun bindingActualTargetType(actualTargetType: Class<*>): BoundRandomGenerator<Enum<*>> {
			return RandomEnumGenerator(actualTargetType as Class<out Enum<*>>)
		}

		override fun generate(): Enum<*> {
			if(enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
			return Random.nextElement(enumValues)
		}
	}
	//endregion
}
