package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.component.extension.*
import org.junit.*
import java.math.*
import kotlin.system.*

class NumberExtensionsTest {
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
	fun toNumber() {
		println(1.convert<Byte>())
		println(1.convert<Short>())
		println(1.convert<Int>())
		println(1.convert<Long>())
		println(1.convert<Float>())
		println(1.convert<Double>())
		println(1.convert<BigInteger>())

		var a = 0L
		var b = 0L
		repeat(1000) { a += measureNanoTime { 1.0.toInt() } }
		repeat(1000) { b += measureNanoTime { 1.0.convert<Int>() } }
		println(a)
		println(b)
	}
}
