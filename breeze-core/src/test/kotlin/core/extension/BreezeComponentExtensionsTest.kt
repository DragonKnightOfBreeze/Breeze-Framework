// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.extension

import icu.windea.breezeframework.core.component.*
import icu.windea.breezeframework.core.component.extension.*
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
		println("hello\tworld\n".escapeBy(Escapers.KotlinEscaper))
		assertEquals("hello\\tworld\\n", "hello\tworld\n".escapeBy(Escapers.KotlinEscaper))
	}

	@Test
	fun unescapeByTest() {
		println("\\\\t".escapeBy(Escapers.KotlinEscaper))
		println("\\\\t".unescapeBy(Escapers.KotlinEscaper))
		assertEquals("hello\tworld\n", "hello\\tworld\\n".unescapeBy(Escapers.KotlinEscaper))
	}

	@Test
	fun encoderTest() {

	}

	@Test
	fun encrypterTest() {
		val a = "hello".encodeToByteArray().encryptBy(Encrypters.DesEncrypter, "12345678".encodeToByteArray())
		val b = a.decryptBy(Encrypters.DesEncrypter, "12345678".encodeToByteArray()).decodeToString()
		assertEquals("hello", b)
	}

	@Test
	fun caseTypeTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(CaseFormats.CamelCaseFormat))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(CaseFormats.PascalCaseFormat))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(CaseFormats.KebabCaseFormat))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(CaseFormats.ScreamingSnakeCaseFormat))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(CaseFormats.LowerCaseFormatWords))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(CaseFormats.CapitalizedWords))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(CaseFormats.CamelCaseFormat))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(CaseFormats.LowerCaseFormat))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(CaseFormats.UpperCaseFormat))
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

		assertTrue { "".matchesBy("", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b?r", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*r", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar*", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/**", PathFormats.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar**", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**o/aaa", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*/aaa", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**/aaa", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/**", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*/var", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/**/var", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*r/var", PathFormats.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*/va", PathFormats.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/**/va", PathFormats.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*r/va", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/{var}", PathFormats.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/{bar}/var", PathFormats.AntPath) }
		assertTrue { "".matchesBy("", PathFormats.AntPath) }
		assertTrue { "/foo".matchesBy("/foo", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/foo/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f?o/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*o/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**o/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f*/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa".matchesBy("/f**/{a}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/bar/{b}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*/{b}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/**/{b}", PathFormats.AntPath) }
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*ar/{b}", PathFormats.AntPath) }

		"/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathFormats.AntPath)
	}

	@Test
	fun resolveVariablesByTest() {
		assertEquals(mapOf(), "".resolveVariablesBy(""))
		assertEquals(mapOf(), "/foo".resolveVariablesBy("/foo"))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/foo/{a}"))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}"))
		assertEquals(mapOf(), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}/far"))

		assertEquals(mapOf(), "".resolveVariablesBy("", PathFormats.AntPath))
		assertEquals(mapOf(), "/foo".resolveVariablesBy("/foo", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/foo/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f?o/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f*o/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f**o/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f*/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa"), "/foo/aaa".resolveVariablesBy("/f**/{a}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*/{b}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**/{b}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*ar/{b}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/?ar/{b}", PathFormats.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"), "/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**ar/{b}", PathFormats.AntPath))
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

		assertEquals(listOf(list), list.queryBy("", PathFormats.ReferencePath))
		assertEquals(listOf(1), list.queryBy("[0]", PathFormats.ReferencePath))
		assertEquals(listOf(3), list.queryBy("[1][1]", PathFormats.ReferencePath))
		assertEquals(list, list.getBy("", PathFormats.ReferencePath))
		assertEquals(1, list.getBy("[0]", PathFormats.ReferencePath))
		assertEquals(3, list.getBy("[1][1]", PathFormats.ReferencePath))
		assertEquals<Any?>(1, list.getOrNullBy("[0]", PathFormats.ReferencePath))
		assertEquals(1, list.getOrElseBy("[0]", PathFormats.ReferencePath) { 111 })
		assertEquals(null, list.getOrNullBy("[111]", PathFormats.ReferencePath))
		assertEquals(111, list.getOrElseBy("[111]", PathFormats.ReferencePath) { 111 })
	}
}
