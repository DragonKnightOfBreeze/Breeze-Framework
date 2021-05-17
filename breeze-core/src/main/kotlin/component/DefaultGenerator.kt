// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.inferTargetType
import icu.windea.breezeframework.core.model.*
import java.math.*
import java.time.*
import java.util.*
import java.util.concurrent.*

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
			register(DefaultDateGenerator)
			register(DefaultLocalDateGenerator)
			register(DefaultLocalTimeGenerator)
			register(DefaultLocalDateTimeGenerator)
			register(DefaultInstantGenerator)
			register(DefaultListGenerator)
			register(DefaultSetGenerator)
			register(DefaultSequenceGenerator)
			register(DefaultMapGenerator)
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
			val paramsString = if(configParams.isEmpty()) "" else configParams.toString()
			val key = if(configParams.isEmpty()) targetType.name else targetType.name + '@' + paramsString
			val defaultGenerator = componentMap.getOrPut(key){
				val result = components.find {
					val sameConfig = it !is Configurable<*> || it.configParams.isEmpty() || paramsString == it.configParams.toString()
					it.targetType.isAssignableFrom(targetType)  && sameConfig
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
	abstract class AbstractDefaultGenerator<T> : DefaultGenerator<T> {
		override val targetType: Class<T> get() = inferTargetType(this, DefaultGenerator::class.java)

		override fun equals(other: Any?): Boolean {
			if(this === other) return true
			if(other == null || javaClass != other.javaClass) return false
			return when{
				this is Configurable<*> && other is Configurable<*> -> configParams.toString() == other.configParams.toString()
				else -> true
			}
		}

		override fun hashCode(): Int {
			return when{
				this is Configurable<*> -> configParams.toString().hashCode()
				else -> 0
			}
		}

		override fun toString(): String {
			return when {
				this is Configurable<*> -> targetType.name + '@' + configParams.toString()
				else -> targetType.name
			}
		}
	}

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
	//endregion
}
