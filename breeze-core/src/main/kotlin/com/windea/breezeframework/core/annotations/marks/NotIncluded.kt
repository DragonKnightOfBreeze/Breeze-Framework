package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为未包含的项。*/
@MustBeDocumented
@Inherited
annotation class NotIncluded(
	val message: String = "Not included."
)
