// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
import java.math.*
import kotlin.random.*

/**
 * 随机值生成器。
 *
 * 随机值生成器用于基于给定的参数生成随机值。
 */
interface RandomGenerator<T> : Component {
	/**
	 * 目标类型。
	 */
	val targetType: Class<T>

	/**
	 * 生成随机值。
	 */
	fun generate(): T

	companion object Registry : AbstractComponentRegistry<RandomGenerator<*>>() {
		init {
			registerDefaultRandomGenerators()
		}

		private fun registerDefaultRandomGenerators() {
			register(RandomByteGenerator)
			register(RandomShortGenerator)
			register(RandomIntGenerator)
			register(RandomLongGenerator)
			register(RandomFloatGenerator)
			register(RandomDoubleGenerator)
			register(RandomBigIntegerGenerator)
			register(RandomBigDecimalGenerator)
			register(RandomSourceStringGenerator)
			register(RandomLetterStringGenerator)
			register(RandomDigitStringGenerator)
			register(RandomWordStringGenerator)
			register(RandomLengthSourceStringGenerator)
			register(RandomLengthLetterStringGenerator)
			register(RandomLengthDigitStringGenerator)
			register(RandomLengthWordStringGenerator)
		}

		val random: Random get() = Random

		init {
			random.toSingletonList()
		}

		/**
		 * 生成指定类型的随机值。
		 */
		inline fun <reified T> generate(): T {
			return generate(T::class.java)
		}

		/**
		 * 生成指定类型的随机值。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> generate(targetType: Class<T>): T {
			for(randomGenerator in components) {
				try {
					if(randomGenerator.targetType.isAssignableFrom(targetType)) {
						return randomGenerator.generate() as T
					}
				} catch(e: Exception) {
					continue
				}
			}
			throw IllegalArgumentException("No suitable random generator found for target type '$targetType'.")
		}
	}

	//region Default Generators
	object RandomByteGenerator : RandomGenerator<Byte> {
		override val targetType: Class<Byte> = Byte::class.javaObjectType

		override fun generate(): Byte {
			return random.nextByte()
		}
	}

	object RandomShortGenerator : RandomGenerator<Short> {
		override val targetType: Class<Short> = Short::class.javaObjectType

		override fun generate(): Short {
			return random.nextShort()
		}
	}

	object RandomIntGenerator : RandomGenerator<Int> {
		override val targetType: Class<Int> = Int::class.javaObjectType

		override fun generate(): Int {
			return random.nextInt()
		}
	}

	object RandomLongGenerator : RandomGenerator<Long> {
		override val targetType: Class<Long> = Long::class.javaObjectType

		override fun generate(): Long {
			return random.nextLong()
		}
	}

	object RandomFloatGenerator : RandomGenerator<Float> {
		override val targetType: Class<Float> = Float::class.javaObjectType

		override fun generate(): Float {
			return random.nextFloat()
		}
	}

	object RandomDoubleGenerator : RandomGenerator<Double> {
		override val targetType: Class<Double> = Double::class.javaObjectType

		override fun generate(): Double {
			return random.nextDouble()
		}
	}

	object RandomBigIntegerGenerator : RandomGenerator<BigInteger> {
		override val targetType: Class<BigInteger> = BigInteger::class.java

		override fun generate(): BigInteger {
			return random.nextBigInteger()
		}
	}

	object RandomBigDecimalGenerator : RandomGenerator<BigDecimal> {
		override val targetType: Class<BigDecimal> = BigDecimal::class.java

		override fun generate(): BigDecimal {
			return random.nextBigDecimal()
		}
	}

	@ConfigurableParams(
		ConfigurableParam("length","Int","0", comment= "Length of generated string"),
		ConfigurableParam("source","String","''",comment = "Source string to generate chars")
	)
	open class RandomSourceStringGenerator : RandomGenerator<String>, Configurable<RandomSourceStringGenerator> {
		companion object Default: RandomSourceStringGenerator()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<String> = String::class.java

		var length: Int = 0
			protected set
		var source: String = ""
			protected set

		override fun configure(params: Map<String, Any?>) {
			params["length"]?.convertOrNull<Int>()?.let { length = it }
			params["source"]?.toString()?.let { source = it }
		}

		override fun copy(params: Map<String, Any?>): RandomSourceStringGenerator {
			return RandomSourceStringGenerator().apply { configure(params) }
		}

		override fun generate(): String {
			if(length <= 0) throw IllegalArgumentException("Param 'length' must be positive.")
			if(source.isEmpty()) throw IllegalArgumentException("Param 'source' must not be empty.")
			return buildString {
				val length = length
				val source = source
				repeat(length) {
					append(random.nextElement(source))
				}
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("length","Int","0", comment= "Length of generated string")
	)
	object RandomLetterStringGenerator : RandomSourceStringGenerator() {
		init {
			source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		}
	}

	@ConfigurableParams(
		ConfigurableParam("length","Int","0", comment= "Length of generated string")
	)
	object RandomDigitStringGenerator : RandomSourceStringGenerator() {
		init {
			source = "0123456789"
		}
	}

	@ConfigurableParams(
		ConfigurableParam("length","Int","0", comment= "Length of generated string")
	)
	object RandomWordStringGenerator : RandomSourceStringGenerator() {
		init {
			source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		}
	}

	@ConfigurableParams(
		ConfigurableParam("minLength","Int","0", comment= "Min length of generated string"),
		ConfigurableParam("maxLength","Int","0", comment= "Max length of generated string"),
		ConfigurableParam("source","String","''",comment = "Source string to generate chars")
	)
	open class RandomLengthSourceStringGenerator : RandomGenerator<String>, Configurable<RandomSourceStringGenerator> {
		companion object Default: RandomLengthSourceStringGenerator()

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<String> = String::class.java

		var minLength: Int = 0
			protected set
		var maxLength: Int? = null
			protected set
		var source: String = ""
			protected set

		override fun configure(params: Map<String, Any?>) {
			params["minLength"]?.convertOrNull<Int>()?.let { minLength = it }
			params["maxLength"]?.convertOrNull<Int>()?.let { maxLength = it }
			params["source"]?.toString()?.let { source = it }
		}

		override fun copy(params: Map<String, Any?>): RandomSourceStringGenerator {
			return RandomSourceStringGenerator().apply { configure(params) }
		}

		override fun generate(): String {
			if(minLength <= 0) throw IllegalArgumentException("Param 'minLength' must be positive.")
			if(source.isEmpty()) throw IllegalArgumentException("Param 'source' must not be empty.")
			return buildString {
				val minLength = minLength
				val maxLength = maxLength
				val source = source
				val count = if(maxLength == null) random.nextInt(minLength) else random.nextInt(minLength, maxLength)
				repeat(count) {
					append(random.nextElement(source))
				}
			}
		}
	}

	@ConfigurableParams(
		ConfigurableParam("minLength","Int","0", comment= "Min length of generated string"),
		ConfigurableParam("maxLength","Int","0", comment= "Max length of generated string")
	)
	object RandomLengthLetterStringGenerator : RandomLengthSourceStringGenerator() {
		init {
			source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		}
	}

	@ConfigurableParams(
		ConfigurableParam("minLength","Int","0", comment= "Min length of generated string"),
		ConfigurableParam("maxLength","Int","0", comment= "Max length of generated string")
	)
	object RandomLengthDigitStringGenerator : RandomLengthSourceStringGenerator() {
		init {
			source = "0123456789"
		}
	}

	@ConfigurableParams(
		ConfigurableParam("minLength","Int","0", comment= "Min length of generated string"),
		ConfigurableParam("maxLength","Int","0", comment= "Max length of generated string")
	)
	object RandomLengthWordStringGenerator : RandomLengthSourceStringGenerator() {
		init {
			source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		}
	}
	//endregion
}
