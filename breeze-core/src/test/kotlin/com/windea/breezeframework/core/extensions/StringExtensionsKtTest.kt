package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.enums.LetterCase.*
import com.windea.breezeframework.core.enums.ReferenceCase.*
import kotlin.test.*

class StringExtensionsKtTest {
	@Test //TESTED ALMOST
	fun letterCaseTest() {
		assertEquals("abcAbcAbc", "AbcAbcAbc".to(camelCase))
		assertEquals("AbcAbcAbc", "abcAbcAbc".to(PascalCase))
		assertEquals("abc-abc-abc", "ABC_ABC_ABC".to(`kebab-case`))
		assertEquals("ABC_ABC_ABC", "abc-abc-abc".to(SCREAMING_SNAKE_CASE))
		assertEquals("abc abc", "AbcAbc".to(`lower case words`))
		assertEquals("Abc abc", "abcAbc".to(`Capitalized words`))
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
}
