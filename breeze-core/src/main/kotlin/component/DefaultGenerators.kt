// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.model.*
import java.lang.reflect.*
import java.math.*
import java.nio.charset.*
import java.time.*
import java.util.*
import java.util.concurrent.atomic.*
import java.util.regex.*
import java.util.stream.*

@Suppress("UNCHECKED_CAST")
object DefaultGenerators : ComponentRegistry<DefaultGenerator<*>>() {
	//region Implementations
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

	object DefaultCharGenerator : AbstractDefaultGenerator<Char>() {
		private const val value: Char = '\u0000'
		override fun generate(): Char = value
	}

	object DefaultBooleanGenerator : AbstractDefaultGenerator<Boolean>() {
		private const val value: Boolean = false
		override fun generate(): Boolean = value
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

	@ExperimentalUnsignedTypes
	object DefaultULongGenerator : AbstractDefaultGenerator<ULong>() {
		private val value: ULong = 0.toULong()
		override fun generate(): ULong = value
	}

	object DefaultAtomicIntegerGenerator : AbstractDefaultGenerator<AtomicInteger>() {
		private val value: AtomicInteger = AtomicInteger(0)
		override fun generate(): AtomicInteger = value
	}

	object DefaultAtomicLongGenerator : AbstractDefaultGenerator<AtomicLong>() {
		private val value: AtomicLong = AtomicLong(0)
		override fun generate(): AtomicLong = value
	}

	object DefaultAtomicBooleanGenerator : AbstractDefaultGenerator<AtomicBoolean>() {
		private val value: AtomicBoolean = AtomicBoolean(false)
		override fun generate(): AtomicBoolean = value
	}

	object DefaultStringGenerator : AbstractDefaultGenerator<String>() {
		private const val value: String = ""
		override fun generate(): String = value
	}

	object DefaultRegexGenerator : AbstractDefaultGenerator<Regex>() {
		private val value: Regex = "".toRegex()
		override fun generate(): Regex = value
	}

	object DefaultPatternGenerator : AbstractDefaultGenerator<Pattern>() {
		private val value: Pattern = Pattern.compile("")
		override fun generate(): Pattern = value
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

	object DefaultEnumGenerator : AbstractDefaultGenerator<Enum<*>>(), GenericDefaultGenerator<Enum<*>> {
		override fun generate(targetType: Type): Enum<*> {
			val enumClass = inferEnumClass(targetType)
			if(enumClass == Enum::class.java) throw IllegalArgumentException("Cannot get actual enum class.")
			val enumValues = enumValuesCache.getOrPut(enumClass) {
				enumClass.enumConstants?.toList() ?: emptyList()
			}
			return enumValues.firstOrNull() ?: throw IllegalArgumentException("Enum class '$targetType' has no enum constants.")
		}
	}

	object DefaultIteratorGenerator : AbstractDefaultGenerator<Iterator<*>>() {
		private val value = EmptyIterator
		override fun generate(): Iterator<*> = value
	}

	object DefaultMutableIteratorGenerator : AbstractDefaultGenerator<MutableIterator<*>>() {
		private val value = EmptyMutableIterator
		override fun generate(): MutableIterator<*> = value
	}

	object DefaultIterableGenerator : AbstractDefaultGenerator<Iterable<*>>() {
		private val value = EmptyIterable
		override fun generate(): Iterable<*> = value
	}

	object DefaultMutableIterableGenerator : AbstractDefaultGenerator<MutableIterable<*>>() {
		private val value = EmptyMutableIterable
		override fun generate(): MutableIterable<*> = value
	}

	object DefaultCollectionGenerator : AbstractDefaultGenerator<Collection<*>>() {
		private val value = emptySet<Any?>()
		override fun generate(): Collection<*> = value
	}

	object DefaultMutableCollectionGenerator : AbstractDefaultGenerator<MutableCollection<*>>() {
		override fun generate(): MutableCollection<*> = mutableSetOf<Any?>()
	}

	object DefaultListGenerator : AbstractDefaultGenerator<List<*>>() {
		private val value = emptyList<Any?>()
		override fun generate(): List<*> = value
	}

	object DefaultMutableListGenerator : AbstractDefaultGenerator<MutableList<*>>() {
		override fun generate(): MutableList<*> = mutableListOf<Any?>()
	}

	object DefaultSetGenerator : AbstractDefaultGenerator<Set<*>>() {
		private val value = emptySet<Any?>()
		override fun generate(): Set<*> = value
	}

	object DefaultMutableSetGenerator : AbstractDefaultGenerator<MutableSet<*>>() {
		override fun generate(): MutableSet<*> = mutableSetOf<Any?>()
	}

	object DefaultIntRangeGenerator : AbstractDefaultGenerator<IntRange>() {
		private val value = IntRange.EMPTY
		override fun generate(): IntRange = value
	}

	object DefaultLongRangeGenerator : AbstractDefaultGenerator<LongRange>() {
		private val value = LongRange.EMPTY
		override fun generate(): LongRange = value
	}

	object DefaultCharRangeGenerator : AbstractDefaultGenerator<CharRange>() {
		private val value = CharRange.EMPTY
		override fun generate(): CharRange = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUIntRangeGenerator : AbstractDefaultGenerator<UIntRange>() {
		private val value = UIntRange.EMPTY
		override fun generate(): UIntRange = value
	}

	@ExperimentalUnsignedTypes
	object DefaultULongRangeGenerator : AbstractDefaultGenerator<ULongRange>() {
		private val value = ULongRange.EMPTY
		override fun generate(): ULongRange = value
	}

	object DefaultMapGenerator : AbstractDefaultGenerator<Map<*, *>>() {
		private val value = emptyMap<Any?, Any?>()
		override fun generate(): Map<*, *> = value
	}

	object DefaultMutableMapGenerator : AbstractDefaultGenerator<MutableMap<*, *>>() {
		override fun generate(): MutableMap<*, *> = mutableMapOf<Any?, Any?>()
	}

	object DefaultSequenceGenerator : AbstractDefaultGenerator<Sequence<*>>() {
		private val value = sequenceOf<Any?>()
		override fun generate(): Sequence<*> = value
	}

	object DefaultStreamGenerator : AbstractDefaultGenerator<Stream<*>>() {
		private val value = Stream.empty<Any?>()
		override fun generate(): Stream<*> = value
	}
	//endregion

	/**
	 * 是否使用回退策略。默认不使用。
	 *
	 * 如果使用回退策略且找不到匹配的默认值生成器，则尝试调用目标类型的无参构造方法生成默认值。
	 */
	var useFallbackStrategy = false

	@OptIn(ExperimentalUnsignedTypes::class)
	override fun registerDefault() {
		register(DefaultByteGenerator)
		register(DefaultShortGenerator)
		register(DefaultIntGenerator)
		register(DefaultLongGenerator)
		register(DefaultFloatGenerator)
		register(DefaultDoubleGenerator)
		register(DefaultCharGenerator)
		register(DefaultBooleanGenerator)
		register(DefaultBigIntegerGenerator)
		register(DefaultBigDecimalGenerator)
		register(DefaultUByteGenerator)
		register(DefaultUShortGenerator)
		register(DefaultUIntGenerator)
		register(DefaultULongGenerator)
		register(DefaultAtomicIntegerGenerator)
		register(DefaultAtomicLongGenerator)
		register(DefaultAtomicBooleanGenerator)
		register(DefaultStringGenerator)
		register(DefaultRegexGenerator)
		register(DefaultPatternGenerator)
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
		register(DefaultEnumGenerator)
		register(DefaultIteratorGenerator)
		register(DefaultMutableIteratorGenerator)
		register(DefaultIterableGenerator)
		register(DefaultMutableIterableGenerator)
		register(DefaultCollectionGenerator)
		register(DefaultMutableCollectionGenerator)
		register(DefaultListGenerator)
		register(DefaultMutableListGenerator)
		register(DefaultSetGenerator)
		register(DefaultMutableSetGenerator)
		register(DefaultIntRangeGenerator)
		register(DefaultLongRangeGenerator)
		register(DefaultCharRangeGenerator)
		register(DefaultUIntRangeGenerator)
		register(DefaultULongRangeGenerator)
		register(DefaultMapGenerator)
		register(DefaultMutableMapGenerator)
		register(DefaultSequenceGenerator)
		register(DefaultStreamGenerator)
	}

	/**
	 * 根据指定的目标类型和配置参数，从缓存中得到默认值生成器。如果没有，则创建并放入。
	 */
	fun <T, C : DefaultGenerator<T>> get(targetType: Class<T>, componentParams: Map<String, Any?>, defaultValue: () -> C): C {
		return components.getOrPut(inferKey(targetType, componentParams), defaultValue) as C
	}

	/**
	 * 根据可选的配置参数，生成指定类型的默认值。
	 */
	inline fun <reified T> generate(componentParams: Map<String, Any?> = emptyMap()): T {
		return generate(javaTypeOf<T>(), componentParams)
	}

	/**
	 * 根据可选的配置参数，生成指定类型的默认值。
	 */
	fun <T> generate(targetType: Class<T>, componentParams: Map<String, Any?> = emptyMap()): T {
		return doGenerate(targetType, componentParams)
	}

	/**
	 * 根据可选的配置参数，生成指定类型的默认值。
	 */
	fun <T> generate(targetType: Type, componentParams: Map<String, Any?> = emptyMap()): T {
		return doGenerate(targetType, componentParams)
	}


	private fun <T> doGenerate(targetType: Type, componentParams: Map<String, Any?>): T {
		val targetClass = inferClass(targetType)
		//遍历已注册的默认值生成器，如果匹配目标类型，则尝试用它生成默认值，并加入缓存
		val key = inferKey(targetClass, componentParams)
		val defaultGenerator = components.getOrPut(key) {
			val result = infer(targetClass, componentParams)
			if(result == null) {
				if(useFallbackStrategy) {
					val fallback = fallbackGenerate(targetClass)
					if(fallback != null) return fallback as T
				}
				throw IllegalArgumentException("No suitable default generator found for target type '$targetType'.")
			}
			result
		}
		return when(defaultGenerator) {
			is GenericDefaultGenerator<*> -> defaultGenerator.generate(targetType) as T
			else -> defaultGenerator.generate() as T
		}
	}

	private fun inferKey(targetType: Class<*>, componentParams: Map<String, Any?>): String {
		return if(componentParams.isEmpty()) targetType.name else "${targetType.name}@$componentParams"
	}

	private fun infer(targetType: Class<*>, componentParams: Map<String, Any?>): DefaultGenerator<*>? {
		var result = components.values.findLast { it.targetType.isAssignableFrom(targetType) } ?: return null
		if(result.componentParams.toString() != componentParams.toString()) {
			result = result.copy(componentParams)
		}
		return result
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
