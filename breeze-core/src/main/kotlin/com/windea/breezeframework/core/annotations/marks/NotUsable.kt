package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为不可用的项。*/
@MustBeDocumented
@Inherited
annotation class NotUsable(
	val message: String = "Not usable."
)
