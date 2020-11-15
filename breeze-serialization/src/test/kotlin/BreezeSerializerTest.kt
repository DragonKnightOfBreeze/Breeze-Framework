// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.windea.breezeframework.serialization.components.*
import com.windea.breezeframework.serialization.extensions.*
import kotlin.test.*

class BreezeSerializerTest {
	@Test
	fun jsonSerializeTest() {
		val a = mapOf(
			"name" to "Windea",
			"gender" to "Female",
			"age" to 3000,
			"weapon" to arrayOf("BreezesLanding", "BreathOfBreeze")
		)
		println(a.serializeBy(JsonSerializer.BreezeJsonSerializer()))
	}

}
