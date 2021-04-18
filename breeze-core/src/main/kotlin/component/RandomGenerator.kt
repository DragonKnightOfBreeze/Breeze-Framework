// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import java.math.*
import java.util.concurrent.*
import kotlin.random.*

/**
 * 随机值生成器。
 *
 * 随机值生成器用于基于给定的参数生成随机值。
 */
interface RandomGenerator<T>: Component {
	/**
	 * 目标类型。
	 */
	val targetType: Class<T>

	/**
	 * 生成随机值。
	 */
	fun generate():T

	companion object Registry: AbstractComponentRegistry<RandomGenerator<*>>(){
		init {
			registerDefaultRandomGenerators()
		}

		private fun registerDefaultRandomGenerators(){
			register(RandomByteGenerator)
			register(RandomShortGenerator)
			register(RandomIntGenerator)
			register(RandomLongGenerator)
			register(RandomFloatGenerator)
			register(RandomDoubleGenerator)
			register(RandomBigIntegerGenerator)
			register(RandomBigDecimalGenerator)
		}

		val random: Random get() = Random

		init {
			random.toSingletonList()
		}

		/**
		 * 生成指定类型的随机值。
		 */
		inline fun <reified T> generate():T{
			return generate(T::class.java)
		}

		/**
		 * 生成指定类型的随机值。
		 */
		@Suppress("UNCHECKED_CAST")
		fun <T> generate(targetType: Class<T>): T {
			for(randomGenerator in components) {
				try{
					if(randomGenerator.targetType.isAssignableFrom(targetType)){
						return randomGenerator.generate() as T
					}
				}catch(e:Exception){
					continue
				}
			}
			throw IllegalArgumentException("No suitable random generator found for target type '$targetType'.")
		}
	}

	//region Default Generators
	object RandomByteGenerator:RandomGenerator<Byte>{
		override val targetType: Class<Byte> = Byte::class.javaObjectType

		override fun generate(): Byte {
			return random.nextByte()
		}
	}

	object RandomShortGenerator:RandomGenerator<Short>{
		override val targetType: Class<Short> = Short::class.javaObjectType

		override fun generate(): Short {
			return random.nextShort()
		}
	}

	object RandomIntGenerator:RandomGenerator<Int>{
		override val targetType: Class<Int> = Int::class.javaObjectType

		override fun generate(): Int {
			return random.nextInt()
		}
	}

	object RandomLongGenerator:RandomGenerator<Long>{
		override val targetType: Class<Long> = Long::class.javaObjectType

		override fun generate(): Long {
			return random.nextLong()
		}
	}

	object RandomFloatGenerator:RandomGenerator<Float>{
		override val targetType: Class<Float> = Float::class.javaObjectType

		override fun generate(): Float {
			return random.nextFloat()
		}
	}

	object RandomDoubleGenerator:RandomGenerator<Double>{
		override val targetType: Class<Double> =  Double::class.javaObjectType

		override fun generate(): Double {
			return random.nextDouble()
		}
	}

	object RandomBigIntegerGenerator:RandomGenerator<BigInteger>{
		override val targetType: Class<BigInteger> = BigInteger::class.java

		override fun generate(): BigInteger {
			return random.nextBigInteger()
		}
	}

	object RandomBigDecimalGenerator:RandomGenerator<BigDecimal>{
		override val targetType: Class<BigDecimal> = BigDecimal::class.java

		override fun generate(): BigDecimal {
			return random.nextBigDecimal()
		}
	}
	//endregion
}
