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
			register(RandomBigIntegerGenerator)
			register(RandomBigDecimalGenerator)
			register(RandomUIntGenerator)
			register(RandomULongGenerator)
			register(RandomCharGenerator)
			register(RandomBooleanGenerator)
			register(RandomStringGenerator)
			register(RandomUuidGenerator)
			register(RandomDateGenerator)
			register(RandomLocalDateGenerator)
			register(RandomLocalTimeGenerator)
			register(RandomLocalDateTimeGenerator)
			register(RandomInstantGenerator)
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
				var result = components.find { it.targetType.isAssignableFrom(targetType) }
				if(result is ConfigurableRandomGenerator<*> && configParams.isNotEmpty()) {
					result = result.configure(configParams)
				}
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
	object RandomByteGenerator : AbstractRandomGenerator<Byte>() {
		override fun generate(): Byte = Random.nextByte()
	}

	object RandomShortGenerator : AbstractRandomGenerator<Short>() {
		override fun generate(): Short = Random.nextShort()
	}

	object RandomIntGenerator : AbstractRandomGenerator<Int>() {
		override fun generate(): Int = Random.nextInt()
	}

	object RandomLongGenerator : AbstractRandomGenerator<Long>() {
		override fun generate(): Long = Random.nextLong()
	}

	object RandomFloatGenerator : AbstractRandomGenerator<Float>() {
		override fun generate(): Float = Random.nextFloat()
	}

	object RandomDoubleGenerator : AbstractRandomGenerator<Double>() {
		override fun generate(): Double = Random.nextDouble()
	}

	object RandomBigIntegerGenerator : AbstractRandomGenerator<BigInteger>() {
		override fun generate(): BigInteger = Random.nextBigInteger()
	}

	object RandomBigDecimalGenerator : AbstractRandomGenerator<BigDecimal>() {
		override fun generate(): BigDecimal = Random.nextBigDecimal()
	}

	@ExperimentalUnsignedTypes
	object RandomUIntGenerator : AbstractRandomGenerator<UInt>() {
		override fun generate(): UInt = Random.nextUInt()
	}

	@ExperimentalUnsignedTypes
	object RandomULongGenerator : AbstractRandomGenerator<ULong>() {
		override fun generate(): ULong = Random.nextULong()
	}

	object RandomCharGenerator : AbstractRandomGenerator<Char>() {
		override fun generate(): Char = Random.nextChar()
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
		ConfigParam("maxLength", "Int", "<null>"),
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
			}
			if(source.isEmpty()) throw IllegalArgumentException("Config param 'source' cannot be null or empty.")
			return buildString {
				val length = when {
					length != 0 -> length
					maxLength != 0 -> Random.nextInt(minLength, maxLength)
					else -> Random.nextInt(minLength)
				}
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
			if(min == null) throw IllegalArgumentException("Config param 'min' cannot be null.")
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
		companion object Default: RandomLocalDateTimeGenerator()

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
		companion object Default:RandomInstantGenerator()

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
	//endregion
}
