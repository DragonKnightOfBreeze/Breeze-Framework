// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serializer.impl

import icu.windea.breezeframework.mapper.*
import icu.windea.breezeframework.serializer.*

/**
 * 由BreezeFramework实现的序列化器。
 * @see icu.windea.breezeframework.mapper.Mapper
 * */
interface BreezeSerializer<T : Mapper> : Serializer {
	val delegate: T
}
