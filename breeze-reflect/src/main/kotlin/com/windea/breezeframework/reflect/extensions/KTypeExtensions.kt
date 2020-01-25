@file:JvmName("KTypeExtensions")

package com.windea.breezeframework.reflect.extensions

import kotlin.reflect.*
import kotlin.reflect.jvm.*

//https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/TypeErasure.kt
/**得到当前Kotlin类型的被擦除类型。*/
val KType.erasure: Class<out Any>
	get() = this.jvmErasure.java
