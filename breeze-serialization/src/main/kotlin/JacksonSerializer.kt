// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization

import com.fasterxml.jackson.databind.*
import com.windea.breezeframework.core.domain.*

/**
 * 由Jackson委托实现的序列化器。
 *
 * @see com.fasterxml.jackson.databind.ObjectMapper
 */
interface JacksonSerializer : Serializer,DelegateSerializer
