// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.component

import java.math.*
import java.time.*
import java.util.*
import java.util.concurrent.*

/**
 * 默认值生成器。
 *
 * 默认值生成器用于生成默认值。
 */
interface DefaultGenerator<T>:Component {
	/**
	 * 目标类型。
	 */
	val targetType: Class<T>

	/**
	 * 生成默认值。
	 */
	fun generate():T

	companion object Registry: AbstractComponentRegistry<DefaultGenerator<*>>(){
		@OptIn(ExperimentalUnsignedTypes::class)
		override fun registerDefault(){
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

		private val componentCache: MutableMap<Class<*>,DefaultGenerator<*>> = ConcurrentHashMap()

		/**
		 * 生成指定类型的默认值。
		 */
		inline fun <reified T> generate():T{
			return generate(T::class.java)
		}

		/**
		 * 生成指定类型的默认值。
		 */
		@Suppress("UNCHECKED_CAST")
		@JvmStatic
		fun <T> generate(targetType:Class<T>):T{
			//遍历已注册的默认值生成器，如果匹配目标类型，则尝试用它生成默认值
			//如果成功则加入缓存，如果失败则抛出异常
			val defaultGenerator = componentCache.getOrPut(targetType){
				for(defaultGenerator in components) {
					if(defaultGenerator.targetType.isAssignableFrom(targetType)) {
						return@getOrPut defaultGenerator
					}
				}
				//尝试使用无参构造实例化
				try{
					return targetType.getConstructor().newInstance()
				}catch(e:Exception){
					throw IllegalArgumentException("No suitable default generator found for target type '$targetType'.")
				}
			}
			return defaultGenerator.generate() as T
		}
	}

	//region Default Generators
	object DefaultByteGenerator: DefaultGenerator<Byte>{
		override val targetType: Class<Byte> = Byte::class.javaObjectType
		private const val value: Byte = 0
		override fun generate(): Byte = value
	}

	object DefaultShortGenerator:DefaultGenerator<Short>{
		override val targetType: Class<Short> = Short::class.javaObjectType
		private const val value: Short = 0
		override fun generate(): Short = value
	}

	object DefaultIntGenerator:DefaultGenerator<Int>{
		override val targetType: Class<Int> = Int::class.javaObjectType
		private const val value: Int = 0
		override fun generate(): Int = value
	}

	object DefaultLongGenerator:DefaultGenerator<Long>{
		override val targetType: Class<Long> = Long::class.javaObjectType
		private val value: Long = 0
		override fun generate(): Long = value
	}

	object DefaultFloatGenerator:DefaultGenerator<Float>{
		override val targetType: Class<Float> = Float::class.javaObjectType
		private val value: Float = 0f
		override fun generate(): Float = value
	}

	object DefaultDoubleGenerator:DefaultGenerator<Double>{
		override val targetType: Class<Double> = Double::class.javaObjectType
		private val value: Double = 0.0
		override fun generate():Double = value
	}

	object DefaultBigIntegerGenerator:DefaultGenerator<BigInteger>{
		override val targetType: Class<BigInteger> = BigInteger::class.java
		private val value: BigInteger = BigInteger.ZERO
		override fun generate(): BigInteger = value
	}

	object DefaultBigDecimalGenerator:DefaultGenerator<BigDecimal>{
		override val targetType: Class<BigDecimal> = BigDecimal::class.java
		private val value: BigDecimal = BigDecimal.ZERO
		override fun generate(): BigDecimal = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUByteGenerator:DefaultGenerator<UByte>{
		override val targetType: Class<UByte> = UByte::class.java
		private val value: UByte = 0.toUByte()
		override fun generate(): UByte = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUShortGenerator:DefaultGenerator<UShort>{
		override val targetType: Class<UShort> = UShort::class.java
		private val value: UShort = 0.toUShort()
		override fun generate(): UShort = value
	}

	@ExperimentalUnsignedTypes
	object DefaultUIntGenerator:DefaultGenerator<UInt>{
		override val targetType: Class<UInt> = UInt::class.java
		private val value: UInt = 0.toUInt()
		override fun generate(): UInt = value
	}

	object DefaultCharGenerator:DefaultGenerator<Char>{
		override val targetType: Class<Char> = Char::class.java
		private val value: Char = '\u0000'
		override fun generate(): Char = value
	}

	object DefaultBooleanGenerator:DefaultGenerator<Boolean>{
		override val targetType: Class<Boolean> = Boolean::class.java
		private val value: Boolean = false
		override fun generate(): Boolean = value
	}

	object DefaultStringGenerator:DefaultGenerator<String>{
		override val targetType: Class<String> = String::class.java
		private val value: String = ""
		override fun generate(): String = value
	}

	object DefaultDateGenerator:DefaultGenerator<Date>{
		override val targetType: Class<Date> = Date::class.java
		override fun generate(): Date = Date()
	}

	object DefaultLocalDateGenerator:DefaultGenerator<LocalDate>{
		override val targetType: Class<LocalDate> = LocalDate::class.java
		override fun generate(): LocalDate = LocalDate.now()
	}

	object DefaultLocalTimeGenerator:DefaultGenerator<LocalTime>{
		override val targetType: Class<LocalTime> = LocalTime::class.java
		override fun generate(): LocalTime = LocalTime.now()
	}

	object DefaultLocalDateTimeGenerator:DefaultGenerator<LocalDateTime>{
		override val targetType: Class<LocalDateTime> = LocalDateTime::class.java
		override fun generate(): LocalDateTime = LocalDateTime.now()
	}

	object DefaultInstantGenerator:DefaultGenerator<Instant>{
		override val targetType: Class<Instant> = Instant::class.java
		override fun generate(): Instant = Instant.now()
	}

	object DefaultListGenerator:DefaultGenerator<List<*>>{
		override val targetType: Class<List<*>> = List::class.java
		override fun generate(): List<*> = emptyList<Any?>()
	}

	object DefaultSetGenerator:DefaultGenerator<Set<*>>{
		override val targetType: Class<Set<*>> = Set::class.java
		override fun generate(): Set<*> = emptySet<Any?>()
	}

	object DefaultSequenceGenerator:DefaultGenerator<Sequence<*>>{
		override val targetType: Class<Sequence<*>> = Sequence::class.java
		override fun generate(): Sequence<*> = sequenceOf<Any?>()
	}

	object DefaultMapGenerator:DefaultGenerator<Map<*,*>>{
		override val targetType: Class<Map<*, *>> = Map::class.java
		override fun generate(): Map<*, *> = emptyMap<Any?,Any?>()
	}
	//endregion
}
