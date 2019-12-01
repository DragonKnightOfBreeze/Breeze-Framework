package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.core.LetterCase.*
import com.windea.breezeframework.core.enums.core.ReferenceCase.*
import kotlin.test.*

class StringExtensionsKtTest {
	@Test //TESTED
	fun splitToWordListTest() {
		assertEquals(listOf("abc", "abc", "abc"), "  abc abc  abc".splitToWordList())
		assertEquals(listOf("abc", "abc", "abc"), "__abc_abc__abc".splitToWordList('_'))
		assertEquals(listOf("abc", "abc", "abc"), "--abc-abc--abc-".splitToWordList('-'))
	}
	
	@Test //TESTED
	fun toWordsTest() {
		assertEquals("abc Abc", "abcAbc".toWords())
		assertEquals("Abc Abc", "AbcAbc".toWords())
		assertEquals("ABC Abc", "ABCAbc".toWords())
	}
	
	@Test //TESTED
	fun letterCaseTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".switchCaseBy(camelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".switchCaseBy(PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".switchCaseBy(`kebab-case`))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".switchCaseBy(SCREAMING_SNAKE_CASE))
		assertEquals("abc abc", "AbcAbc".switchCaseBy(`lower case words`))
		assertEquals("Abc Abc", "abcAbc".switchCaseBy(`Capitalized Words`))
		assertEquals("abcAbc", "Abc Abc".switchCaseBy(camelCase))
		assertEquals("abcabc", "AbcAbc".switchCaseBy(lowercase))
		assertEquals("ABCABC", "ABcABc".switchCaseBy(UPPERCASE))
	}
	
	@Test //TESTED ALMOST
	fun referenceCaseTest() {
		assertEquals("Abc.Abc", "Abc.Abc".switchCaseBy(Standard))
		assertEquals("abc.abc[1].abc", "/abc/abc/1/abc".switchCaseBy(Standard))
		assertEquals("$.abc.abc.[1].abc", "/abc/abc/1/abc".switchCaseBy(Json))
		assertEquals("#/abc/abc/1/abc", "/abc/abc/1/abc".switchCaseBy(JsonSchema))
	}
	
	@Test //TESTED
	fun repeatExtensionTest() {
		println("abc".repeat(3))
		println("abc".flatRepeat(3))
	}
	
	@Test //TESTED
	fun messageFormatTest() {
		assertEquals("123a123b123","123{0}123{1}123".messageFormat("a","b"))
	}
	
	@Test //TESTED
	fun customFormatTest() {
		assertEquals("1a2b3","1{}2{}3".customFormat("{}","a","b"))
		assertEquals("1a2b3", "1\${}2\${}3".customFormat("\${}", "a", "b"))
		assertEquals("1a2b3","1{0}2{1}3".customFormat("{index}","a","b"))
		assertEquals("1a2b3", "1{aaa}2{bbb}3".customFormat("{name}", "aaa" to "a", "bbb" to "b"))
		assertEquals("1b2a3","1{1}2{0}3".customFormat("{index}","a","b"))
		assertEquals("1b2a3", "1{bbb}2{aaa}3".customFormat("{name}", "aaa" to "a", "bbb" to "b"))
	}
	
	@Test //TESTED
	fun quoteTest() {
		assertEquals(""""'1\"2'"""", """'1"2'""".wrapQuote('"'))
		assertEquals("""'1"2'""", """"'1\"2'"""".unwrapQuote())
	}
	
	@Test //TESTED
	fun progressiveTest() {
		val a = """
			123
			123123
		""".trimIndent()
		val b = """
			123
			123123
			123123123
		""".trimIndent()
		
		//println(a plusByLine b)
		//println(a.padStartByLine())
		//println(a.padEndByLine())
	}
}
