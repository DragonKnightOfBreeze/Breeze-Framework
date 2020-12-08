// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.component.*
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
		assertEquals("hello\\tworld\\n", "hello\tworld\n".escapeBy(Escaper.KotlinEscaper))
	}

	@Test
	fun unescapeByTest() {
		assertEquals("hello\tworld\n", "hello\\tworld\\n".unescapeBy(Escaper.KotlinEscaper))
	}

	@Test
	fun encoderTest(){

	}

	@Test
	fun encrypterTest(){
		val a = "hello".encodeToByteArray().encryptBy(Encrypter.DesEncrypter, "12345678".encodeToByteArray())
		val b = a.decryptBy(Encrypter.DesEncrypter,"12345678".encodeToByteArray()).decodeToString()
		assertEquals("hello",b)
	}

	@Test
	fun caseTypeTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(LetterCase.CamelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(LetterCase.PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(LetterCase.KebabCase))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(LetterCase.ScreamingSnakeCase))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(LetterCase.LowerCaseWords))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(LetterCase.CapitalizedWords))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(LetterCase.CamelCase))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(LetterCase.LowerCase))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(LetterCase.UpperCase))
	}

	@Test
	fun matchesByTest(){
		assertTrue { "".matchesBy("") }
		assertTrue { "/foo/bar".matchesBy("/foo/bar") }
		assertTrue { "/foo/bar".matchesBy("/foo/{a}") }
		assertTrue { "/foo/bar".matchesBy("/{a}/bar") }

		assertFalse { "/foo/bar".matchesBy("/foo/ba") }
		assertFalse { "/foo/bar".matchesBy("/{a}/ba") }
		assertFalse { "/foo/bar".matchesBy("/foo/bar/123") }
		assertFalse { "/foo/bar".matchesBy("/{a}/bar/123") }

		assertTrue { "".matchesBy("", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b?r", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*r", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar*", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/**", PathPattern.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar**", PathPattern.AntPath) }
		assertTrue {"/foo/aaa".matchesBy("/f**o/aaa", PathPattern.AntPath)}
		assertTrue {"/foo/aaa".matchesBy("/f*/aaa", PathPattern.AntPath)}
		assertTrue {"/foo/aaa".matchesBy("/f**/aaa", PathPattern.AntPath)}
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/**", PathPattern.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*/var", PathPattern.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/**/var", PathPattern.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*r/var", PathPattern.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*/va", PathPattern.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/**/va", PathPattern.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*r/va", PathPattern.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/{var}", PathPattern.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/{bar}/var", PathPattern.AntPath) }
		assertTrue { "".matchesBy("", PathPattern.AntPath)}
		assertTrue { "/foo".matchesBy("/foo", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/foo/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f?o/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f*o/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f**o/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f*/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f**/{a}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/bar/{b}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*/{b}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/**/{b}", PathPattern.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*ar/{b}", PathPattern.AntPath)}

		"/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathPattern.AntPath)
	}

	@Test
	fun resolveVariablesByTest(){
		assertEquals(mapOf(),"".resolveVariablesBy(""))
		assertEquals(mapOf(),"/foo".resolveVariablesBy("/foo"))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/foo/{a}"))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}"))
		assertEquals(mapOf(),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}/far"))

		assertEquals(mapOf(),"".resolveVariablesBy("", PathPattern.AntPath))
		assertEquals(mapOf(),"/foo".resolveVariablesBy("/foo", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/foo/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f?o/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f*o/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f**o/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f*/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f**/{a}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*/{b}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**/{b}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*ar/{b}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/?ar/{b}", PathPattern.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**ar/{b}", PathPattern.AntPath))
	}

	@Test
	fun queryByTest(){
		val list = listOf<Any?>(1, listOf(2, 3, 4), listOf(5, mapOf("a" to 1,"b" to 2)), 8)
		assertEquals(listOf(list),list.queryBy("/"))
		assertEquals(listOf(1),list.queryBy("/0"))
		assertEquals(listOf(3),list.queryBy("/1/1"))
		assertEquals(list,list.getBy("/"))
		assertEquals(1,list.getBy("/0"))
		assertEquals(3,list.getBy("/1/1"))
		assertEquals<Any?>(1,list.getOrNullBy("/0"))
		assertEquals(1,list.getOrElseBy("/0") {111})
		assertEquals(null,list.getOrNullBy("/111"))
		assertEquals(111,list.getOrElseBy("/111") {111})
		assertEquals(list,list.queryBy("/{name}"))
		assertEquals(listOf(2, 3, 4),list.queryBy("/1/{name}"))

		assertEquals(listOf(list),list.queryBy("", PathPattern.ReferencePath))
		assertEquals(listOf(1),list.queryBy("[0]", PathPattern.ReferencePath))
		assertEquals(listOf(3),list.queryBy("[1][1]", PathPattern.ReferencePath))
		assertEquals(list,list.getBy("", PathPattern.ReferencePath))
		assertEquals(1,list.getBy("[0]", PathPattern.ReferencePath))
		assertEquals(3,list.getBy("[1][1]", PathPattern.ReferencePath))
		assertEquals<Any?>(1,list.getOrNullBy("[0]", PathPattern.ReferencePath))
		assertEquals(1,list.getOrElseBy("[0]", PathPattern.ReferencePath) {111})
		assertEquals(null,list.getOrNullBy("[111]", PathPattern.ReferencePath))
		assertEquals(111,list.getOrElseBy("[111]", PathPattern.ReferencePath) {111})
	}
}
