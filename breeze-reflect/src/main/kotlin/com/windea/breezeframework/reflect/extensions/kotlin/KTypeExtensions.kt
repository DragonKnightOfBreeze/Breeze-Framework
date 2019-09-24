package com.windea.breezeframework.reflect.extensions.kotlin

import com.windea.breezeframework.core.annotations.marks.*
import kotlin.reflect.*
import kotlin.reflect.jvm.*

/**得到当前Kotlin类型的被擦除类型。*/
@Reference("[klutter](https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/TypeErasure.kt)")
val KType.erasedType: Class<out Any>
	get() = this.jvmErasure.java
