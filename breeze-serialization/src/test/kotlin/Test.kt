package com.windea.breezeframework.serialization

import kotlinx.serialization.*
import kotlin.test.*

class Tests {
	@Test
	@ImplicitReflectionSerializer
	fun test1() {
		val map = Mapper.map(Data(42, "foo"))
		println(map)
		
		val map2 = Mapper.map(Data.serializer(), Data(42, "foo"))
		println(map2)
		
		val data = Mapper.unmap<Data>(map)
		println(data)
		
		val data2 = Mapper.unmap(Data.serializer(), map2)
		println(data2)
	}
}

@Serializable
data class Data(val first: Int, val second: String)
