// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.serialization.components

import com.windea.breezeframework.core.annotations.*

/**
 * 由第三方库委托实现的序列化器。
 * 其具体实现需要依赖第三方库，如`gson`，`fastjson`，`jackson`和`kotlinx-serialization`。
 */
@BreezeComponent
interface DelegateSerializer
