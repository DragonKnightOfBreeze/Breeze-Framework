package com.windea.breezeframework.core.deprecated

import com.windea.breezeframework.core.annotations.*
import kotlin.reflect.*
import kotlin.test.*

class StudyTests {
	@Test
	fun test1() {
		readLine()
	}

	@Test
	fun sequenceTest() {
		val seq1 = sequence { this.yield(1) }
		seq1.take(5).forEach { println(it) }

		val seq2 = generateSequence(0) { it + 1 }
		seq2.take(10).forEach { println(it) }
	}

	fun test2() {
	}

	@Test
	fun testNoArg() {
		@Suppress("DEPRECATION")
		val p = PersonA::class.java.newInstance()
		println(p)
	}

	@Test
	fun testKReflection() {
		//val person = PersonA("Windea", "???")
		////得到成员只读属性
		//val a = person::class.memberProperties.filterIsInstance<KProperty1<PersonA, String>>().map { it.get(person) }
		//println(a)
		//val a2 = person::class.memberProperties.getProperty<KProperty1<PersonA, String>>("username").map { it.get(person) }
		//println(a2)
		//person::class.memberProperties.filterIsInstance<KMutableProperty1<PersonA, String>>().map { it.set(person, "A") }
		//val b = person::class.memberProperties.filterIsInstance<KProperty1<PersonA, String>>().map { it.get(person) }
		//println(b)
	}

	@Test
	fun testKReflection2() {
		//已支持build-in types的反射。
		//String::class.members.forEach(::println)
		//Map::class.members.forEach(::println)
		//Int::class.members.forEach(::println)
	}

	@Test
	fun testReflectTime() {
		//val person = PersonA("Windea", "???")
		////1486397500 所有
		////1111486800
		////1226933800
		//measureNanoTime {
		//	PersonA::class.memberProperties.first().get(person)
		//}.let { println(it) }
		////713400 所有
		////478300
		////408800
		//measureNanoTime {
		//	PersonA::class.java.declaredFields.first().also { it.isAccessible = true }.get(person)
		//}.let { println(it) }

		//val foo = Foo("123")
		////1079325800 1.079s
		//measureNanoTime { Foo::class.memberProperties.first().get(foo) }.let(::println)
		////419000  0.0004s
		//measureNanoTime { Foo::class.java.declaredFields.first().also { it.isAccessible = true }.get(foo) }.let(::println)
	}

	@Test
	fun testRegex() {
		println("abc123abc" matches "\\d+".toRegex())
		println("abc123abc" matches "abc\\d+abc".toRegex())
		println("abc\nabc123abc\nabc" matches "abc\\d+abc".toRegex())
		println("abc123abc" matches "^\\d+$".toRegex())
		println("abc123abc" matches "^abc\\d+abc$".toRegex())
		println("abc\nabc123abc\nabc" matches "^abc\\d+abc$".toRegex())
		println("\\d+".toRegex().find("abc123abc")?.groupValues?.forEach { println(it) })
	}

	@Test
	fun test0() {
		println(12345.takeHighestOneBit())
		println(12345.takeLowestOneBit())
	}
}


class Foo(
	val bar: String
)

@NoArg
interface IPerson

@NoArg
class PersonA(
	var username: String,
	val password: String
) : IPerson

private inline fun <reified P : KProperty<*>> Collection<KProperty<*>>.getProperty(name: String): List<P> {
	return this.filter { it.name == name }.filterIsInstance<P>()
}
