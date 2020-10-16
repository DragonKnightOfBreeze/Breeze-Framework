package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.*
import com.windea.breezeframework.core.domain.LetterCase.*
import kotlin.test.*

class StringExtensionsTest {
	@Test
	fun letterCaseTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(CamelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(KebabCase))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(ScreamingSnakeCase))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(LowerCaseWords))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(CapitalizedWords))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(CamelCase))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(LowerCase))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(UpperCase))
	}

	@Test
	fun repeatExtensionTest() {
		println("abc".repeat(3))
		println("abc".repeatOrdinal(3))
	}

	@Test
	fun replaceExtensionTest() {
		val string = "abc123abc123"
		println(string.replace("""([a-z])\d""".toRegex(), "$1"))
		println(string.replaceRepeatedly("""([a-z])\d""".toRegex(), "$1"))
	}

	@Test
	fun transformInTest() {
		"\\Q abc abc \\E..\\Q abc abc abc \\E abc abc \\Q abc abc 123 \\E".transformIn("\\Q", "\\E") {
			it.trim().split(" ").joinToString(", ", "{", "}") { s -> s.dropLast(1) }
		}.also { println(it) }
	}

	@Test
	fun ifNotEmptyOrBlankTest() {
		println("123".ifNotEmpty { "/" })
		println("123".ifNotBlank { "/" })
	}

	@Test
	fun formatTest() {
		assertEquals(
			"hello world!",
			"hello {}!".formatBy(FormatType.Log, "world")
		)
		assertEquals(
			"hello world, hello java!",
			"hello {0}, hello {1}!".formatBy(FormatType.Indexed, "world", "java")
		)
		assertEquals(
			"hello world, hello java!",
			"hello {arg1}, hello {arg2}!".formatBy(FormatType.Named, "arg1" to "world", "arg2" to "java")
		)
	}

	@Test
	fun quoteTest() {
		assertEquals(""""'1\"2'"""", """'1"2'""".quote('"', false))
		assertEquals("""'1"2'""", """"'1\"2'"""".unquote(false))
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
	fun splitMatchedTest() {
		val expectedResult = listOf("https", "localhost", "8080", "www.test.com", "name=Windea")
		val result = "https://localhost:8080/www.test.com?name=Windea"
			.splitMatched("://", ":", "/", null, "?") { i, s -> arrayOf("", "", "", s, "")[i] }
		assertEquals(expectedResult, result)
	}

	@Test
	fun splitToStringsTest() {
		val expectedResult = listOf("name" to "Windea", "age" to "4000",
			"weapon" to listOf("L" to "Breeze'sBreath", "R" to "Breeze'sLanding}"), "gender" to "Female")
		val result = "{name=Windea,age=4000,weapon={L=Breeze'sBreath,R=Breeze'sLanding},gender=Female}"
			.splitToStrings(",", "{", "}").map {
				it.split("=", limit = 2).let { (a, b) ->
					a to when {
						"," in b -> b.splitToStrings(",", "{", "}").map { e -> e.split("=", limit = 2).let { (a1, b1) -> a1 to b1 } }
						else -> b.split("=", limit = 2).let { (a1, b1) -> a1 to b1 }
					}
				}
			}
		assertEquals(expectedResult, result)
	}

	@Test
	fun toColorTest() {
		println("red".toColor())
		println("red".toColor())
		println("Red".toColor())
		println("darkred".toColor())
		println("dark red".toColor())
		println("darkRed".toColor())
		println("dark_red".toColor())
		println("#ff0000".toColor())
		println("#f00".toColor())
		println("#f00".toColor())
		println("rgb(255 0 0)".toColor())
		println("rgb(255,0,0)".toColor())
		println("rgb(255, 0, 0)".toColor())
		println("rgba(255 0 0 255)".toColor())
		println("rgba(255,0,0,255)".toColor())
		println("rgba(255, 0, 0, 255)".toColor())

		assertNotNull("red".toColor())
		assertNotNull("Red".toColor())
		assertNotNull("darkred".toColor())
		assertNotNull("dark red".toColor())
		assertNotNull("darkRed".toColor())
		assertNotNull("dark_red".toColor())
		assertNotNull("#ff0000".toColor())
		assertNotNull("#f00".toColor())
		assertNotNull("#f00".toColor())
		assertNotNull("rgb(255 0 0)".toColor())
		assertNotNull("rgb(255,0,0)".toColor())
		assertNotNull("rgb(255, 0, 0)".toColor())
		assertNotNull("rgba(255 0 0 255)".toColor())
		assertNotNull("rgba(255,0,0,255)".toColor())
		assertNotNull("rgba(255, 0, 0, 255)".toColor())
	}

	@Test
	fun matchesByTest(){
		assertTrue { "/foo/bar".matchesBy("/foo/bar") }
		assertTrue { "/foo/bar".matchesBy("/foo/{a}") }
		assertTrue { "/foo/bar".matchesBy("/{a}/bar") }

		assertFalse { "/foo/bar".matchesBy("/foo/ba") }
		assertFalse { "/foo/bar".matchesBy("/{a}/ba") }
		assertFalse { "/foo/bar".matchesBy("/foo/bar/123") }
		assertFalse { "/foo/bar".matchesBy("/{a}/bar/123") }

		assertTrue { "/foo/bar".matchesBy("/foo/bar",PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b?r",PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*r",PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/b*",PathType.AntPath) }
		assertTrue { "/foo/bar".matchesBy("/foo/**",PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/**",PathType.AntPath) }
		assertTrue { "/foo/bar/var".matchesBy("/foo/bar/{var}",PathType.AntPath) }
	}
}
