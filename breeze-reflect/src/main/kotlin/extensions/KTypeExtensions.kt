// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("KTypeExtensions")

package com.windea.breezeframework.reflect.extensions

import java.lang.reflect.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**
 * 得到当前Kotlin类型的擦除类型。可用于将[KType]转花成[Class]。
 */
val KType.erasureType: Class<out Any>
	get() = this.jvmErasure.java
