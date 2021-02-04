// Copyright (c) 2019-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.model.*
import com.windea.breezeframework.core.type.*

/**将浮点数二元素元组转化为二维向量。*/
inline fun TypedTuple2<Float>.toVector2(): Vector2 = Vector2(first, second)

/**将浮点数三元素元组转化为三维向量。*/
inline fun TypedTuple3<Float>.toVector3(): Vector3 = Vector3(first, second, third)
