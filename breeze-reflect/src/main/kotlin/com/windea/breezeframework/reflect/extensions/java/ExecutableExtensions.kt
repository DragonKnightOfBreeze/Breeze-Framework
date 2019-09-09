@file:Reference(value = "[klutter](https://github.com/kohesive/klutter/blob/master/reflect/src/main/kotlin/uy/klutter/reflect/Helpers.kt)")

package com.windea.breezeframework.reflect.extensions.java

import com.windea.breezeframework.core.annotations.messages.*
import java.lang.reflect.*

//TODO Remain to add comments

val Executable?.isAbstract: Boolean get() = this?.modifiers?.let { Modifier.isAbstract(it) } ?: false

val Executable?.isFinal: Boolean
	get() = this?.modifiers?.let { Modifier.isFinal(it) } ?: false

val Executable?.isInterface: Boolean
	get() = this?.modifiers?.let { Modifier.isInterface(it) } ?: false

val Executable?.isNative: Boolean
	get() = this?.modifiers?.let { Modifier.isNative(it) } ?: false

val Executable?.isPrivate: Boolean
	get() = this?.modifiers?.let { Modifier.isPrivate(it) } ?: false

val Executable?.isProtected: Boolean
	get() = this?.modifiers?.let { Modifier.isProtected(it) } ?: false

val Executable?.isPublic: Boolean
	get() = this?.modifiers?.let { Modifier.isPublic(it) } ?: false

val Executable?.isStatic: Boolean
	get() = this?.modifiers?.let { Modifier.isStatic(it) } ?: false

val Executable?.isStrict: Boolean
	get() = this?.modifiers?.let { Modifier.isStrict(it) } ?: false

val Executable?.isSynchronized: Boolean
	get() = this?.modifiers?.let { Modifier.isSynchronized(it) } ?: false

val Executable?.isTransient: Boolean
	get() = this?.modifiers?.let { Modifier.isTransient(it) } ?: false

val Executable?.isVolatile: Boolean
	get() = this?.modifiers?.let { Modifier.isVolatile(it) } ?: false
