// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.core.model

import icu.windea.breezeframework.core.extension.emptyPage
import icu.windea.breezeframework.core.extension.toPage
import org.junit.Test

class PageTest {
	@Test
	fun test1(){
		val p1 = emptyPage<String>()
		val p2 = (0..12).toList().toPage(1,10)
		val p3 = (0..12).toList().toPage(2,10)
		println()
	}
}
