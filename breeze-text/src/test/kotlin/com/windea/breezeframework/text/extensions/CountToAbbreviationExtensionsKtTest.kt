package com.windea.breezeframework.text.extensions

import org.junit.*

class CountToAbbreviationExtensionsKtTest {
	@Test
	fun toAbbreviation() {
		println(11.toAbbreviation(1))
		println(1111111111.toAbbreviation(5))
		println(1111111111.toAbbreviation(9))
		println(555555555.toAbbreviation(8))
		println(600000000.toAbbreviation(7))
		println(11.toAbbreviation(1, 0))
		println(1111111111.toAbbreviation(5, 1))
		println(1111111111.toAbbreviation(9, 2))
		println(555555555.toAbbreviation(8, 3))
		println(600000000.toAbbreviation(7, 4))
	}
	
	@Test
	fun toChsAbbreviation() {
		println(11.toChsAbbreviation(1))
		println(1111111111.toChsAbbreviation(5))
		println(1111111111.toChsAbbreviation(9))
		println(555555555.toChsAbbreviation(8))
		println(600000000.toChsAbbreviation(7))
	}
}
