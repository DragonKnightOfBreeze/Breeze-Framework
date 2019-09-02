package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为不适用的项。*/
@MustBeDocumented
@Inherited
annotation class NotSuitable(
	val message: String = "Not suitable."
)
