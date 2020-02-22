package com.windea.breezeframework.serializer.impl

import com.windea.breezeframework.serializer.Serializer
import kotlinx.serialization.*

/**由KotlinxSerialization实现的序列化器。*/
@PublishedApi
internal interface KotlinSerializer<T : StringFormat> : Serializer {
	val delegate: T
}
