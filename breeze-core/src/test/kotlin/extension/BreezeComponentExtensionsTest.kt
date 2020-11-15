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
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(CaseType.CamelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(CaseType.PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(CaseType.KebabCase))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(CaseType.ScreamingSnakeCase))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(CaseType.LowerCaseWords))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(CaseType.CapitalizedWords))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(CaseType.CamelCase))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(CaseType.LowerCase))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(CaseType.UpperCase))
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

		assertTrue { "".matchesBy("", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b?r", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*r", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar*", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/**", PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/bar**", PathType.AntPath) }
		assertTrue {"/foo/aaa".matchesBy("/f**o/aaa", PathType.AntPath)}
		assertTrue {"/foo/aaa".matchesBy("/f*/aaa", PathType.AntPath)}
		assertTrue {"/foo/aaa".matchesBy("/f**/aaa", PathType.AntPath)}
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/**", PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*/var", PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/**/var", PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/*r/var", PathType.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*/va", PathType.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/**/va", PathType.AntPath) }
		assertFalse { "/foo/bar/var".matchesBy("/foo/*r/va", PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/{var}", PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/{bar}/var", PathType.AntPath) }
		assertTrue { "".matchesBy("", PathType.AntPath)}
		assertTrue { "/foo".matchesBy("/foo", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/foo/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f?o/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f*o/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f**o/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f*/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa".matchesBy("/f**/{a}", PathType.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/bar/{b}", PathType.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*/{b}", PathType.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/**/{b}", PathType.AntPath)}
		assertTrue { "/foo/aaa/bar/bbb".matchesBy("/foo/{a}/*ar/{b}", PathType.AntPath)}

		"/foo/bar/bar/bar".matchesBy("/foo/*/b?r/**", PathType.AntPath)
	}

	@Test
	fun resolveVariablesByTest(){
		assertEquals(mapOf(),"".resolveVariablesBy(""))
		assertEquals(mapOf(),"/foo".resolveVariablesBy("/foo"))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/foo/{a}"))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}"))
		assertEquals(mapOf(),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}/far"))

		assertEquals(mapOf(),"".resolveVariablesBy("", PathType.AntPath))
		assertEquals(mapOf(),"/foo".resolveVariablesBy("/foo", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/foo/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f?o/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f*o/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f**o/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f*/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa"),"/foo/aaa".resolveVariablesBy("/f**/{a}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/bar/{b}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*/{b}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**/{b}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/*ar/{b}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/?ar/{b}", PathType.AntPath))
		assertEquals(mapOf("a" to "aaa", "b" to "bbb"),"/foo/aaa/bar/bbb".resolveVariablesBy("/foo/{a}/**ar/{b}", PathType.AntPath))
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

		assertEquals(listOf(list),list.queryBy("", PathType.ReferencePath))
		assertEquals(listOf(1),list.queryBy("[0]", PathType.ReferencePath))
		assertEquals(listOf(3),list.queryBy("[1][1]", PathType.ReferencePath))
		assertEquals(list,list.getBy("", PathType.ReferencePath))
		assertEquals(1,list.getBy("[0]", PathType.ReferencePath))
		assertEquals(3,list.getBy("[1][1]", PathType.ReferencePath))
		assertEquals<Any?>(1,list.getOrNullBy("[0]", PathType.ReferencePath))
		assertEquals(1,list.getOrElseBy("[0]", PathType.ReferencePath) {111})
		assertEquals(null,list.getOrNullBy("[111]", PathType.ReferencePath))
		assertEquals(111,list.getOrElseBy("[111]", PathType.ReferencePath) {111})
	}
}
