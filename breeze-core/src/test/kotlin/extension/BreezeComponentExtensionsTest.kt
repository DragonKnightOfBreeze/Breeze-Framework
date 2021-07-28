// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.component.*
import kotlin.test.*

class BreezeComponentExtensionsTest {
	@Test
	fun convertTest() {
		"123".convert<Int>().andPrintln()
		"123".convertOrNull<Int>().andPrintln()
		123.convertOrNull<String>().andPrintln()
		123.convertOrNull<String>().andPrintln()
	}

	@Test
	fun escapeByTest() {
		println("hello\tworld\n".escapeBy(Escaper.KotlinEscaper))
		assertEquals("hello\\tworld\\n", "hello\tworld\n".escapeBy(Escaper.KotlinEscaper))
	}

	@Test
	fun unescapeByTest() {
		println("\\\\t".escapeBy(Escaper.KotlinEscaper))
		println("\\\\t".unescapeBy(Escaper.KotlinEscaper))
		assertEquals("hello\tworld\n", "hello\\tworld\\n".unescapeBy(Escaper.KotlinEscaper))
	}

	@Test
	fun encoderTest() {

	}

	@Test
	fun encrypterTest() {
		val a = "hello".encodeToByteArray().encryptBy(Encrypter.DesEncrypter, "12345678".encodeToByteArray())
		val b = a.decryptBy(Encrypter.DesEncrypter, "12345678".encodeToByteArray()).decodeToString()
		assertEquals("hello", b)
	}

	@Test
	fun caseTypeTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(CaseFormat.CamelCaseFormat))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(CaseFormat.PascalCaseFormat))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(CaseFormat.KebabCaseFormat))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(CaseFormat.ScreamingSnakeCaseFormat))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(CaseFormat.LowerCaseFormatWords))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(CaseFormat.CapitalizedWords))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(CaseFormat.CamelCaseFormat))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(CaseFormat.LowerCaseFormat))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(CaseFormat.UpperCaseFormat))
	}

	@Test
	fun matchesByTest() {
		assertTrue { "".matchesBy("") }
		assertTrue { "/foo/bar".matchesBy("/foo/bar") }
		assertTrue { "/foo/bar".matchesBy("/foo/{a}") }
		assertTrue { "/foo/bar".matchesBy("/{a}/bar") }

		assertFalse { "/foo/bar".matchesBy("/foo/ba") }
		assertFalse { "/foo/bar".matchesBy("/{a}/ba") }
		assertFalse { "/foo/bar".matchesBy("/foo/bar/123") }
		assertFalse { "/foo/bar".matchesBy("/{a}/bar/123") }

		assertTrue { "".matchesBy("", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b?r", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*r", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar*", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/**", PathFormat.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar**", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**o/aaa", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*/aaa", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**/aaa", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/**", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*/var", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/**/var", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*r/var", PathFormat.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*/va", PathFormat.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/**/va", PathFormat.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*r/va", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/{var}", PathFormat.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/{bar}/var", PathFormat.AntPath) }
		assertTrue { "".matchesBy("", PathFormat.AntPath) }
		assertTrue { "/foo".matchesBy("/foo", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/foo/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f?o/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*o/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**o/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**/{a}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/bar/{b}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*/{b}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/**/{b}", PathFormat.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*ar/{b}", PathFormat.AntPath) }

		"/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathFormat.AntPath)
	}

	@Test
	fun resolveVariablesByTest() {
		assertEquals(mapOf(), "".resolveVariablesBy(""))
		assertEquals(mapOf(), "/foo".resolveVariablesBy("/foo"))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/foo/{a}"))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}"))
		assertEquals(mapOf(), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}/far"))

		assertEquals(mapOf(), "".resolveVariablesBy("", PathFormat.AntPath))
		assertEquals(mapOf(), "/foo".resolveVariablesBy("/foo", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/foo/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f?o/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f*o/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f**o/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f*/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f**/{a}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*/{b}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**/{b}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*ar/{b}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/?ar/{b}", PathFormat.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**ar/{b}", PathFormat.AntPath))
	}

	@Test
	fun queryByTest() {
		val list = listOf<Any?>(1, listOf(2, 3, 4), listOf(5, mapOf("a" to 1, "b" to 2)), 8)
		assertEquals(listOf(list), list.queryBy("/"))
		assertEquals(listOf(1), list.queryBy("/0"))
		assertEquals(listOf(3), list.queryBy("/1/1"))
		assertEquals(list, list.getBy("/"))
		assertEquals(1, list.getBy("/0"))
		assertEquals(3, list.getBy("/1/1"))
		assertEquals<Any?>(1, list.getOrNullBy("/0"))
		assertEquals(1, list.getOrElseBy("/0") { 111 })
		assertEquals(null, list.getOrNullBy("/111"))
		assertEquals(111, list.getOrElseBy("/111") { 111 })
		assertEquals(list, list.queryBy("/{name}"))
		assertEquals(listOf(2, 3, 4), list.queryBy("/1/{name}"))

		assertEquals(listOf(list), list.queryBy("", PathFormat.ReferencePath))
		assertEquals(listOf(1), list.queryBy("[0]", PathFormat.ReferencePath))
		assertEquals(listOf(3), list.queryBy("[1][1]", PathFormat.ReferencePath))
		assertEquals(list, list.getBy("", PathFormat.ReferencePath))
		assertEquals(1, list.getBy("[0]", PathFormat.ReferencePath))
		assertEquals(3, list.getBy("[1][1]", PathFormat.ReferencePath))
		assertEquals<Any?>(1, list.getOrNullBy("[0]", PathFormat.ReferencePath))
		assertEquals(1, list.getOrElseBy("[0]", PathFormat.ReferencePath) { 111 })
		assertEquals(null, list.getOrNullBy("[111]", PathFormat.ReferencePath))
		assertEquals(111, list.getOrElseBy("[111]", PathFormat.ReferencePath) { 111 })
	}
}
