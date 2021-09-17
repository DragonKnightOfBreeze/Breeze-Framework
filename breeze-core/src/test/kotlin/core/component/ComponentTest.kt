// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.component

import icu.windea.breezeframework.core.component.extension.convert
import org.junit.*

class ComponentTest {
	@Test
	fun inferTargetTypeTest() {
		println(111.convert<String>())
		println("111".convert<Int>())

		try {
			println(Converters.ByteConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converters.ShortConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converters.IntConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
		try {
			println(Converters.LongConverter.targetType)
		} catch(e: Throwable) {
			e.printStackTrace()
		}
	}
}
