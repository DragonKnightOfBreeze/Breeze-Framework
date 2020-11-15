// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*

/**
 * 由Kotlinx Serialization委托实现的序列化器。
 *
 * @see kotlinx.serialization.StringFormat
 */
@BreezeComponent
interface KotlinxSerializer : DataSerializer, DelegateSerializer
