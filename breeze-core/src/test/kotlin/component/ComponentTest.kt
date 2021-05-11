// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.extension.*
import org.junit.*

class ComponentTest {
	@Test
	fun inferTargetTypeTest(){
		println(111.convert<String>())
		println("111".convert<Int>())

		try {
			println(Converter.ByteConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converter.ShortConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converter.IntConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converter.LongConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
	}
}
