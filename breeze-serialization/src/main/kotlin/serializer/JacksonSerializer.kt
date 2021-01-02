// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

/**
 * 由Jackson委托实现的序列化器。
 *
 * @see com.fasterxml.jackson.databind.json.JsonMapper
 * @see JacksonJsonSerializer
 * @see JacksonYamlSerializer
 * @see JacksonXmlSerializer
 * @see JacksonPropertiesSerializer
 */
interface JacksonSerializer : DataSerializer,DelegateSerializer
