// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.model.*
import java.math.*
import java.nio.charset.*
import java.time.*
import java.util.*
import java.util.concurrent.*
import java.util.stream.*

/**
 * 默认值生成器。
 *
 * 默认值生成器用于生成默认值。
 */
interface DefaultGenerator<T> : Component {
	/**目标类型。*/
	val targetType: Class<T>

	/**
	 * 生成默认值。
	 */
	fun generate(): T

	companion object Registry : AbstractComponentRegistry<DefaultGenerator<*>>() {
		@OptIn(ExperimentalUnsignedTypes::class)
		override fun registerDefault() {
			register(DefaultByteGenerator)
			register(DefaultShortGenerator)
			register(DefaultIntGenerator)
			register(DefaultLongGenerator)
			register(DefaultFloatGenerator)
			register(DefaultDoubleGenerator)
			register(DefaultBigIntegerGenerator)
			register(DefaultBigDecimalGenerator)
			register(DefaultUByteGenerator)
			register(DefaultUShortGenerator)
			register(DefaultUIntGenerator)
			register(DefaultCharGenerator)
			register(DefaultBooleanGenerator)
			register(DefaultStringGenerator)
			register(DefaultCharsetGenerator)
			register(DefaultLocaleGenerator)
			register(DefaultTimeZoneGenerator)
			register(DefaultZoneIdGenerator)
			register(DefaultDateGenerator)
			register(DefaultLocalDateGenerator)
			register(DefaultLocalTimeGenerator)
			register(DefaultLocalDateTimeGenerator)
			register(DefaultInstantGenerator)
			register(DefaultDurationGenerator)
			register(DefaultPeriodGenerator)
			register(DefaultArrayGenerator)
			register(DefaultByteArrayGenerator)
			register(DefaultShortArrayGenerator)
			register(DefaultIntArrayGenerator)
			register(DefaultLongArrayGenerator)
			register(DefaultFloatArrayGenerator)
			register(DefaultDoubleArrayGenerator)
			register(DefaultCharArrayGenerator)
			register(DefaultBooleanArrayGenerator)
			register(DefaultIteratorGenerator)
			register(DefaultIterableGenerator)
			register(DefaultCollectionGenerator)
			register(DefaultListGenerator)
			register(DefaultSetGenerator)
			register(DefaultSequenceGenerator)
			register(DefaultMapGenerator)
			register(DefaultStreamGenerator)
			register(DefaultIntRangeGenerator)
			register(DefaultLongRangeGenerator)
			register(DefaultCharRangeGenerator)
			register(DefaultUIntRangeGenerator)
			register(DefaultULongRangeGenerator)
		}

		private val componentMap: MutableMap<String, DefaultGenerator<*>> = ConcurrentHashMap()

		/**
		 * 是否使用回退策略。默认不使用。
		 * 如果使用回退策略且找不到匹配的默认值生成器，则尝试调用目标类型的无参构造方法生成默认值。
		 */
		var useFallbackStrategy = false

		/**
		 * 根据可选的配置参数，生成指定类型的默认值。
		 */
		inline fun <reified T> generate(configParams: Map<String, Any?> = emptyMap()): T {
			return generate(T::class.java, configParams)
		}

		/**
		 * 根据可选的配置参数，生成指定类型的默认值。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> generate(targetType: Class<T>, configParams: Map<String, Any?> = emptyMap()): T {
			//遍历已注册的默认值生成器，如果匹配目标类型，则尝试用它生成默认值，并加入缓存
			val key = if(configParams.isEmpty()) targetType.name else targetType.name + '@' + configParams.toString()
			val defaultGenerator = componentMap.getOrPut(key) {
				var result = components.find { it.targetType.isAssignableFrom(targetType) }
				if(result is ConfigurableDefaultGenerator<*> && configParams.isNotEmpty()) {
					result = result.configure(configParams)
				}
				if(result == null) {
					if(useFallbackStrategy) {
						val fallback = fallbackGenerate(targetType)
						if(fallback != null) return fallback
					}
					throw IllegalArgumentException("No suitable default generator found for target type '$targetType'.")
				}
				result
			}
			return defaultGenerator.generate() as T
		}

		private fun <T> fallbackGenerate(targetType: Class<T>): T? {
			try {
				//尝试调用目标类型的无参构造方法生成默认值
				val constructor = targetType.getConstructor()
				constructor.isAccessible = true
				return constructor.newInstance()
			} catch(e: Exception) {
				return null
			}
		}
	}

	//region Default Generators
	object DefaultByteGenerator : AbstractDefaultGenerator<Byte>() {
		private const val value: Byte = 0
		override fun generate(): Byte = value
	}

	object DefaultShortGenerator : AbstractDefaultGenerator<Short>() {
		private const val value: Short = 0
		override fun generate(): Short = value
	}

	object DefaultIntGenerator : AbstractDefaultGenerator<Int>() {
		private const val value: Int = 0
		override fun generate(): Int = value
	}

	object DefaultLongGenerator : AbstractDefaultGenerator<Long>() {
		private const val value: Long = 0
		override fun generate(): Long = value
	}

	object DefaultFloatGenerator : AbstractDefaultGenerator<Float>() {
		private const val value: Float = 0f
		override fun generate(): Float = value
	}

	object DefaultDoubleGenerator : AbstractDefaultGenerator<Double>() {
		private const val value: Double = 0.0
		override fun generate(): Double = value
	}

	object DefaultBigIntegerGenerator : AbstractDefaultGenerator<BigInteger>() {
		private val value: BigInteger = BigInteger.ZERO
		override fun generate(): BigInteger = value
	}

	object DefaultBigDecimalGenerator : AbstractDefaultGenerator<BigDecimal>() {
		private val value: BigDecimal = BigDecimal.ZERO
		override fun generate(): BigDecimal = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUByteGenerator : AbstractDefaultGenerator<UByte>() {
		private val value: UByte = 0.toUByte()
		override fun generate(): UByte = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUShortGenerator : AbstractDefaultGenerator<UShort>() {
		private val value: UShort = 0.toUShort()
		override fun generate(): UShort = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUIntGenerator : AbstractDefaultGenerator<UInt>() {
		private val value: UInt = 0.toUInt()
		override fun generate(): UInt = value
	}

	object DefaultCharGenerator : AbstractDefaultGenerator<Char>() {
		private const val value: Char = '\u0000'
		override fun generate(): Char = value
	}

	object DefaultBooleanGenerator : AbstractDefaultGenerator<Boolean>() {
		private const val value: Boolean = false
		override fun generate(): Boolean = value
	}

	object DefaultStringGenerator : AbstractDefaultGenerator<String>() {
		private const val value: String = ""
		override fun generate(): String = value
	}

	object DefaultCharsetGenerator : AbstractDefaultGenerator<Charset>() {
		override fun generate(): Charset = Charset.defaultCharset()
	}

	object DefaultLocaleGenerator : AbstractDefaultGenerator<Locale>() {
		override fun generate(): Locale = Locale.getDefault()
	}

	object DefaultTimeZoneGenerator : AbstractDefaultGenerator<TimeZone>() {
		override fun generate(): TimeZone = TimeZone.getDefault()
	}

	object DefaultZoneIdGenerator : AbstractDefaultGenerator<ZoneId>() {
		override fun generate(): ZoneId = TimeZone.getDefault().toZoneId()
	}

	object DefaultDateGenerator : AbstractDefaultGenerator<Date>() {
		override fun generate(): Date = Date()
	}

	object DefaultLocalDateGenerator : AbstractDefaultGenerator<LocalDate>() {
		override fun generate(): LocalDate = LocalDate.now()
	}

	object DefaultLocalTimeGenerator : AbstractDefaultGenerator<LocalTime>() {
		override fun generate(): LocalTime = LocalTime.now()
	}

	object DefaultLocalDateTimeGenerator : AbstractDefaultGenerator<LocalDateTime>() {
		override fun generate(): LocalDateTime = LocalDateTime.now()
	}

	object DefaultInstantGenerator : AbstractDefaultGenerator<Instant>() {
		override fun generate(): Instant = Instant.now()
	}

	object DefaultDurationGenerator : AbstractDefaultGenerator<Duration>() {
		override fun generate(): Duration = Duration.ZERO
	}

	object DefaultPeriodGenerator : AbstractDefaultGenerator<Period>() {
		override fun generate(): Period = Period.ZERO
	}

	object DefaultArrayGenerator : AbstractDefaultGenerator<Array<*>>() {
		override fun generate(): Array<*> = emptyArray<Any?>()
	}

	object DefaultByteArrayGenerator : AbstractDefaultGenerator<ByteArray>() {
		private val value = byteArrayOf()
		override fun generate(): ByteArray = value
	}

	object DefaultShortArrayGenerator : AbstractDefaultGenerator<ShortArray>() {
		private val value = shortArrayOf()
		override fun generate(): ShortArray = value
	}

	object DefaultIntArrayGenerator : AbstractDefaultGenerator<IntArray>() {
		private val value = intArrayOf()
		override fun generate(): IntArray = value
	}

	object DefaultLongArrayGenerator : AbstractDefaultGenerator<LongArray>() {
		private val value = longArrayOf()
		override fun generate(): LongArray = value
	}

	object DefaultFloatArrayGenerator : AbstractDefaultGenerator<FloatArray>() {
		private val value = floatArrayOf()
		override fun generate(): FloatArray = value
	}

	object DefaultDoubleArrayGenerator : AbstractDefaultGenerator<DoubleArray>() {
		private val value = doubleArrayOf()
		override fun generate(): DoubleArray = value
	}

	object DefaultCharArrayGenerator : AbstractDefaultGenerator<CharArray>() {
		private val value = charArrayOf()
		override fun generate(): CharArray = value
	}

	object DefaultBooleanArrayGenerator : AbstractDefaultGenerator<BooleanArray>() {
		private val value = booleanArrayOf()
		override fun generate(): BooleanArray = value
	}

	object DefaultIteratorGenerator : AbstractDefaultGenerator<Iterator<*>>() {
		override fun generate(): Iterator<*> = EmptyIterator
	}

	object DefaultIterableGenerator : AbstractDefaultGenerator<Iterable<*>>() {
		override fun generate(): Iterable<*> = EmptyIterable
	}

	object DefaultCollectionGenerator : AbstractDefaultGenerator<Collection<*>>() {
		override fun generate(): Collection<*> {
			return emptySet<Any?>()
		}
	}

	object DefaultListGenerator : AbstractDefaultGenerator<List<*>>() {
		override fun generate(): List<*> = emptyList<Any?>()
	}

	object DefaultSetGenerator : AbstractDefaultGenerator<Set<*>>() {
		override fun generate(): Set<*> = emptySet<Any?>()
	}

	object DefaultSequenceGenerator : AbstractDefaultGenerator<Sequence<*>>() {
		override fun generate(): Sequence<*> = sequenceOf<Any?>()
	}

	object DefaultMapGenerator : AbstractDefaultGenerator<Map<*, *>>() {
		override fun generate(): Map<*, *> = emptyMap<Any?, Any?>()
	}

	object DefaultStreamGenerator : AbstractDefaultGenerator<Stream<*>>() {
		override fun generate(): Stream<*> = Stream.empty<Any?>()
	}

	object DefaultIntRangeGenerator : AbstractDefaultGenerator<IntRange>() {
		override fun generate(): IntRange = IntRange.EMPTY
	}

	object DefaultLongRangeGenerator : AbstractDefaultGenerator<LongRange>() {
		override fun generate(): LongRange = LongRange.EMPTY
	}

	object DefaultCharRangeGenerator : AbstractDefaultGenerator<CharRange>() {
		override fun generate(): CharRange = CharRange.EMPTY
	}

	@ExperimentalUnsignedTypes
	object DefaultUIntRangeGenerator : AbstractDefaultGenerator<UIntRange>() {
		override fun generate(): UIntRange = UIntRange.EMPTY
	}

	@ExperimentalUnsignedTypes
	object DefaultULongRangeGenerator : AbstractDefaultGenerator<ULongRange>() {
		override fun generate(): ULongRange = ULongRange.EMPTY
	}
	//endregion
}
