package com.windea.breezeframework.core.annotations.marks

/**标注为不推荐的项。*/
@MustBeDocumented
annotation class NotRecommended(
	val message: String = "Not recommended."
)
