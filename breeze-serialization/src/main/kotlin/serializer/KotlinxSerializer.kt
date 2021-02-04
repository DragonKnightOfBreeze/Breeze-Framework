// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

import com.windea.breezeframework.core.*
import com.windea.breezeframework.core.annotation.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.lang.reflect.*

/**
 * 由Kotlinx Serialization委托实现的序列化器。
 *
 * @see kotlinx.serialization.SerialFormat
 * @see KotlinxJsonSerializer
 */
interface KotlinxSerializer : DelegateSerializer
