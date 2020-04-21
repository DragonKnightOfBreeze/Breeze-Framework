package com.windea.breezeframework.core.extensions

import kotlin.test.*

class RegexExtensionsTest {
	@Test
	fun fromTntRangeTest() {
		//[9-9] -> 9
		assertEquals("9", Regex.fromRange(9, 9))
		//[2-9] -> [2-9]
		assertEquals("[2-9]", Regex.fromRange(2, 9))
		//[2-13] -> [2-9]|[10-13] -> [2-9]|1[0-3]
		assertEquals("[2-9]|1[0-3]", Regex.fromRange(2, 13))
		//[12-17] -> [12-17] -> 1[2-7]
		assertEquals("1[2-7]", Regex.fromRange(12, 17))
		//[23-45] -> [23-29]|[30-39]|[40-45] -> 2[3-9]|3[0-9]|4[0-5]
		assertEquals("2[3-9]|3[0-9]|4[0-5]", Regex.fromRange(23, 45))
		//[123-345] -> [123-129]|[130-199]|[200->299]|[300-339]|[340-345] -> 12[3-9]|1[3-9][0-9]|2[0-9][0-9]|3[0-3][0-9]|34[0-5]
		assertEquals("12[3-9]|1[3-9][0-9]|2[0-9][0-9]|3[0-3][0-9]|34[0-5]", Regex.fromRange(123, 345))
	}
}
