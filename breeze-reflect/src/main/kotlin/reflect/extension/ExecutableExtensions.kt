// Copyright (c) 2020-2021 DragonKnightOfBreeze Windea
// Breeze is blowing...

@file:JvmName("ExecutableExtensions")

package icu.windea.breezeframework.reflect.extension

import java.lang.reflect.*

//https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/Helpers.kt

val Executable.isAbstract: Boolean get() = this.modifiers.let { Modifier.isAbstract(it) }

val Executable.isFinal: Boolean get() = this.modifiers.let { Modifier.isFinal(it) }

val Executable.isInterface: Boolean get() = this.modifiers.let { Modifier.isInterface(it) }

val Executable.isNative: Boolean get() = this.modifiers.let { Modifier.isNative(it) }

val Executable.isPrivate: Boolean get() = this.modifiers.let { Modifier.isPrivate(it) }

val Executable.isProtected: Boolean get() = this.modifiers.let { Modifier.isProtected(it) }

val Executable.isPublic: Boolean get() = this.modifiers.let { Modifier.isPublic(it) }

val Executable.isStatic: Boolean get() = this.modifiers.let { Modifier.isStatic(it) }

val Executable.isStrict: Boolean get() = this.modifiers.let { Modifier.isStrict(it) }

val Executable.isSynchronized: Boolean get() = this.modifiers.let { Modifier.isSynchronized(it) }

val Executable.isTransient: Boolean get() = this.modifiers.let { Modifier.isTransient(it) }

val Executable.isVolatile: Boolean get() = this.modifiers.let { Modifier.isVolatile(it) }
