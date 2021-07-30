// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package icu.windea.breezeframework.serialization

/**
 * 由Jackson委托实现的数据的序列化器。
 *
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 */
interface JacksonSerializer : DataSerializer, DelegateSerializer
