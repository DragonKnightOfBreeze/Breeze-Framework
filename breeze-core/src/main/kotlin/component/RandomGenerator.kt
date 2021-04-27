// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import com.windea.breezeframework.core.annotation.*
import com.windea.breezeframework.core.extension.*
import com.windea.breezeframework.core.model.*
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
	/**
	 * 目标类型。
	 */
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

		private val componentCache: MutableMap<Class<*>,RandomGenerator<*>> = ConcurrentHashMap()

		val random: Random get() = Random

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
		@JvmStatic
		fun <T> generate(targetType: Class<T>): T {
			//遍历已注册的随机数生成器，如果匹配目标类型，则尝试用它生成随机值
			//如果成功则加入缓存，如果失败则抛出异常
			val randomGenerator = componentCache.getOrPut(targetType){
				for(randomGenerator in components) {
					if(randomGenerator.targetType.isAssignableFrom(targetType)) {
						return@getOrPut randomGenerator
					}
				}
				throw IllegalArgumentException("No suitable random generator found for target type '$targetType'.")
			}
			return randomGenerator.generate() as T
		}
	}

	//region Random Generators
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

	@ExperimentalUnsignedTypes
	object RandomUIntGenerator:RandomGenerator<UInt>{
		override val targetType: Class<UInt> = UInt::class.java

		override fun generate(): UInt {
			return random.nextUInt()
		}
	}

	@ExperimentalUnsignedTypes
	object RandomULongGenerator:RandomGenerator<ULong>{
		override val targetType: Class<ULong> = ULong::class.java

		override fun generate(): ULong {
			return random.nextULong()
		}
	}

	object RandomCharGenerator:RandomGenerator<Char>{
		override val targetType: Class<Char> = Char::class.java

		override fun generate(): Char {
			return random.nextChar()
		}
	}

	object RandomBooleanGenerator:RandomGenerator<Boolean>{
		override val targetType: Class<Boolean> = Boolean::class.java

		override fun generate(): Boolean {
			return random.nextBoolean()
		}
	}

	@ConfigurableParams(
		ConfigurableParam("length","Int","0", comment= "Length of generated string"),
		ConfigurableParam("minLength","Int","0", comment= "Min length of generated string"),
		ConfigurableParam("maxLength","Int","0", comment= "Max length of generated string"),
		ConfigurableParam("source","String","''",comment = "Source string to generate chars"),
	)
	open class RandomStringGenerator : RandomGenerator<String>, Configurable<RandomStringGenerator> {
		companion object Default: RandomStringGenerator(){
			const val letterSource = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
			const val numberSource = "0123456789"
			const val wordSource = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
		}

		override val configurableInfo: ConfigurableInfo = ConfigurableInfo()

		override val targetType: Class<String> = String::class.java

		var length: Int = 0
			protected set
		var minLength: Int = 0
			protected set
		var maxLength:Int = 0
			protected set
		var source: String = ""
			protected set

		override fun configure(params: Map<String, Any?>) {
			params["length"]?.convertOrNull<Int>()?.let { length = it }
			params["minLength"]?.convertOrNull<Int>()?.let { length = it }
			params["maxLength"]?.convertOrNull<Int>()?.let { length = it }
			params["source"]?.toString()?.let { source = it }
		}

		override fun copy(params: Map<String, Any?>): RandomStringGenerator {
			return RandomStringGenerator().apply { configure(params) }
		}

		override fun generate(): String {
			if(length < 0) throw IllegalArgumentException("Param 'length' must not be negative.")
			if(length == 0){
				if(minLength < 0) throw IllegalArgumentException("Param 'minLength' must not be negative.");
				if(maxLength < 0) throw IllegalArgumentException("Param 'maxLength' must not be negative.");
			}
			if(source.isEmpty()) throw IllegalArgumentException("Param 'source' must not be empty.")
			val length = when{
				length != 0 -> length
				maxLength != 0 -> random.nextInt(minLength,maxLength)
				else -> random.nextInt(minLength)
			}
			val source = source
			return buildString {
				repeat(length) {
					append(random.nextElement(source))
				}
			}
		}
	}

	object RandomUuidGenerator:RandomGenerator<UUID>{
		override val targetType: Class<UUID> = UUID::class.java

		override fun generate(): UUID {
			return Random.nextUuid()
		}
	}
	//endregion
}
