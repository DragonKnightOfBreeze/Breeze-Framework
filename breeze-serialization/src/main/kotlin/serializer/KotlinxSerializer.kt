// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

/**
 * 由Kotlinx Serialization委托实现的数据的序列化器。
 *
 * @see kotlinx.serialization.StringFormat
 */
interface KotlinxSerializer :DataSerializer, DelegateSerializer
