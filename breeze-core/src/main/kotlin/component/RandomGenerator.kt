// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.annotation.*
import icu.windea.breezeframework.core.extension.*
import icu.windea.breezeframework.core.model.*
import java.math.*
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
		}

		private val componentMap: MutableMap<String, RandomGenerator<*>> = ConcurrentHashMap()
		private val random: Random get() = Random

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
			val paramsString = if(configParams.isNotEmpty()) configParams.toString() else ""
			val key = if(configParams.isNotEmpty()) targetType.name + '@' + paramsString else targetType.name
			val randomGenerator = componentMap.getOrPut(key) {
				val result = components.find {
					val sameConfig =
						if(it is Configurable<*> && it.configParams.isNotEmpty()) paramsString == it.configParams.toString() else true
					it.targetType.isAssignableFrom(targetType) && sameConfig
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
	abstract class AbstractRandomGenerator<T> : RandomGenerator<T> {
		override val targetType: Class<T> get() = inferTargetType(this, RandomGenerator::class.java)

		override fun equals(other: Any?): Boolean {
			if(this === other) return true
			if(other == null || javaClass != other.javaClass) return false
			return when {
				this is Configurable<*> && other is Configurable<*> -> configParams.toString() == other.configParams.toString()
				else -> true
			}
		}

		override fun hashCode(): Int {
			return when {
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

	object RandomByteGenerator : AbstractRandomGenerator<Byte>() {
		override fun generate(): Byte = random.nextByte()
	}

	object RandomShortGenerator : AbstractRandomGenerator<Short>() {
		override fun generate(): Short = random.nextShort()
	}

	object RandomIntGenerator : AbstractRandomGenerator<Int>() {
		override fun generate(): Int = random.nextInt()
	}

	object RandomLongGenerator : AbstractRandomGenerator<Long>() {
		override fun generate(): Long = random.nextLong()
	}

	object RandomFloatGenerator : AbstractRandomGenerator<Float>() {
		override fun generate(): Float = random.nextFloat()
	}

	object RandomDoubleGenerator : AbstractRandomGenerator<Double>() {
		override fun generate(): Double = random.nextDouble()
	}

	object RandomBigIntegerGenerator : AbstractRandomGenerator<BigInteger>() {
		override fun generate(): BigInteger = random.nextBigInteger()
	}

	object RandomBigDecimalGenerator : AbstractRandomGenerator<BigDecimal>() {
		override fun generate(): BigDecimal = random.nextBigDecimal()
	}

	@ExperimentalUnsignedTypes
	object RandomUIntGenerator : AbstractRandomGenerator<UInt>() {
		override fun generate(): UInt = random.nextUInt()
	}

	@ExperimentalUnsignedTypes
	object RandomULongGenerator : AbstractRandomGenerator<ULong>() {
		override fun generate(): ULong = random.nextULong()
	}

	object RandomCharGenerator : AbstractRandomGenerator<Char>() {
		override fun generate(): Char = random.nextChar()
	}

	object RandomBooleanGenerator : AbstractRandomGenerator<Boolean>() {
		override fun generate(): Boolean = random.nextBoolean()
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
		ConfigParam("maxLength", "Int", "0"),
		ConfigParam("source", "String", "")
	)
	open class RandomStringGenerator(
		final override val configParams: Map<String, Any?> = emptyMap()
	) : AbstractRandomGenerator<String>(), Configurable<RandomStringGenerator> {
		companion object Default : RandomStringGenerator()

		val length: Int = configParams.get("length")?.convertOrNull()?:0
		val minLength: Int = configParams.get("minLength")?.convertOrNull()?:0
		val maxLength: Int = configParams.get("maxLength")?.convertOrNull()?:0
		val source: String = configParams.get("source")?.convertOrNull()?:""

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
			if(length < 0) throw IllegalArgumentException("Param 'length' must not be negative.")
			if(length == 0) {
				if(minLength < 0) throw IllegalArgumentException("Param 'minLength' must not be negative.")
				if(maxLength < 0) throw IllegalArgumentException("Param 'maxLength' must not be negative.")
			}
			if(source.isEmpty()) throw IllegalArgumentException("Param 'source' must not be empty.")
			return buildString {
				val length = when {
					length != 0 -> length
					maxLength != 0 -> random.nextInt(minLength, maxLength)
					else -> random.nextInt(minLength)
				}
				repeat(length) {
					append(random.nextElement(source))
				}
			}
		}
	}

	object RandomUuidGenerator : AbstractRandomGenerator<UUID>() {
		override fun generate(): UUID {
			return Random.nextUuid()
		}
	}
	//endregion
}
