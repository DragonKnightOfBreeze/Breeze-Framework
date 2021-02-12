// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.serializer

/**
 * 由第三方库委托实现的序列化器。
 * 其具体实现需要依赖第三方库，如`gson`，`fastjson`，`jackson`和`kotlinx-serialization`。
 *
 * @see JacksonSerializer
 * @see KotlinxSerializer
 * @see GsonSerializer
 * @see FastJsonSerializer
 */
interface DelegateSerializer
