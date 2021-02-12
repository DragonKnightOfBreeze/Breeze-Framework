// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl

import com.windea.breezeframework.mapper.*
import com.windea.breezeframework.serializer.*

/**
 * 由BreezeFramework实现的序列化器。
 * @see com.windea.breezeframework.mapper.Mapper
 * */
interface BreezeSerializer<T : Mapper> : Serializer {
	val delegate: T
}
