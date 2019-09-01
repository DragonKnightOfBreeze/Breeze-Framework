package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**未包含的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotIncluded(
	val message: String = "Not included."
)
