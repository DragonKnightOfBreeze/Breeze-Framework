import com.windea.utility.common.domain.*
import com.windea.utility.common.extensions.*
import org.junit.*
import java.time.*
import kotlin.reflect.*

class CommonTests {
	@Test
	fun test1() {
		println(1.0.toBigDecimal() - 0.9.toBigDecimal() == 0.1.toBigDecimal())
	}
	
	//中文方法名居然是合法的，什么鬼东西……
	@Test
	fun test2() {
		你好 { "世界！" }
	}
	
	fun 你好(message: () -> String) {
		println("你好：")
		println(message())
	}
	
	@Test
	fun test3() {
		val 昨天: Period = Period.of(2019, 8, 7)
		
		昨天 是 (1 天 前)
	}
	
	infix fun <T> T.是(other: T) = this == other
	
	object 前
	
	infix fun Int.天(other: 前) = this.days
	
	@Test
	fun test4() {
		val map = mapOf("a" to 1, "b" to 2)
		println(map)
		println(map.entries)
		println(map.toList())
	}
	
	@Test
	@ExperimentalStdlibApi
	fun test5() {
		//源代码显示抛出UnsupportedException，但是的确能够正常工作，为什么？
		println(typeOf<Int>())
		
		fun func1(param1: Int) {}
		println(nameOf<Int>())
		println(nameOf<Foo1>())
		println(nameOf(Int::class))
		println(nameOf(CommonTests::test5))
		println(nameOf(Bar::prop))
		println(nameOf(Bar::func))
	}
	
	@Test
	fun test6() {
		println(1..2)
		println(1 until 2)
		println(1 downTo 0)
	}
	
	@Test
	fun test7() {
		println("123".toInt())
		//println("123L".toLong())
		println("123.0".toFloat())
		println("123.0".toDouble())
	}
	
	@Test
	fun test8() {
		println(EntityA(1L) == EntityB(1L))
		println(EntityA(1L) == EntityA(1L))
		println(EntityA(1L) == EntityB(2L))
		println(EntityA(1L) == EntityA(2L))
		println(EntityA(3L))
	}
}

annotation class Foo1(
	val exp: KClass<Function<String>>
)

class Bar {
	val prop = 1
	fun func() {}
}

open class EntityA(override var id: Long?) : TEntity<Long>()

open class EntityB(override var id: Long?) : TEntity<Long>()
