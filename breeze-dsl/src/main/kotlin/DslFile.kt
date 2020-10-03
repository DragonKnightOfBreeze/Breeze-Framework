// Copyright (c) 2019-2020 DragonKnightOfBreeze Windea
// Breeze is blowing...

package com.windea.breezeframework.dsl

/**
 * Annotation that defines a dsl file,
 * which is a kotlin script file and whose output result is a [Dsl].
 */
@MustBeDocumented
@Target(AnnotationTarget.FILE)
annotation class DslFile

