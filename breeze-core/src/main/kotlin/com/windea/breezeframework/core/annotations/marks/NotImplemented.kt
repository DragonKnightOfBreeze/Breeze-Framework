package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**未实现的项的注解。*/
@MustBeDocumented
@Inherited
annotation class NotImplemented(
	val message: String = "Not implemented."
)
