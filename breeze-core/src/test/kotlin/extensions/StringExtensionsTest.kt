package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.domain.text.*
import com.windea.breezeframework.core.domain.text.LetterCase.*
import com.windea.breezeframework.core.domain.text.LetterCase.Companion.SCREAMING_SNAKE_CASE
import com.windea.breezeframework.core.domain.text.LetterCase.Companion.`Capitalized Words`
import com.windea.breezeframework.core.domain.text.LetterCase.Companion.`kebab-case`
import com.windea.breezeframework.core.domain.text.LetterCase.Companion.`lower case words`
import com.windea.breezeframework.core.domain.text.LetterCase.Companion.camelCase
import com.windea.breezeframework.core.domain.text.ReferenceCase.*
import kotlin.test.*

class StringExtensionsTest {
	@Test
	fun letterCaseTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(camelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(`kebab-case`))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(SCREAMING_SNAKE_CASE))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(`lower case words`))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(`Capitalized Words`))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(camelCase))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(LowerCase))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(UpperCase))
	}

	@Test
	fun referenceCaseTest() {
		assertEquals("Abc.Abc", "Abc.Abc".switchCaseBy(ObjectReference))
		assertEquals("abc.abc[1].abc", "/abc/abc/1/abc".switchCaseBy(ObjectReference))
		assertEquals("$.abc.abc.[1].abc", "/abc/abc/1/abc".switchCaseBy(JsonReference))
		assertEquals("/abc/abc/1/abc", "/abc/abc/1/abc".switchCaseBy(PathReference))
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
	fun quoteTest() {
		assertEquals(""""'1\"2'"""", """'1"2'""".quote('"', false))
		assertEquals("""'1"2'""", """"'1\"2'"""".unquote(false))
	}

	@Test
	fun transformInTest() {
		"\\Q abc abc \\E..\\Q abc abc abc \\E abc abc \\Q abc abc 123 \\E".transformIn("\\Q", "\\E") {
			it.trim().split(" ").joinToString(", ", "{", "}") { s -> s.dropLast(1) }
		}.also { println(it) }
	}

	@Test
	fun toRegexByTest() {
		assertTrue("/home/123/detail" matches "/home/?23/detail".toRegexBy(MatchType.AntPath))
		assertTrue("/home/123/detail" matches "/home/*/detail".toRegexBy(MatchType.AntPath))
		assertTrue("/home/123/detail" matches "/home/**/detail".toRegexBy(MatchType.AntPath))
		assertTrue("/home/123/detail" matches "/home/**".toRegexBy(MatchType.AntPath))
		assertTrue("/home/123/detail" matches "/*/123/**".toRegexBy(MatchType.AntPath))
		assertTrue("/home/123/detail" matches "/home/123/detail".toRegexBy(MatchType.AntPath))

		assertTrue("Test.kt" matches "*.kt".toRegexBy(MatchType.EditorConfigPath))
		assertTrue("Test.kt" matches "*.{kt, kts}".toRegexBy(MatchType.EditorConfigPath))
		assertTrue("Test.kt" matches "Tes[a-z].kt".toRegexBy(MatchType.EditorConfigPath))
		assertTrue("Test.kt" matches "Test.kt".toRegexBy(MatchType.EditorConfigPath))

		assertTrue("/abc/123/def" matches "/abc/-/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/[]/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/[23-234]/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/[b]/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/[b]/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/{}/123/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/{a}/123/def".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123" matches "/abc/[b]".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/[b]/re:[def]*".toRegexBy(MatchType.PathReference))
		assertTrue("/abc/123/def" matches "/abc/123/def".toRegexBy(MatchType.PathReference))
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
	fun splitMatchedTest() {
		val expectedResult = listOf("https","localhost","8080","www.test.com","name=Windea")
		val result = "https://localhost:8080/www.test.com?name=Windea"
			.splitMatched("://", ":", "/", null, "?") { i, s -> arrayOf("", "", "", s,  "")[i] }
		assertEquals(expectedResult,result)
	}

	@Test
	fun splitToStringsTest(){
		val expectedResult = listOf("name" to "Windea","age" to "4000",
			"weapon" to listOf("L" to "Breeze'sBreath","R" to "Breeze'sLanding}"), "gender" to "Female")
		val result = "{name=Windea,age=4000,weapon={L=Breeze'sBreath,R=Breeze'sLanding},gender=Female}"
			.splitToStrings(",","{","}").map{
				it.split("=",limit=2).let{ (a,b)->a to when{
					"," in b -> b.splitToStrings(",","{","}").map{e -> e.split("=",limit=2).let{ (a1,b1)->a1 to b1}}
					else -> b.split("=",limit=2).let{ (a1,b1)->a1 to b1}
				}}
			}
		assertEquals(expectedResult,result)
	}

	@Test
	fun toColorTest(){
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
}
