// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("KTypeExtensions")

package com.windea.breezeframework.reflect.extension

import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**
 * 得到当前Kotlin类型的擦除类型。可用于将[KType]转花成[Class]。
 */
val KType.erasureType: Class<out Any>
	get() = this.jvmErasure.java
