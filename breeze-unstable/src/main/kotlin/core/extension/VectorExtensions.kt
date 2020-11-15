// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("VectorExtensions")
@file:Suppress("NOTHING_TO_INLINE")

package com.windea.breezeframework.core.extension

import com.windea.breezeframework.core.*

/**将二维向量转化为三位向量。*/
inline fun Vector2.toVector3(): Vector3 = Vector3(this.x, this.y, 0f)


/**将二维向量转化为二元素元组。*/
inline fun Vector2.toPair(): Pair<Float, Float> = Pair(this.x, this.y)

/**将三维向量转化为三元素元组。*/
inline fun Vector3.toTriple(): Triple<Float, Float, Float> = Triple(this.x, this.y, this.z)
