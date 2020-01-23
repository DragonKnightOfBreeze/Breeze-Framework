package com.windea.breezeframework.core.extensions

import org.junit.*
import java.math.*
import kotlin.system.*

class NumberExtensionsKtTest {
	//@Test
	//fun factorial() {
	//	println(1.factorial())
	//	println(100.factorial())
	//	println(1000.factorial())
	//	//println(Long.MAX_VALUE.factorial()) //太恐怖了。。。
	//}

	//@Test
	//fun cumulative() {
	//	println(1.cumulative())
	//	println(100.cumulative())
	//	println(1000.cumulative())
	//	//println(Long.MAX_VALUE.cumulative()) //虽然最终能够得出结果，但是仍然很恐怖
	//}

	@Test
	fun round() {
		println(1.11111f.round(2))
		println(1.55555f.round(2))
		println(1.50000f.round(2))
		println(1.99999f.round(2))
	}

	@Test //TESTED
	fun toNumber() {
		println(1.toNumber<Byte>())
		println(1.toNumber<Short>())
		println(1.toNumber<Int>())
		println(1.toNumber<Long>())
		println(1.toNumber<Float>())
		println(1.toNumber<Double>())
		println(1.toNumber<BigInteger>())

		var a = 0L
		var b = 0L
		repeat(1000) { a += measureNanoTime { 1.0.toInt() } }
		repeat(1000) { b += measureNanoTime { 1.0.toNumber<Int>() } }
		println(a)
		println(b)
	}
}
