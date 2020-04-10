package com.windea.breezeframework.serializer.impl

import com.fasterxml.jackson.databind.*
import com.windea.breezeframework.serializer.*

/**由Jackson实现的序列化器。*/
interface JacksonSerializer<T : ObjectMapper> : Serializer {
	val delegate: T
}
