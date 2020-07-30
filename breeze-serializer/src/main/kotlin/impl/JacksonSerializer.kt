package com.windea.breezeframework.serializer.impl

import com.fasterxml.jackson.databind.*
import com.windea.breezeframework.serializer.*

/**
 * 由Jackson实现的序列化器。
 * @see com.fasterxml.jackson.databind.ObjectMapper
 * */
interface JacksonSerializer<T : ObjectMapper> : Serializer {
	val delegate: T
}
