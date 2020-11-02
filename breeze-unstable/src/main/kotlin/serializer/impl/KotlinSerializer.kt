// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serializer.impl

import com.windea.breezeframework.serializer.Serializer
import kotlinx.serialization.*

/**
 * 由KotlinxSerialization实现的序列化器。
 * @see kotlinx.serialization.StringFormat
 */
interface KotlinSerializer<T : StringFormat> : Serializer {
	val delegate: T
}
