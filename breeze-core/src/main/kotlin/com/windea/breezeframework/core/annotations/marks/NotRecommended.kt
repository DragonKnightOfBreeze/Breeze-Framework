package com.windea.breezeframework.core.annotations.marks

import java.lang.annotation.*

/**标注为不推荐的项。*/
@MustBeDocumented
@Inherited
annotation class NotRecommended(
	val message: String = "Not recommended."
)
