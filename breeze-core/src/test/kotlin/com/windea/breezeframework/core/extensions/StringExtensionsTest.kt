package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.text.*
import com.windea.breezeframework.core.enums.text.LetterCase.*
import com.windea.breezeframework.core.enums.text.LetterCase.Companion.SCREAMING_SNAKE_CASE
import com.windea.breezeframework.core.enums.text.LetterCase.Companion.`Capitalized Words`
import com.windea.breezeframework.core.enums.text.LetterCase.Companion.`kebab-case`
import com.windea.breezeframework.core.enums.text.LetterCase.Companion.`lower case words`
import com.windea.breezeframework.core.enums.text.LetterCase.Companion.camelCase
import com.windea.breezeframework.core.enums.text.ReferenceCase.*
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
		assertEquals("Abc.Abc", "Abc.Abc".switchCaseBy(JavaReference))
		assertEquals("abc.abc[1].abc", "/abc/abc/1/abc".switchCaseBy(JavaReference))
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
		println(string.replaceLooped("""([a-z])\d""".toRegex(), "$1"))
	}

	@Test
	fun quoteTest() {
		assertEquals(""""'1\"2'"""", """'1"2'""".quote('"'))
		assertEquals("""'1"2'""", """"'1\"2'"""".unquote())
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
}
