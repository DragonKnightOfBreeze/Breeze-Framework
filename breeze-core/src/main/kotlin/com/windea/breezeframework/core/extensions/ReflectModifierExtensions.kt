@file:Reference("[klutter](https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/Helpers.kt)")

package com.windea.breezeframework.core.extensions

import com.windea.breezeframework.core.annotations.internal.*
import java.lang.reflect.*

//Remain to add comments

fun Executable?.isAbstract(): Boolean = this?.modifiers?.let { Modifier.isAbstract(it) } ?: false

fun Executable?.isFinal(): Boolean = this?.modifiers?.let { Modifier.isFinal(it) } ?: false

fun Executable?.isInterface(): Boolean = this?.modifiers?.let { Modifier.isInterface(it) } ?: false

fun Executable?.isNative(): Boolean = this?.modifiers?.let { Modifier.isNative(it) } ?: false

fun Executable?.isPrivate(): Boolean = this?.modifiers?.let { Modifier.isPrivate(it) } ?: false

fun Executable?.isProtected(): Boolean = this?.modifiers?.let { Modifier.isProtected(it) } ?: false

fun Executable?.isPublic(): Boolean = this?.modifiers?.let { Modifier.isPublic(it) } ?: false

fun Executable?.isStatic(): Boolean = this?.modifiers?.let { Modifier.isStatic(it) } ?: false

fun Executable?.isStrict(): Boolean = this?.modifiers?.let { Modifier.isStrict(it) } ?: false

fun Executable?.isSynchronized(): Boolean = this?.modifiers?.let { Modifier.isSynchronized(it) } ?: false

fun Executable?.isTransient(): Boolean = this?.modifiers?.let { Modifier.isTransient(it) } ?: false

fun Executable?.isVolatile(): Boolean = this?.modifiers?.let { Modifier.isVolatile(it) } ?: false
