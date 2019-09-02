package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为未实现的项。*/
@MustBeDocumented
@Inherited
annotation class NotImplemented(
	val message: String = "Not implemented."
)
