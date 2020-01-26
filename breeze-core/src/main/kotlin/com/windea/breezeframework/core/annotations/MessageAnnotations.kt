package com.windea.breezeframework.core.annotations

import org.intellij.lang.annotations.*
import kotlin.annotation.AnnotationTarget.*

/**名字。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Name(
	/**名字一览。*/
	vararg val value: String
)

/**别名。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FIELD)
annotation class Alias(
	/**别名一览。*/
	vararg val value: String
)

/**引用。*/
@MustBeDocumented
@Target(CLASS, PROPERTY, FUNCTION, FILE)
annotation class Reference(
	/**引用一览。*/
	@Language("Markdown")
	vararg val message: String
)
