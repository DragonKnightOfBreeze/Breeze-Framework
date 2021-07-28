// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.extension

import org.junit.*

class UnstableExtensionsTest {
	@Test
	fun fuzzyMatchesTest() {
		"name_abc".fuzzyMatches("name") //true
		"name_abc".fuzzyMatches("name_abc") //true
		"name_abc".fuzzyMatches("n_c") //false
		"name_abc".fuzzyMatches("n_a") //true
		"name_abc".fuzzyMatches("n_ab") //true
		"name_abc".fuzzyMatches("na") //true
		"name_abc".fuzzyMatches("nc") //false
	}
}
