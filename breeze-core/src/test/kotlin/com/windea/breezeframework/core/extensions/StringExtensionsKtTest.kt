package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.LetterCase.*
import com.windea.breezeframework.core.enums.ReferenceCase.*
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
		assertEquals("abcAbcAbc", "AbcAbcAbc".to(camelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".to(PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".to(`kebab-case`))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".to(SCREAMING_SNAKE_CASE))
		assertEquals("abc abc", "AbcAbc".to(`lower case words`))
		assertEquals("Abc abc", "abcAbc".to(`Capitalized Words`))
		assertEquals("abcAbc", "Abc Abc".to(camelCase))
		assertEquals("abcabc", "AbcAbc".to(lowercase))
		assertEquals("ABCABC", "ABcABc".to(UPPERCASE))
	}
	
	@Test //TESTED ALMOST
	fun referenceCaseTest() {
		assertEquals("Abc.Abc", "AbcAbc".to(StandardReference))
		assertEquals("abc.abc[1].abc", "/abc/abc/1/abc".to(StandardReference))
		assertEquals("$.abc.abc.[1].abc", "/abc/abc/1/abc".to(JsonReference))
		assertEquals("#/abc/abc/1/abc", "/abc/abc/1/abc".to(JsonSchemaReference))
	}
	
	@Test //TESTED
	fun repeatExtension() {
		println("abc".repeat(3))
		println("abc".flatRepeat(3))
	}
}
